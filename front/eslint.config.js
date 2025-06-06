import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import tseslint from 'typescript-eslint'

export default tseslint.config(
  { ignores: ['dist'] },
  {
    extends: [js.configs.recommended, ...tseslint.configs.recommended],
    files: ['**/*.{ts,tsx}'],
    languageOptions: {
      ecmaVersion: 2020,
      globals: globals.browser,
    },
    plugins: {
      'react-hooks': reactHooks,
      'react-refresh': reactRefresh,
    },
    rules: {
      ...reactHooks.configs.recommended.rules,
      'react-refresh/only-export-components': [
        'warn',
        { allowConstantExport: true },
      ],
      "@typescript-eslint/no-unused-vars": [
          "error",
          {
              varsIgnorePattern: "^_", // 忽略变量名以下划线开头的变量
              argsIgnorePattern: "^_",  // 忽略函数参数以下划线开头的参数
              functionIgnorePattern: "^_", // 忽略下划线开头的函数
          }
      ],
      // "@typescript-eslint/no-unused-vars": false,
      "@typescript-eslint/no-explicit-any": "off", // 关闭 any 类型检查
    },
  },
)
