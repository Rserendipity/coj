<script setup>
import Describe from '@/views/problem/solve/compoment/descripe/compoment/Describe.vue';
import Answer from "@/views/problem/solve/compoment/descripe/compoment/Answer.vue";
import Comment from "@/views/problem/solve/compoment/descripe/compoment/Comment.vue";
import Submits from "@/views/problem/solve/compoment/descripe/compoment/Submits.vue";
</script>

<template>
  <el-tabs type="border-card" class="box">
    <el-tab-pane :lazy="true">
      <template #label>
        <i class="iconfont icon-table"/>
        <el-text>描述</el-text>
      </template>
      <Describe/>
    </el-tab-pane>

    <el-tab-pane :lazy="true">
      <template #label>
        <i class="iconfont icon-comment"/>
        <el-text>评论</el-text>
      </template>
      <Comment/>
    </el-tab-pane>

    <el-tab-pane :lazy="true">
      <template #label>
        <i class="iconfont icon-wentijieda"/>
        <el-text>题解</el-text>
      </template>
      <Answer/>
    </el-tab-pane>

    <el-tab-pane :lazy="true">
      <template #label>
        <i class="iconfont icon-pen1"/>
        <el-text>提交</el-text>
      </template>
      <Submits/>
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

.el-text {
  display: inline-block;
  margin-left: 5px;
}

</style>