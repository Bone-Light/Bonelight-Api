// hooks.ts
import {type TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import type { RootState, AppDispatch } from './store';

// 类型安全的 useDispatch 和 useSelector
export const useAppDispatch = () => useDispatch<AppDispatch>();
// TypedUseSelectorHook<RootState> 获取 RootState 数据 提取器
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

// 可选：封装特定模块的 Hook（如用户模块）
export const useUser = () => {
    // 传递器 用于触发 state 的函数
    // const dispatch = useAppDispatch();
    // useAppSelector 用于从 Redux Store 中读取数据
    const auth = useAppSelector((state:RootState) => state.auth);

    return { // 这里暴露 模块状态
        auth,
    };
};