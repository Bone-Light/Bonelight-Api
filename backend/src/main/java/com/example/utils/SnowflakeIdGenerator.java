package com.example.utils;

import org.springframework.stereotype.Component;

/**
 * 雪花算法ID生成器
 * 雪花算法是一个分布式ID生成算法，可以保证在分布式系统中生成的ID是唯一的。
 * ID组成（64位）：
 * - 1位符号位，固定为0
 * - 41位时间戳，精确到毫秒，可以使用69年
 * - 5位数据中心ID，最多支持32个数据中心
 * - 5位工作机器ID，每个数据中心最多支持32个工作机器
 * - 12位序列号，每毫秒最多生成4096个ID
 */
@Component
public class SnowflakeIdGenerator {
    /**
     * 开始时间戳（2025-05-06T00:00:00Z）
     * 用于计算相对时间戳，可以使系统时间戳位数更小
     */
    private static final long START_TIMESTAMP = 1746489600000L;

    /**
     * 数据中心ID所占位数：5位
     * 工作机器ID所占位数：5位
     * 序列号所占位数：12位
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 数据中心ID最大值：31
     * 工作机器ID最大值：31
     * 序列号最大值：4095
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /**
     * 各部分向左的位移量
     * - 工作机器ID左移12位
     * - 数据中心ID左移17位（12+5）
     * - 时间戳左移22位（12+5+5）
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * 数据中心ID
     */
    private final long dataCenterId;

    /**
     * 工作机器ID
     */
    private final long workerId;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 同一毫秒内的序列号
     */
    private long sequence = 0L;

    /**
     * 默认构造函数
     * 使用默认的数据中心ID（1）和工作机器ID（1）
     */
    public SnowflakeIdGenerator(){
        this(1, 1);
    }

    /**
     * 构造函数
     * @param dataCenterId 数据中心ID (0~31)
     * @param workerId 工作机器ID (0~31)
     * @throws IllegalArgumentException 当数据中心ID或工作机器ID超出范围时抛出
     */
    private SnowflakeIdGenerator(long dataCenterId, long workerId) {
        // 合法性检查
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID can't be greater than " + MAX_DATA_CENTER_ID + " or less than 0");
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    /**
     * 生成下一个ID（线程安全）
     *
     * @return 返回一个唯一的雪花算法ID
     * @throws IllegalStateException 当检测到时钟回拨时抛出
     */
    public synchronized long nextId() {
        // 获取当前时间戳
        long timestamp = getCurrentTimestamp();

        // 时钟回拨检查
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards. Refusing to generate ID.");
        }

        // 如果是同一毫秒内
        if (timestamp == lastTimestamp) {
            // 序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒内序列号用完，等待下一毫秒
            if (sequence == 0) {
                timestamp = getNextTimestamp(lastTimestamp);
            }
        } else {
            // 不同毫秒，序列号重置
            sequence = 0L;
        }

        // 更新上次的时间戳
        lastTimestamp = timestamp;

        // 组合生成ID（位运算）
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT) |
                (dataCenterId << DATA_CENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    /**
     * 获取当前时间戳
     * @return 当前时间戳（毫秒）
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取下一个时间戳
     * 循环等待直到获取到一个比lastTimestamp大的时间戳
     *
     * @param lastTimestamp 上次的时间戳
     * @return 下一个时间戳
     */
    private long getNextTimestamp(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }
}