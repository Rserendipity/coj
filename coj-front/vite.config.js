import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// element
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// monaco
import monacoEditorPlugin from 'vite-plugin-monaco-editor'

// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    monacoEditorPlugin({
      languageWorkers: ['editorWorkerService']
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: { //主要是加上这段代码
    proxy: {
      '/api': {
        // target: 'http://120.26.15.28:11111',	//实际请求地址
        target: 'http://127.0.0.1:11111',	//实际请求地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  }
})
