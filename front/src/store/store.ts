// store.js (JavaScript 版)
import { configureStore } from '@reduxjs/toolkit';
import authSlice from "@/store/slice/authSlice.ts";
export const store = configureStore({
    reducer: {
        auth: authSlice,
    },
    // 内置默认中间件已包含 redux-thunk 和 immutableCheck
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            // 关闭序列化检查（大型项目建议开启）
            serializableCheck: false
        })
});

// 导出 RootState 类型（用于类型安全的 useSelector） 自动推断 state 类型
export type RootState = ReturnType<typeof store.getState>;

// 导出 AppDispatch 类型（用于类型安全的 useDispatch）默认值
export type AppDispatch = typeof store.dispatch;