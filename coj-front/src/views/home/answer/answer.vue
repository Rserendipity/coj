<script setup>
import { computed, ref } from 'vue';
import { useRoute } from "vue-router";
import { getProblemAnswerAPI, uploadProblemAnswerAPI } from "@/apis/problem";
import { useProblemStore } from '@/stores/problem';
import router from "@/router";

const route = useRoute();
const code = ref("");
const problemStore = useProblemStore();

getProblemAnswerAPI(route.query.id).then(({ data }) => {
  if (data.code === 0) {
    code.value = data.data ? data.data : "";
  } else {
    ElMessage.warning(data.message);
  }
});

const name = computed(() => {
  return problemStore.problems.find(item => item.id === route.query.id)?.title;
});

const submit = () => {
  uploadProblemAnswerAPI({ id: route.query.id, answer: code.value }).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("提交成功");
      router.push('/home/question');
    } else {
      ElMessage.warning(data.message);
    }
  });
}

</script>

<template>
  <el-scrollbar class="box">
    <el-row>
      <el-col>
        <el-text>
          <h3>题目id：{{ route.query.id }}</h3>
        </el-text>
      </el-col>
      <el-col>
        <el-text>
          <h3>题目名：{{ name }}</h3>
        </el-text>
      </el-col>
    </el-row>
    <el-row style="padding: 20px">
      <v-md-editor v-model="code" placeholder="输入参考答案..." />
    </el-row>
    <el-row>
      <el-button type="primary" @click="submit">提 交</el-button>
    </el-row>
  </el-scrollbar>
</template>

<style scoped>
.box {
  padding: 0 20px;
}

.el-row {
  margin-bottom: 20px;
}

.el-button {
  margin-top: 20px;
  margin-left: auto;
}
</style>