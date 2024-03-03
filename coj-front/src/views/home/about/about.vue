<script setup>

import {onMounted, ref} from 'vue';
import {getAboutAPI} from "@/apis/about";

const about = ref('');
const defaultInfo = ref("project");
const lazyArr = ref({});

const switchInfo = (tab) => {
  // 添加数据懒加载
  if (!lazyArr.value[tab.paneName]) {
    getAboutAPI(tab.paneName).then(({ data }) => {
      if (data.code === 0) {
        about.value = data.data;
        lazyArr.value[tab.paneName] = data.data;
      } else {
        ElMessage.warning(data.message);
      }
    });
  } else {
    about.value = lazyArr.value[tab.paneName];
  }
}

onMounted(() => {
  // 请求默认的数据
  switchInfo({ paneName: defaultInfo.value });
})

</script>

<template>
  <el-tabs v-model="defaultInfo" @tab-click="switchInfo">
    <el-tab-pane label="关于项目" name="project"></el-tab-pane>
    <el-tab-pane label="作者信息" name="author"></el-tab-pane>
    <el-tab-pane label="数据库设计" name="database"></el-tab-pane>
  </el-tabs>
  <div class="md">
    <el-scrollbar>
      <v-md-preview :text="about" />
    </el-scrollbar>
  </div>
</template>

<style scoped>
.el-tabs {
  height: 60px;
  display: flex;
  position: sticky;
  justify-content: center;
  padding: 20px 0px;
}
.md {
  padding: 10px 20px;
  height: calc(100vh - 120px);
  overflow: auto;
}
</style>