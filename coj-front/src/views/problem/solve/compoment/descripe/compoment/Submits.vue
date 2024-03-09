<script setup>

import {getSubmitListAPI} from "@/apis/submit";
import {useRoute} from "vue-router";
import {useUserStore} from "@/stores/user";
import {useSubmitsStore} from "@/stores/submits";
import router from "@/router";

const submitStore = useSubmitsStore();
const route = useRoute();
const userStore = useUserStore();

getSubmitListAPI(userStore.userinfo.id, route.query.id).then(({data}) => {
  if (data.code === 0) {
    submitStore.submits = data.data.sort((a, b) => new Date(b.time) - new Date(a.time));
  } else {
    ElMessage.warning(data.message);
  }
});

const stateToMessage = [
  "等待判题",
  "判题中",
  "通过",
  "答案错误",
  "时间超限",
  "内存超限",
  "编译报错",
  "运行报错",
  "系统错误"
]

const tagsView = (state) => {
  switch (state) {
    case 0:
      return "info"
    case 1:
      return "warning"
    case 2:
      return "success"
    default:
      return "danger"
  }
};

const timeChange = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString();
}

const click = (row) => {
  router.push({
    path: "/problem/detail",
    query: {
      id: row.id
    }
  })
}

</script>

<template>
  <el-row class="submits">
    <el-empty description="暂无提交记录" v-if="submitStore.submits.length===0" style="margin: 0 auto;"/>
    <el-table :data="submitStore.submits" height="calc(100vh - 140px)" @row-click="click" v-else>
      <el-table-column prop="state" label="状态" width="120px">
        <template v-slot="{ row }">
          <el-row>
            <el-tag :type="tagsView(row.state)">
              {{ stateToMessage[row.state] }}
            </el-tag>
          </el-row>
          <el-row>
            <el-text style="font-size: 10px">
              {{ timeChange(row.time) }}
            </el-text>
          </el-row>
        </template>
      </el-table-column>

      <el-table-column prop="language" label="语言" min-width="60px"/>

      <el-table-column prop="use_time" label="消耗时间">
        <template v-slot="{ row }">
          <div class="use">
            <i class="iconfont icon-time"/>
            <el-text v-if="row.judgeInfo.state !== 2">N/A</el-text>
            <el-text v-else>{{ row.judgeInfo.useTime }} ms</el-text>
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="use_memory" label="消耗内存">
        <template v-slot="{ row }">
          <div class="use">
            <i class="iconfont icon-cpu"/>
            <el-text v-if="row.judgeInfo.state !== 2">N/A</el-text>
            <el-text v-else>{{ row.judgeInfo.useMemory }} kb</el-text>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-row>


</template>

<style scoped>
.use {
  width: 90px;
}

.use .el-text {
  display: inline-block;
  margin-left: 5px;
}

</style>