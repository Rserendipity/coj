<script setup>
import {ref} from 'vue';
import {getProblemAnswerAPI} from "@/apis/problem";
import {useRoute} from "vue-router";
import {MdPreview} from "md-editor-v3";

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
  <el-empty v-if="code.length===0" description="暂无题解"/>
  <el-scrollbar class="box">
    <div style="calc(100vh - 120px)">
      <MdPreview :modelValue="code" preview-theme="vuepress" :toolbars="[]"/>
    </div>
  </el-scrollbar>

</template>

<style scoped>
.box {
  height: calc(100vh - 120px);
}
</style>