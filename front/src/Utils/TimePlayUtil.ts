export interface Task{
    callback: () => void
    interval: number
    nextTick: number
    maxCount: number
    currentCount: number
    startTime: number // 任务启动时间戳
    endTime?: number // 任务结束时间戳（自动记录）
    remainingTime?: number // 记录剩余时间
    paused: boolean
}

class TimePlay {
    // 存储任务对象（含生命周期状态）
    static taskMap = new Map<() => void,Task>();

    static intervalHandle: NodeJS.Timeout | null = null;

    // 注册任务（带生命周期记录）
    static addTask(
        callback: () => void,
        interval: number,
        maxCount: number = 0
    ) {
        const task:Task = {
            callback,
            interval,
            nextTick: Date.now() + interval,
            maxCount,
            currentCount: 0,
            startTime: Date.now(),
            paused: false,
        };

        this.taskMap.set(callback, task);
        this.startIntervalIfNeeded();
        return task.startTime; // 返回启动时间供外部使用
    }

    // 启动定时器（带误差补偿）
    static startIntervalIfNeeded() {
        if (!this.intervalHandle) {
            this.intervalHandle = setInterval(() => {
                const now = Date.now();
                this.taskMap.forEach((task, callback) => {
                    if (task.paused) return;

                    if (now >= task.nextTick) {
                        try {
                            task.callback();
                        } catch (error) {
                            console.error('[定时任务执行错误]', error);
                            this.removeTask(callback);
                            return;
                        }

                        // 记录执行信息
                        task.currentCount++;
                        task.endTime = now; // 更新最后执行时间

                        // 生命周期判断
                        const isCompleted =
                            task.maxCount > 0 && ((task.currentCount >= task.maxCount) ||
                            (task.endTime - task.startTime >= task.interval * task.maxCount));

                        if (isCompleted) {
                            this.removeTask(callback);
                        } else {
                            task.nextTick = now + task.interval;
                        }
                    }
                });
            }, 100); // 检测间隔
        }
    }

    // 暂停任务（保留剩余时间）
    static pauseTask(callback: () => void) {
        const task = this.taskMap.get(callback);
        if (task) {
            task.paused = true;
            task.remainingTime = task.endTime! - Date.now(); // 计算剩余时间
        }
    }

    // 恢复任务（精确续时）
    static resumeTask(callback: () => void) {
        const task = this.taskMap.get(callback);
        if (task && task.paused) {
            task.paused = false;
            task.startTime = Date.now() - (task.endTime! - task.startTime); // 修正起始时间
            task.endTime = undefined; // 清除完成标记
            task.nextTick = Date.now() + task.remainingTime!; // 恢复触发时间
        }
    }

    // 移除任务
    static removeTask(callback: () => void) {
        const task = this.taskMap.get(callback);
        if (task) {
            this.taskMap.delete(callback);
        }
    }
}

export default TimePlay;