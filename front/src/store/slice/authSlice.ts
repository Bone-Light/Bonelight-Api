import {createAsyncThunk, createSlice, type PayloadAction} from '@reduxjs/toolkit'

export interface User {
    role: string
    username: string
    email: string
}

export interface AuthState {
    user: User | null
    loading: boolean
    error: string | null
}

const initialState: AuthState = {
    user: null,
    loading: false,
    error: null
}

// 创建异步 thunk action
export const loginUser = createAsyncThunk<User, { username: string; password: string }>(
    'auth/login',
    async (credentials, { rejectWithValue }) => {
        try {
            const response = await fetch('/aaa/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', // 重要：保持 session 需要包含凭证
                body: JSON.stringify(credentials),
            })

            if (!response.ok) {
                throw new Error('登录失败')
            }

            const user = await response.json()
            return user
        } catch (error) {
            return rejectWithValue(error instanceof Error ? error.message : '登录失败')
        }
    }
)

const authSlice = createSlice({
    name: 'auth', // 存储的命名空间
    initialState,
    reducers: {
        loginStart(state:AuthState) {
            state.loading = true
            state.error = null
        },
        // state 设置状态， PayloadAction<User> 装参数的，payload 提取
        loginSuccess(state:AuthState, action: PayloadAction<User>) {
            state.user = action.payload
            state.loading = false
        },
        loginFailure(state:AuthState, action: PayloadAction<string>) {
            state.error = action.payload
            state.loading = false
        }
    }
})
// 自动生成的 action creators

export const { loginStart, loginSuccess, loginFailure } = authSlice.actions
// 自动生成的 reducer

export default authSlice.reducer
