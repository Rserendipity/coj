<script setup>
import {onMounted} from "vue";
import router from "@/router";
import {useUserStore} from "@/stores/user";
import checkAccess from "./common/checkAccess";
import {getUserInfoAPI} from "@/apis/user";
import httpInstance from "./utils/httpInstance";
import {useRoute} from "vue-router";

const route = useRoute();
const userStore = useUserStore();

const initRouter = () => {
  /*
  * 路由守卫
  * 判断用户是否登录
  * */
  router.beforeEach((to, from, next) => {
    if (userStore.userinfo.id === -1 && to.path !== '/user/login' && to.path !== '/user/register') {
      // ElMessage.warning("请先登录后访问");
      router.push('/user/login');
    } else if (to?.meta?.role !== null) { // 去往的页面需要权限
      // 用户没有访问该页面的权限
      if (!checkAccess(userStore.userinfo.role, to.meta.role)) {
        next({path: '/forbidden'}); // 跳转到禁止页面
        return;
      }
    }
    next();
  });
}

const initLogin = () => {
  // 从本地获取token
  let token = localStorage.getItem('token');
  if (!token) {
    router.push('/user/login');
  }
  httpInstance.defaults.headers['Authorization'] = token;

  getUserInfoAPI().then(({data}) => {
    if (data.code === 0) {
      ElMessage.success('已自动登录');
      userStore.userinfo = data.data;
      router.push('/');
    } else {
      ElMessage.warning('请先登录');
      router.push('/user/login');
    }
  })
}

onMounted(() => {
  initLogin();
  initRouter();
})
</script>

<template>
  <RouterView :key="route.fullPath"/>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}


</style>
