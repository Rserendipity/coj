<script setup>

import {computed} from "vue";
import router from "@/router";
import checkAccess from "@/common/checkAccess";
import roleEnum from "@/common/roleEnum";
import {useUserStore} from "@/stores/user";
import {useRoute} from "vue-router";
import httpInstance from "@/utils/httpInstance";

const routerView = computed(() => {
  return router.getRoutes().filter((item) => {
    if (item.meta?.hidden) {
      return false;
    }
    if (item.path.startsWith('/home') && item.path !== '/home') {
      return checkAccess(userStore.userinfo.role, item?.meta?.role ?? roleEnum.NOT_LONGIN)
    } else {
      return false;
    }
  });
});

const userStore = useUserStore();
const userNameView = computed(() => {
  let name = "";
  if (userStore.userinfo.nickname) {
    name = userStore.userinfo.nickname;
  } else {
    name = userStore.userinfo.account;
  }
  if (name.length > 15) {
    return name.slice(0, 15) + "...";
  } else {
    return name;
  }
});
const route = useRoute();

const submenuFuncMapper = {
  info() {
    router.push('/user/info');
  },
  logout() {
    userStore.userinfo.id = -1;
    userStore.userinfo.role = roleEnum.NOT_LONGIN;
    localStorage.removeItem('token');
    httpInstance.defaults.headers.Authorization = "";
    router.push('/user/login');
  }
}

const menuFuncMapper = {
  question() {
    router.push('/home/question');
  },
  submit() {
    router.push('/home/submit');
  },
  upload() {
    router.push('/home/upload');
  },
  edit() {
    router.push('/home/edit');
  },
  about() {
    router.push('/home/about');
  }
}

const menuHandler = (key, keyPath) => {
  if (keyPath[0] === "999") {
    submenuFuncMapper[key]();
  } else {
    menuFuncMapper[key]();
  }
}

</script>

<template>
  <el-menu :default-active="route.path.slice(6)" :ellipsis="false" class="menu" mode="horizontal" @select="menuHandler">
    <el-text style="margin-right: 10px;" type="primary">
      <h2>COJ</h2>
    </el-text>

    <el-menu-item v-for="route in routerView" :index="route.path.slice(6)">{{ route.name }}</el-menu-item>

    <el-sub-menu class="user" index="999">
      <template #title>欢迎：{{ userNameView }}</template>
      <el-menu-item index="info">个人中心</el-menu-item>
      <el-menu-item id="logout" index="logout">退出登录</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<style scoped>
.menu {
  display: flex;
  flex-direction: row;
  width: 100%;
  position: sticky;
}

.menu .user {
  margin-left: auto;
  text-align: right;
}

#logout:hover {
  color: rgb(245, 108, 108);
  background-color: rgb(254, 240, 240);
}
</style>