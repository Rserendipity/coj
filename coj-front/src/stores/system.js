import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useSystemStore = defineStore('system', () => {
    // 加载状态
    const loading = ref({
        question: false,
    });

    // 用户选择的编程语言
    const userLanguage = ref('java');

    // 默认视图
    const defaultView = ref('description');

    return {loading, userLanguage, defaultView};
})
