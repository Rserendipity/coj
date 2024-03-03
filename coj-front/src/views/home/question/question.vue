<script setup>
import router from "@/router";
import {useUserStore} from "@/stores/user";
import {useSystemStore} from "@/stores/system";
import {useProblemStore} from "@/stores/problem";
import roleEnum from "@/common/roleEnum";
import {deleteProblemAPI, getProblemListAPI} from "@/apis/problem";
import {ref, onMounted, onUnmounted} from 'vue';

const userStore = useUserStore();
const useProblem = useProblemStore();

const levelView = (level) => {
  switch (level) {
    case "简单":
      return "success";
    case "中等":
      return "warning";
    case "困难":
      return "danger";
    default:
      return "info";
  }
};

const solve = (id) => {
  systemStore.loading.question = true;
  router.push({
    path: '/problem/solve',
    query: {
      id: id,
    }
  })
}

const modify = (id) => {
  router.push({
    path: '/home/upload',
    query: {
      id: id
    }
  })
}

const answer = (id) => {
  router.push({
    path: '/home/answer',
    query: {
      id: id
    }
  })
}

const del = (id) => {
  deleteProblemAPI(id).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("删除成功");
      getProblems();
    } else {
      ElMessage.warning(data.message);
    }
  })
}

const getProblems = () => {
  getProblemListAPI().then(({ data }) => {
    if (data.code === 0) {
      useProblem.problems = data.data;
    } else {
      ElMessage.warning(data.message);
    }
  })
}

const systemStore = useSystemStore();

onMounted(() => {
  if (useProblem.problems.length === 0) {
    getProblems();
  }
  systemStore.loading.question = false;
})

onUnmounted(() => {
  systemStore.loading.question = false;
})

</script>

<template>
  <el-scrollbar v-loading="systemStore.loading.question" element-loading-text="题目加载中，请耐心等待...">
    <el-table :data="useProblem.problems" stripe style="width: 100%" >
      <template #empty>
        <el-empty description="没有题目...请联系管理员添加题目">
        </el-empty>
      </template>

      <el-table-column label="ID" prop="id" width="150">
        <template #default="{ row }">
          <el-tag>{{ String(row.id).slice(-8) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="题目" prop="title" width="200">
        <template #default="{ row }">
          <el-link @click="solve(row.id)" :underline="false">
            {{ row.title }}
          </el-link>
        </template>
      </el-table-column>

      <el-table-column label="难度" prop="level" width="80">
        <template #default="{ row }">
          <el-tag :type="levelView(row.level)"> {{ row.level }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="标签" prop="tags" width="300">
        <template #default="{ row }">
          <el-tag v-for="tag in row.tags">
            {{ tag }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="通过数" min-width="100" prop="pass">
        <template #default="{ row }">
          {{ row.pass }}
        </template>
      </el-table-column>


      <el-table-column v-if="userStore.userinfo.role === roleEnum.ADMIN" label="管理" min-width="50">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="modify(row.id)">修改</el-button>
          <el-button size="small" type="primary" @click="answer(row.id)">题解</el-button>
          <el-popconfirm title="确定要删除吗？" confirm-button-text="是" cancel-button-text="否" @confirm="del(row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

  </el-scrollbar>
</template>


<style scoped>
.el-tag {
  margin-right: 10px;
}
</style>