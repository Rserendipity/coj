<script setup>
import {ref} from 'vue';
import {MdPreview} from "md-editor-v3";
import Container from "@/compoment/Container.vue";
import {getSubmitDetailAPI} from "@/apis/submit";
import {useRoute} from "vue-router";
import router from '@/router';
import {useSystemStore} from "@/stores/system";

const code = ref("");
const route = useRoute()
const SystemStore = useSystemStore();
const getSubmitDetail = async () => {
  let res = await getSubmitDetailAPI(route.query.id);
  res = res.data.data;
  code.value = `
> 语言：${res.language}

> 状态：${res.judgeInfo.message}

> 时间消耗：${res.judgeInfo.useTime} ms

> 内存消耗：${res.judgeInfo.useMemory} kb

\`\`\` ${res.judgeInfo.stderr ? "sh" : res.language}
${res.judgeInfo.stderr ? res.judgeInfo.stderr : res.code}
\`\`\`
`;
}

getSubmitDetail();

const back = () => {
  router.back();
}

</script>

<template>
  <Container>
    <el-scrollbar>
      <el-row>
        <el-button type="primary" @click="back()">返 回</el-button>
      </el-row>
      <MdPreview :modelValue="code" preview-theme="cyanosis"/>
    </el-scrollbar>
  </Container>
</template>

<style scoped>
.el-scrollbar {
  padding: 20px;
}
</style>