<script setup>
import {ref} from 'vue';
import {useUserStore} from "@/stores/user";
import {useRoute} from "vue-router";
import {getCommentAPI, uploadCommentAPI} from "@/apis/comment";
import {MdEditor, MdPreview} from "md-editor-v3";

const userStore = useUserStore();
const route = useRoute();
const showDrawer = ref(false);

const comment = ref({
  userId: userStore.userinfo.id,
  problemId: route.query.id,
  content: "",
});

const submit = () => {
  uploadCommentAPI(comment.value).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("提交成功");
      getMarks();
      comment.value.content = "";
    } else {
      ElMessage.error("提交失败");
    }
  })
}
const marks = ref([]);

const getMarks = () => {
  getCommentAPI(route.query.id).then(({ data }) => {
    if (data.code === 0) {
      marks.value = data.data.sort((a, b) => {
        return new Date(b.createTime) - new Date(a.createTime);
      }).map(mark => ({...mark, isCollapsed: false}));
    } else {
      ElMessage.error(data.message);
    }
  })
}

getMarks();

const userNameView = (item) => {
  return item.nickname ? item.nickname : item.account;
};

const isCollapsed = ref(true);
const collapsedText = (isCollapsed) => {
  return isCollapsed ? "收起" : "展开";
}

const isContentOverflow = (content) => {
  const arr = content.match(/\n/g);
  return arr?.length >= 7 ?? false;
};

</script>

<template>
  <el-scrollbar>
    <el-row class="submit">

      <MdEditor v-model="comment.content" placeholder="请输入评论..." :toolbars="['code', 'quote']" :preview="false"
        class="edit" :footers="[]" />

      <el-tooltip :disabled="comment.content.length !== 0" effect="light" placement="top">
        <template #content>
          <el-text>请先输入内容....</el-text>
        </template>
        <div style="margin-left: auto">
          <el-button @click="showDrawer = true;" :disabled="comment.content.length === 0">预览
          </el-button>
          <el-button type="primary" @click="submit" :disabled="comment.content.length === 0">提交</el-button>
        </div>
      </el-tooltip>
    </el-row>

    <el-drawer v-model="showDrawer" direction="ltr">
      <MdPreview :modelValue="comment.content" preview-theme="vuepress" />
    </el-drawer>

    <el-empty v-if="marks.length === 0">

      <template #description>
        <el-text>
          暂无评论
        </el-text>
      </template>
    </el-empty>

    <el-card v-for="item in marks" :ket="item.id" shadow="hover">
      <el-row style="margin-bottom: 10px">
        <el-avatar :src="item.avatar" :size="20"/>
        <el-text style="margin-left: 10px">
          <h3>
            {{ userNameView(item) }}
          </h3>
        </el-text>
        <el-text style="margin-left: auto; font-size: 10px">{{ item.createTime }}</el-text>
      </el-row>

      <div class="content" :class="{ collapsed: item.isCollapsed }">
        <MdPreview :modelValue="item.content" class="previews" preview-theme="vuepress" />
      </div>
      <div style="text-align: center;">
        <el-button v-show="isContentOverflow(item.content)" type="primary" @click="item.isCollapsed = !item.isCollapsed">{{ collapsedText(item.isCollapsed) }}</el-button>
      </div>
    </el-card>

  </el-scrollbar>

</template>

<style scoped>
.content {
  max-height: 200px;
  overflow: hidden;
}

.content.collapsed {
  max-height: none;
}

.submit {
  display: flex;
  flex-direction: column;
}

.edit {
  height: 250px;
}

.el-scrollbar {
  height: calc(100vh - 140px);
  overflow: auto;
}

.el-button {
  margin: 10px 10px;
}

.el-card {
  margin: 20px 10px;
}
</style>