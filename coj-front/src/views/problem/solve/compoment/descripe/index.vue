<script setup>
import Describe from '@/views/problem/solve/compoment/descripe/compoment/Describe.vue';
import Answer from "@/views/problem/solve/compoment/descripe/compoment/Answer.vue";
import Comment from "@/views/problem/solve/compoment/descripe/compoment/Comment.vue";

</script>

<template>
  <el-tabs type="border-card" class="box">
    <el-tab-pane label="题目描述">
      <Describe/>
    </el-tab-pane>

    <el-tab-pane label="评论">
      <Comment/>
    </el-tab-pane>

    <el-tab-pane label="题解">
      <Answer/>
    </el-tab-pane>

    <el-tab-pane label="提交记录">
      提交记录
    </el-tab-pane>
  </el-tabs>

</template>

<style scoped>
.box {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #fff;
}

</style>