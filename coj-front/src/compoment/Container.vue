<template>
    <el-container class="container">
      <div class="main">
        <slot></slot>
      </div>
    </el-container>
</template>

<style scoped>
.container {
  background-color: #f5f5f5;
  width: 100%;
  height: calc(100vh - 60px);
}

.container .main {
  background-color: white;
  min-width: 400px;
  width: 90%;
  margin: 0 auto;
}
</style>
<script setup>
</script>