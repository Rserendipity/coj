<script setup>
import * as monaco from 'monaco-editor';
import {onMounted, ref, toRaw, watch} from 'vue';
import {cppCompletion, javaCompletion} from "@/views/problem/solve/compoment/code/inhint";
import router from "@/router";
import {useRoute} from "vue-router";
import {useSubmitsStore} from "@/stores/submits";
import {useUserStore} from "@/stores/user";
import {getSubmitListAPI, uploadSubmitAPI} from "@/apis/submit";
import {useSystemStore} from "@/stores/system";

const route = useRoute();
const editor = ref(null);
const submitStore = useSubmitsStore();
const userStore = useUserStore();
const systemStore = useSystemStore();


onMounted(() => {
  editor.value = monaco.editor.create(document.querySelector('#codeContainer'), {
    value: langs[0].defaultCode,
    language: 'cpp',
    fontSize: 18,
    theme: 'vs',
    minimap: {
      enabled: false,
    },
    automaticLayout: true,
  });
  cppCompletion
  javaCompletion

  // 加载保存的编程语言
  const localLanguage = localStorage.getItem('userLanguage');
  if (localLanguage) {
    langSelect.value = localLanguage;
  }
  // 加载本地代码
  const localCode = localStorage.getItem(langSelect.value + '@' + route.query.id);
  if (localCode) {
    toRaw(editor.value).setValue(toRaw(localCode));
  }
});

const langs = [
  {
    lang: 'Java',
    value: 'java',
    defaultCode: "public class Main {\n" +
        "    public static void main(String[] args) {\n" +
        "        // TODO 再此编写你的代码\n" +
        "        \n" +
        "    }\n" +
        "}"
  },
  {
    lang: 'C++',
    value: 'cpp',
    defaultCode: "#include <iostream>\n" +
        "using namespace std;\n" +
        "\n" +
        "int main() {\n" +
        "    // TODO 从此处开始编写你的代码\n" +
        "    \n" +
        "    return 0;\n" +
        "}"
  },
]
const langSelect = ref("java");

const themes = [
  {
    lang: '浅色',
    value: 'vs',
  },
  {
    lang: '深色',
    value: 'vs-dark',
  }
]
const themeSelect = ref("vs");
const fontSizeSelect = ref(18);

watch([langSelect, themeSelect, fontSizeSelect], () => {
  toRaw(editor.value).updateOptions({
    fontSize: fontSizeSelect.value,
    theme: themeSelect.value,
    language: langSelect.value
  });
})

watch(langSelect, () => {
  let code = "";
  systemStore.userLanguage = langSelect.value;
  // 保存到本地
  localStorage.setItem('userLanguage', langSelect.value);

  // 加载本地代码
  const localCode = localStorage.getItem(langSelect.value + '@' + route.query.id);
  if (localCode) {
    toRaw(editor.value).setValue(toRaw(localCode));
  } else {
    langs.forEach((i) => {
      if (i.value === langSelect.value) {
        code = i.defaultCode;
      }
    });
    toRaw(editor.value).setValue(toRaw(code));
  }

  // langs.forEach((i) => {
  //   if (i.value === langSelect.value) {
  //     code = i.defaultCode;
  //   }
  // });
  // toRaw(editor.value).setValue(toRaw(code));
})

// 保存代码到本地
document.onkeydown = function (e) {
  if (e.ctrlKey && e.key === 's') {
    e.preventDefault();

    const code = toRaw(editor.value).getValue();
    localStorage.setItem(langSelect.value + '@' + route.query.id, code);

    ElMessage.success('已保存');
  }
}


const submit = () => {
  let submit = {
    "code": toRaw(editor.value).getValue(),
    "language": langSelect.value,
    "problemId": route.query.id,
    "userId": userStore.userinfo.id
  }
  uploadSubmitAPI(submit).then(({data}) => {
    if (data.code === 0) {
      ElMessage.success("提交成功");
      getSubmitListAPI(userStore.userinfo.id, route.query.id).then(({data}) => {
        if (data.code === 0) {
          submitStore.submits = data.data.sort((a, b) => new Date(b.time) - new Date(a.time));
        } else {
          ElMessage.warning(data.message);
        }
      });
    } else {
      ElMessage.error(data.message);
    }
  });
}

const reset = () => {
  langs.forEach((i) => {
    if (i.value === langSelect.value) {
      toRaw(editor.value).setValue(toRaw(i.defaultCode));
    }
  });
}

</script>

<template>
  <el-row class="option">
    <el-text style="margin-left: 10px;">选择语言：</el-text>
    <el-select v-model="langSelect" style="width: 80px">
      <el-option v-for="item in langs" :key="item.lang" :label="item.lang" :value="item.value"/>
    </el-select>

    <el-button @click="reset()" style="margin-left: auto">重置代码</el-button>

    <el-text style="margin-left: 10px">切换主题：</el-text>
    <el-select v-model="themeSelect" style="width: 80px">
      <el-option v-for="item in themes" :key="item.lang" :label="item.lang" :value="item.value"/>
    </el-select>

    <el-text style="margin-left: 10px">切换字体大小：</el-text>
    <el-select v-model="fontSizeSelect" style="width: 80px">
      <el-option v-for="item in [10, 12, 14, 16, 18, 20, 22, 24, 26]" :key="item" :label="item" :value="item"/>
    </el-select>
  </el-row>

  <div id="codeContainer"></div>

  <el-row class="submit">
    <el-button style="margin-left: auto;" @click="router.back()">返 回</el-button>
    <el-button style="margin-right: 20px" type="primary" @click="submit">提 交</el-button>
  </el-row>
</template>

<style scoped>
.option {
  height: 40px;
  display: flex;
  align-items: center;
}

#codeContainer {
  width: 100%;
  height: calc(100% - 100px);
}

.submit {
  margin-top: 10px;
  height: 50px;
  background-color: #fff;
  align-items: center;
}
</style>