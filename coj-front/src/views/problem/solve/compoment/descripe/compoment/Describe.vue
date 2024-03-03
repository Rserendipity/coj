<script setup>
import { computed, ref } from 'vue';
import { getProblemDetailAPI } from "@/apis/problem";
import { useRoute } from "vue-router";

const route = useRoute();

const problem = ref({});

const levelView = computed(() => {
  switch (problem.value.level) {
    case "简单":
      return "success";
    case "中等":
      return "warning";
    case "困难":
      return "danger";
    default:
      return "info";
  }
})

getProblemDetailAPI(route.query.id).then(({ data }) => {
  if (data.code === 0) {
    problem.value = data.data;
  } else {
    ElMessage.warning(data.message);
  }
});
</script>


<template>
  <el-row class="info">
    <el-text>
      <h1>
        {{ problem?.title }}
      </h1>
      <div class="tag">
        <el-tag :type="levelView">
          {{ problem?.level }}
        </el-tag>
        <el-tag v-for="tag in problem.tags" :key="tag">
          {{ tag }}
        </el-tag>
      </div>
    </el-text>
  </el-row>

  <el-row class="md">
    <el-scrollbar>
      <v-md-preview :text="problem?.description" />
    </el-scrollbar>
  </el-row>

  <div class="limit">
    <el-row>
      <el-text>
        时间限制：
        <el-tag type="warning">
          {{ problem.judgeConfig?.timeLimit }}
        </el-tag>
        ms
      </el-text>
    </el-row>
    <el-row>
      <el-text>
        内存限制：
        <el-tag type="warning">
          {{ problem.judgeConfig?.memoryLimit }}
        </el-tag>
        kb
      </el-text>
    </el-row>

  </div>
</template>

<style scoped>
.info {
  height: 100px;
  background-color: #fff;
}

.info h1 {
  font-size: 24px;
  padding-bottom: 10px;
}

.info h2 {
  font-size: 16px;
}

.tag {
  display: flex;
  width: 100%;
  flex-direction: row;
  align-items: center;
}

.tag>.el-tag {
  margin-right: 10px;
}

.md {
  height: calc(100vh - 300px);
  overflow: auto;
}

.limit {
  padding: 10px;
}
</style>