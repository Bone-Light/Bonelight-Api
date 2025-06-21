import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import type {ApiObj} from "@/Api/type/ApiObj.ts";
import {getApiList as getApi}  from "@/Api/tot.ts";

interface ApiState {
    apiList: ApiObj[],
    loading: boolean,
    error: any,
}

const initialState: ApiState = {
    apiList: [],
    loading: false,
    error: null
}

// 定义异步 Thunk（处理 API 请求）
export const getApiList = createAsyncThunk<
    ApiObj[],  // 成功时的 payload 类型
    void       // 输入参数类型（无参数）
>(
    "apiList/get",
    async (_, thunkAPI:any) => {
        try {
            // 假设 getApi 是异步的（如请求后端）
            return getApi();
        } catch (error) {
            return thunkAPI.rejectWithValue("获取接口列表失败");
        }
    }
);


const apiListSlice = createSlice({
    name: 'apiList',
    initialState,
    reducers:{
        loading: (state) => {
            state.loading = true;
        }
    },
    extraReducers: (builder) => {
        builder
            // 处理 Thunk 的 pending 状态
            .addCase(getApiList.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            // 处理 Thunk 的 fulfilled 状态（成功）
            .addCase(getApiList.fulfilled, (state, action) => {
                state.apiList = action.payload; // 从 Thunk 的返回值更新列表
                state.loading = false;
            })
            // 处理 Thunk 的 rejected 状态（失败）
            .addCase(getApiList.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload instanceof Error
                    ? action.payload.message
                    : action.payload as string; // 提取错误信息
            });
    },
})

export const {loading} = apiListSlice.actions;
export default apiListSlice.reducer;

