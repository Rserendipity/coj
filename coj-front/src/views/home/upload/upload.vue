<script setup>

import {computed, nextTick, onMounted, ref} from 'vue';
import {getProblemDetailAPI, updateQuestionAPI} from "@/apis/problem";
import {useRoute} from 'vue-router';
import router from "@/router";
import {useUserStore} from "@/stores/user";
import {MdEditor} from "md-editor-v3";

const userStore = useUserStore();

const question = ref({
  title: "",
  level: "简单",
  description: "",
  tags: [],
  judgeConfig: {
    timeLimit: 1000,
    memoryLimit: 1024,
  },
  judgeCases: [
    {input: '', output: ''},
  ],
  userId: userStore.userinfo.id
});

const inputValue = ref('')
const inputVisible = ref(false)
const InputRef = ref("");
const addTag = computed(() => {
  return question.value.tags.length >= 5;
})

const handleClose = (tag) => {
  question.value.tags.splice(question.value.tags.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value.input.focus()
  })
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    question.value.tags.push(inputValue.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}

const submit = () => {
  updateQuestionAPI(question.value, route.query.id).then(({data}) => {
    if (data.code === 0) {
      if (route.query.id) {
        ElMessage.success("修改成功");
      } else {
        ElMessage.success("添加成功");
      }
      router.push("/");
    } else {
      ElMessage.warning(data.message);
    }
  })
}

const deleteCase = computed(() => {
  return question.value.judgeCases.length === 1
})
const route = useRoute();

onMounted(() => {
  if (route.query.id) {
    getProblemDetailAPI(route.query.id).then(({data}) => {
      if (data.code === 0) {
        question.value = data.data;
      } else {
        ElMessage.warning(data.message);
      }
    })
  }
})

const btnView = computed(() => {
  return route.query.id ? "修改题目" : "添加题目";
})

</script>

<template>
  <el-scrollbar>
    <el-form label-position="left">
      <el-form-item label="标 题" label-width="70px">
        <el-input v-model="question.title"/>
      </el-form-item>

      <el-form-item label="难 度" label-width="70px">
        <el-radio-group v-model="question.level" label="label position">
          <el-radio-button label="简单">简单</el-radio-button>
          <el-radio-button label="中等">中等</el-radio-button>
          <el-radio-button label="困难">困难</el-radio-button>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="标 签" label-width="70px">
        <el-tag v-for="tag in question.tags" size="large" :key="tag" closable :disable-transitions="false"
                @close="handleClose(tag)">
          {{ tag }}
        </el-tag>
        <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="input-new-tag"
                  @keyup.enter="handleInputConfirm" @blur="handleInputConfirm"/>
        <el-tooltip v-else :disabled="!addTag" effect="light" placement="top">
          <template #content>
            <el-text>
              无法新增标签，最多只能添加5个标签
            </el-text>
          </template>
          <el-button class="button-new-tag" @click="showInput" :disabled="addTag">
            新增标签
          </el-button>
        </el-tooltip>

      </el-form-item>

      <el-form-item label="描 述" label-width="70px">
          <MdEditor v-model="question.description" preview-theme="vuepress" class="shadow"  :footers="[]"/>
<!--        <v-md-editor v-model="" height="400px" class="shadow"/>-->
      </el-form-item>

      <el-form-item label="资源限制" label-width="70px">
        <div class="limit shadow">
          <el-row>
            <el-text>
              时间限制(ms)
            </el-text>
            <el-text>
              内存限制(KB)
            </el-text>
          </el-row>

          <el-row>
            <el-input v-model="question.judgeConfig.timeLimit"/>
            <el-input v-model="question.judgeConfig.memoryLimit"/>
          </el-row>
        </div>
      </el-form-item>

      <el-form-item label="测试用例" label-width="70px">
        <div class="case shadow">
          <el-row v-for="(item, index) in question.judgeCases" :key="index">
            <el-aside>
              <el-row style="margin-bottom: 5px">
                <el-text class="text">输入</el-text>
                <el-input v-model="item.input"/>
              </el-row>
              <el-row>
                <el-text class="text">预期输出</el-text>
                <el-input v-model="item.output"/>
              </el-row>
            </el-aside>

            <el-tooltip :disabled="!deleteCase" effect="light" placement="top">
              <template #content>
                <el-text>
                  无法删除，至少有一个测试用例
                </el-text>
              </template>
              <el-button type="danger" @click="question.judgeCases.splice(index, 1)" :disabled="deleteCase" id="delete">
                删除
              </el-button>
            </el-tooltip>
          </el-row>

          <el-row>
            <el-button @click="question.judgeCases.push({ input: '', output: '' })" type="success">
              新增测试用例
            </el-button>
          </el-row>
        </div>
      </el-form-item>
    </el-form>

    <el-button type="primary" class="submit" @click="submit">
      {{ btnView }}
    </el-button>
  </el-scrollbar>
</template>

<style scoped>
.el-form {
  width: 90%;
  margin: 0 auto;
  padding-top: 20px;
}

.el-tag {
  margin-right: 10px;
}

.input-new-tag,
.button-new-tag {
  display: inline-block;
  width: 90px;
}

.limit {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 10px 0;
  border-radius: 4px;
}

.limit .el-row {
  display: flex;
  margin-bottom: 10px;
}

.limit .el-text {
  display: inline-block;
  margin: 0 auto;
}

.limit .el-input {
  width: 40%;
  margin: 0 auto;
}

.case {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 10px 0;
  border-radius: 4px;
}

.case > .el-row {
  display: flex;
  width: 95%;
  margin: 0 auto;
  align-items: center;
}

.case .el-aside {
  width: 90%;
  margin: 10px 0;
}

.case .el-aside .el-row .el-text {
  width: 60px;
}

.case .el-aside .el-row .el-input {
  width: calc(100% - 60px);
  margin: 0 auto;
}

.case #delete {
  margin: 0 5px;
  width: calc(10% - 10px);
}

.case .el-button {
  margin: 0 auto;
}

.shadow {
  box-shadow: 0 0 10px #e1e1e1;
}

.submit {
  display: block;
  margin-left: auto;
  margin-right: 5%;
  margin-bottom: 40px;
}
</style>
