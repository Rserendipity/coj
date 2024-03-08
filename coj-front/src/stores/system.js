import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useSystemStore = defineStore('system', () => {
    // 加载状态
    const loading = ref({
        question: false,
    });

    // 详情页面
    const detail = ref({
        question: false,
    });

    return {loading, detail};
})
