// hooks.ts
import {type TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import type { RootState, AppDispatch } from './store';
import {loginStart, loginSuccess, loginFailure, type User} from '@/store/slice/authSlice';

// 类型安全的 useDispatch 和 useSelector
export const useAppDispatch = () => useDispatch<AppDispatch>();
// TypedUseSelectorHook<RootState> 获取 RootState 数据 提取器
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

// 可选：封装特定模块的 Hook（如用户模块）
export const useUser = () => {
    // 传递器 用于触发 state 的函数
    const dispatch = useAppDispatch();
    // useAppSelector 用于从 Redux Store 中读取数据
    const auth = useAppSelector((state:RootState) => state.auth);

    return {
        auth,
        /*这下面的其实冗余了，但用就是这么用*/
        login: () => dispatch(loginStart()),
        loginSuccess: (user:User) => dispatch(loginSuccess(user)),
        loginFailure: (error:string) => dispatch(loginFailure(error)),
    };
};