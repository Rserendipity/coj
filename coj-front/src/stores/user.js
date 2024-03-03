import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useUserStore = defineStore('user', () => {
  // 保存用户信息
  const userinfo = ref({
    "id": -1,
    "account": "",
    "nickname": "",
    "avatar": "",
    "role": "",

    // "id": 1,
    // "account": "admin",
    // "nickname": "管理员",
    // "avatar": "",
    // "role": "",
  });

  return {userinfo};
})
