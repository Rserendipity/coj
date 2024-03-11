<script setup>

import {onMounted, ref} from 'vue';
import {getAboutAPI} from "@/apis/about";
import {MdPreview} from "md-editor-v3";

const about = ref('');
const defaultInfo = ref("describe");
const lazyArr = ref({});

const switchInfo = (tab) => {
  // 添加数据懒加载
  if (!lazyArr.value[tab.paneName]) {
    getAboutAPI(tab.paneName).then(({data}) => {
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
  switchInfo({paneName: defaultInfo.value});
})

</script>

<template>
  <el-container>
    <el-aside class="tab">
      <el-tabs v-model="defaultInfo" @tab-click="switchInfo" tab-position="left">
        <el-tab-pane label="项目概述" name="describe"></el-tab-pane>
        <el-tab-pane label="安装运行" name="install"></el-tab-pane>
        <el-tab-pane label="作者简历" name="resume"></el-tab-pane>
        <el-tab-pane label="数据库设计" name="database"></el-tab-pane>
      </el-tabs>
    </el-aside>

    <el-main class="md">
      <el-scrollbar>
        <MdPreview :modelValue="about" preview-theme="vuepress"/>
      </el-scrollbar>
    </el-main>
  </el-container>


</template>

<style scoped>
.tab {
  width: 120px;
  height: calc(100vh - 60px);
}

.tab .el-tabs {
  height: 100%;
}

.md {
  padding: 0 10px;
  width: calc(100% - 120px);
  height: calc(100vh - 60px);
  overflow: auto;
}
</style>