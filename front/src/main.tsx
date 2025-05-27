import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from "@/App.tsx";
import {Provider} from "react-redux";
import {store} from "@/store/store.ts";
import '@/assets/css/global.css'
createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <Provider store={store}> {/* 关键包裹 */}
          <App />
      </Provider>
  </StrictMode>,
)
