<script setup>
import Description from "@/views/problem/solve/compoment/descripe/index.vue";
import CodeEditor from "@/views/problem/solve/compoment/code/CodeEditor.vue";
import {onMounted, ref} from "vue";
import {useSystemStore} from "@/stores/system";

const desc = ref();
const bar = ref();
const main = ref();

onMounted(() => {
  const descEl = desc.value.$el;
  const barEl = bar.value.$el;
  const mainEl = main.value.$el;

  let isMouseDown = false;
  barEl.addEventListener("mousedown", () => {
    isMouseDown = true;
    document.onselectstart = () => false;
    barEl.classList.add('dragging');
  });

  window.addEventListener("mousemove", (e) => {
    if (isMouseDown) {
      const x = e.clientX;
      const descWidth = x - descEl.getBoundingClientRect().left;
      const mainWidth = mainEl.getBoundingClientRect().right - x;
      // 不超出范围
      if (descWidth < 350 || mainWidth < 560) return;
      descEl.style.width = `${descWidth}px`;
      mainEl.style.width = `${mainWidth}px`;
    }
  });

  window.addEventListener("mouseup", () => {
    isMouseDown = false;
    document.onselectstart = () => true;
    barEl.classList.remove('dragging');
  });
});
</script>

<template>
  <el-container class="container">
    <el-aside class="desc" ref="desc">
      <Description/>
    </el-aside>

    <el-aside class="bar" ref="bar"/>

    <el-main class="main" ref="main">
      <CodeEditor/>
    </el-main>

  </el-container>
</template>

<style scoped>
.container {
  padding: 10px;
  background-color: #f5f5f5;
  height: calc(100vh - 60px);
}

.desc {
  width: 35%;
  min-width: 350px;
  height: 100%;
}

.bar {
  width: 10px;
  background-color: #f5f5f5;
}

.bar:hover {
  background-color: #409EFF;
  cursor: e-resize;
}

.bar.dragging {
  background-color: #409EFF;
}

.main {
  width: 60%;
  min-width: 550px;
  padding: 0;
}
</style>