import { createSlice, createAsyncThunk, type PayloadAction } from '@reduxjs/toolkit'

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

// 通用登录 thunk（根据 rememberMe 参数选择存储位置）
export const loginUser = createAsyncThunk<User, {
    body: { username: string; password: string },
    rememberMe: boolean
}>(
    'auth/login',
    async ({ body, rememberMe }, { rejectWithValue }) => {
        try {
            const response = await fetch('/aaa/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', // 携带 cookie
                body: JSON.stringify(body), // 序列化为 json 对象
            })

            if (!response.ok) throw new Error('Login failed')

            const user = await response.json()

            // 根据 rememberMe 选择存储位置
            const storageKey = rememberMe ? 'persistentUser':'sessionUser'
            const storage = rememberMe ? localStorage:sessionStorage

            storage.setItem(storageKey, JSON.stringify(user))

            return user
        } catch (error) {
            return rejectWithValue(error instanceof Error ? error.message : 'Login failed')
        }
    }
)

export const autoLogin = createAsyncThunk<User>(
    'auth/autoLogin',
    async (_, { rejectWithValue }) => {
        try {
            let user: User | null = null

            // 优先尝试 sessionStorage
            const sessionUser = sessionStorage.getItem('sessionUser')
            if (sessionUser) {
                user = JSON.parse(sessionUser)
                if (user) {
                    await validateUser(user)
                } // 新增验证步骤
            }

            // 然后尝试 localStorage
            if (!user) {
                const persistentUser = localStorage.getItem('persistentUser')
                if (persistentUser) {
                    user = JSON.parse(persistentUser)
                    if (user) {
                        await validateUser(user)
                    }
                }
            }

            if (!user) return rejectWithValue(null);
            return user
        } catch (error) {
            clearAuthStorage() // 新增清理函数
            return rejectWithValue(error instanceof Error ? error.message : 'Auto login failed')
        }
    }
)

// todo 后端验证登录 -- 待补充
// 新增用户验证函数
const validateUser = async (user: User) => {
    const response = await fetch('/auth/api/validate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        // body: JSON.stringify({ token: user.token }) // 假设用户对象包含验证令牌
    })

    if (!response.ok) {
        clearAuthStorage()
        throw new Error('Invalid session')
    }
    return user
}

// 新增清理函数
const clearAuthStorage = () => {
    sessionStorage.removeItem('sessionUser')
    localStorage.removeItem('persistentUser')
}

// 获取本地 user 对象
const getStoredUser = (): User | null => {
    const sessionUser = sessionStorage.getItem('sessionUser');
    if (sessionUser) return JSON.parse(sessionUser);
    const persistentUser = localStorage.getItem('persistentUser');
    if (persistentUser) return JSON.parse(persistentUser);
    return null;
};

// 初始化
const initialState: AuthState = {
    user: getStoredUser(),
    loading: false,
    error: null
}

// 切片本体
const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        logout(state: AuthState) {
            state.user = null
            clearAuthStorage()
        }
    },
    extraReducers: (builder) => {
        builder
            // 调用时
            .addCase(loginUser.pending, (state:AuthState) => {
                state.loading = true
                state.error = null
            })
            // 成功时
            .addCase(loginUser.fulfilled, (state:AuthState, action:PayloadAction<User>) => {
                state.user = action.payload
                state.loading = false
            })
            // 失败时
            .addCase(loginUser.rejected, (state:AuthState, action:PayloadAction<string|unknown>) => {
                state.error = action.payload as string || 'Login failed'
                state.loading = false
            })
            .addCase(autoLogin.pending, (state:AuthState) => {
                state.loading = true
                state.error = null
            })
            .addCase(autoLogin.fulfilled, (state:AuthState, action:PayloadAction<User>) => {
                state.user = action.payload
                state.loading = false
            })
            .addCase(autoLogin.rejected, (state:AuthState, action:PayloadAction<string|unknown>) => {
                state.user = null
                state.error = action.payload as string || 'Auto login failed'
                state.loading = false
            })
    }
})

export const { logout } = authSlice.actions
export default authSlice.reducer