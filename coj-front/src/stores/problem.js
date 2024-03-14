import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useProblemStore = defineStore('problem', () => {
  
  const problems = ref([]);

  return { problems };
})
