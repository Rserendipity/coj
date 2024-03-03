<script setup>
import {ref} from 'vue';
import {getProblemAnswerAPI} from "@/apis/problem";
import {useRoute} from "vue-router";

const code = ref("");
const route = useRoute();

getProblemAnswerAPI(route.query.id).then(({data}) => {
  if (data.code === 0) {
    code.value = data.data ? data.data : "";
  } else {
    ElMessage.warning(data.message);
  }
});
</script>

<template>
  <el-empty v-if="code.length===0">
    <template #description>
      <el-text>暂无题解</el-text>
    </template>
  </el-empty>
  <el-scrollbar class="box">
    <div style="calc(100vh - 120px)">
      <v-md-preview :text="code"/>
    </div>
  </el-scrollbar>

</template>

<style scoped>
.box {
  height: calc(100vh - 120px);
}
</style>