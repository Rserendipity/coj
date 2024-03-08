<script setup>
import {reactive, ref} from "vue";
import router from "@/router/index.js";
import {loginAPI} from "@/apis/user.js";
import {useUserStore} from "@/stores/user.js";
import httpInstance from "@/utils/httpInstance";

const userStore = useUserStore();

const userinfo = reactive({
  account: '',
  password: '',
})

const member = ref(true);

const checkUsername = (rule, value, callback) => {
  if (value === '') {
    return callback(new Error('账号不能为空'))
  }
  if (value.length < 4) {
    callback(new Error('账号长度小于4'))
  }
}
const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('密码不能为空'))
  }
  if (value.length < 8) {
    callback(new Error('密码长度小于8'))
  }
}

const rules = reactive({
  account: [{ validator: checkUsername, trigger: 'blur' }],
  password: [{ validator: validatePass, trigger: 'blur' }],
})

const login = () => {
  if (userinfo.account === '' || userinfo.password === '') {
    ElMessage.warning("账号或密码为空");
    return;
  }

  if (userinfo.account.length < 4 || userinfo.password.length < 8) {
    ElMessage.warning("账号或密码不符合规则");
    return;
  }

  loginAPI(userinfo).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("登录成功");
      // 在实例上挂在token
      httpInstance.defaults.headers.Authorization = data.data.token;
      if (member.value) {
        // 设置token到本地
        localStorage.setItem('token', data.data.token);
      }
      userStore.userinfo = data.data;
      router.push('/');
    } else {
      ElMessage.warning(data.message);
    }
  })
}

</script>

<template>
  <div class="container">
    <div class="main">
      <el-form :model="userinfo" :rules="rules" label-width="0px">
        <el-text type="primary">
          <h1>Code-Online-Judge</h1>
        </el-text>

        <el-form-item label="" prop="account">
          <el-input v-model="userinfo.account" placeholder="请输入账号">
            <template #prefix>
              <i class="iconfont icon-user" />
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="" prop="password">
          <!-- 点击enter时也触发登录 -->
          <el-input v-model="userinfo.password" @keyup.enter.native="login" autocomplete="off" show-password
            type="password" placeholder="请输入密码">
            <template #prefix>
              <i class="iconfont icon-password"></i>
            </template>
          </el-input>

        </el-form-item>


        <el-row>
          <el-checkbox v-model="member">记住我</el-checkbox>
          <el-link type="primary" :underline="false" @click="router.push('/user/register')">
            没账号？点此注册
          </el-link>
        </el-row>

        <el-row>
          <el-button type="primary" @click="login">登 录</el-button>
        </el-row>
      </el-form>

    </div>
  </div>
</template>

<style scoped>
.container {
  height: 100vh;
  width: 100vw;
  background: url(@/assets/background.png) no-repeat fixed;
  background-size: cover;
}

.main {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, calc(-50% - 50px));
  padding: 0 40px;
  width: 400px;
  background-color: rgb(255, 255, 255, 0.7);
  border-radius: 10px;
}

h1 {
  text-align: center;
  padding: 20px 0
}

.el-link {
  margin-left: auto;
}

.el-button {
  width: 320px;
  margin-top: 10px;
  margin-bottom: 40px;
}
</style>
