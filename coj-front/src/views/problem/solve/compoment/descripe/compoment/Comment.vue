<script setup>
import {ref, computed} from 'vue';
import {useUserStore} from "@/stores/user";
import {useRoute} from "vue-router";
import {getCommentAPI, uploadCommentAPI} from "@/apis/comment";

const userStore = useUserStore();
const route = useRoute();
const showDrawer = ref(false);

const comment = ref({
  userId: userStore.userinfo.id,
  problemId: route.query.id,
  content: "",
});

const submit = () => {
  uploadCommentAPI(comment.value).then(({data}) => {
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
  getCommentAPI(route.query.id).then(({data}) => {
    if (data.code === 0) {
      marks.value = data.data.sort((a, b) => {
        return new Date(b.createTime) - new Date(a.createTime);
      });
    } else {
      ElMessage.error(data.message);
    }
  })
}

getMarks();

const userNameView = (item) => {
  return item.nickname ? item.nickname : item.account;
};

</script>

<template>
  <el-scrollbar>
    <el-row class="submit">
      <v-md-editor left-toolbar="code quote" right-toolbar="" mode="edit" v-model="comment.content"
                   placeholder="请输入内容..."/>
      <el-tooltip :disabled="comment.content.length!==0" effect="light" placement="top">
        <template #content>
          <el-text>
            请先输入内容....
          </el-text>
        </template>
        <div style="margin-left: auto">
          <el-button @click="showDrawer = true;" :disabled="comment.content.length===0">预览
          </el-button>
          <el-button type="primary" @click="submit" :disabled="comment.content.length===0">提交</el-button>
        </div>
      </el-tooltip>
    </el-row>

    <el-drawer v-model="showDrawer" direction="ltr">
      <v-md-preview :text="comment.content"/>
    </el-drawer>

    <el-empty v-if="marks.length===0">
      <template #description>
        <el-text>
          暂无评论
        </el-text>
      </template>
    </el-empty>

    <el-card v-for="item in marks" :ket="item.id" shadow="hover">
      <el-row style="margin-bottom: 10px">
        <el-text>
          <h3>
            {{ userNameView(item) }}
          </h3>
        </el-text>
        <el-text style="margin-left: auto; font-size: 10px">{{ item.createTime }}</el-text>
      </el-row>
      <v-md-preview :text="item.content" class="previews"/>
    </el-card>
  </el-scrollbar>

</template>

<style scoped>
.el-scrollbar {
  height: calc(100vh - 140px);
  overflow: auto;
}

.submit {
  padding: 10px 10px 0 10px;
}

.el-button {
  margin: 10px 10px;
}

.previews > div {
  padding: 0;
}

.el-card {
  margin: 20px 10px;
}
</style>