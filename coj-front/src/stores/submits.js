import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useSubmitsStore = defineStore('submits', () => {

    const submits = ref([]);

    return { submits };
})
