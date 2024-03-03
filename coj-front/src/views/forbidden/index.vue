<script setup>
import OjHeader from "@/compoment/COJ_Header.vue";
import router from "@/router";
</script>

<template>
  <el-header>
    <OjHeader/>
  </el-header>

  <el-container>
    <el-text>
      <h1>
        此页面不存在 或 没有权限访问此页面
        <el-link type="primary" @click="router.push('/')">点此返回主页面</el-link>
      </h1>
    </el-text>
  </el-container>
</template>

<style scoped>

h1 {
  text-align: center;
}

</style>
