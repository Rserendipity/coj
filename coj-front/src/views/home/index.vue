<script setup>
import OjHeader from "@/compoment/COJ_Header.vue";
import Container from "@/compoment/Container.vue";
import {useRoute} from 'vue-router';

const route = useRoute();

</script>

<template>
  <el-header>
    <OjHeader />
  </el-header>

  <Container>
    <router-view v-slot="{ Component }">
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view>

    <!-- <KeepAlive>
      <RouterView :key="route.fullPath" />
    </KeepAlive> -->
  </Container>
</template>
