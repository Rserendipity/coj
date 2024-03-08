<script setup>
import {ref} from 'vue';
import {MdPreview} from "md-editor-v3";
import Container from "@/compoment/Container.vue";
import {getSubmitDetailAPI} from "@/apis/submit";
import {useRoute} from "vue-router";
import router from '@/router';

const code = ref("");
const route = useRoute()

const getSubmitDetail = async () => {
  let res = await getSubmitDetailAPI(route.query.id);
  res = res.data.data;
  console.log(res);
  code.value = `> 语言：${res.language}\n` +
      "\n" +
      `> 状态：${res.judgeInfo.message} \n` +
      "\n" +
      `> 时间消耗：${res.judgeInfo.useTime} ms\n` +
      "\n" +
      `> 内存消耗：${res.judgeInfo.useMemory} kb\n` +
      "\n" +
      `\`\`\` ${res.language} \n` +
      `${res.code}\n` +
      "```";
}

getSubmitDetail();

</script>

<template>
  <Container>
    <el-scrollbar>
      <MdPreview :modelValue="code" preview-theme="cyanosis"/>
      <el-row>
        <el-button type="primary" @click="router.back();" style="margin-left: auto;">返 回</el-button>
      </el-row>
    </el-scrollbar>
  </Container>
</template>

<style scoped>
.el-scrollbar {
  padding: 20px;
}
</style>