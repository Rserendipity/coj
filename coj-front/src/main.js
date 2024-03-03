import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import {VMdPreview, VueMarkdownEditor} from "@/config/md.js";

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)

// 使用 markdown 编辑器
app.use(VueMarkdownEditor);
app.use(VMdPreview);

app.mount('#app')
