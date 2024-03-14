<script setup>
import {reactive, ref} from 'vue'
import {useUserStore} from '@/stores/user';
import {loginAPI, registerAPI} from '@/apis/user';
import {useRouter} from 'vue-router';
import httpInstance from "@/utils/httpInstance";

const router = useRouter();
const userStore = useUserStore();

const userinfo = reactive({
  account: '',
  password: '',
  confirmPass: '',
})

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
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('密码不能为空'))
  } else if (value !== userinfo.password) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
}

const rules = reactive({
  account: [{validator: checkUsername, trigger: 'blur'}],
  password: [{validator: validatePass, trigger: 'blur'}],
  confirmPass: [{validator: validatePass2, trigger: 'blur'}],
})

const register = () => {
  if (userinfo.account === '' || userinfo.password === '' || userinfo.confirmPass === '') {
    ElMessage.warning("账号或密码为空");
    return;
  }
  if (userinfo.account.length < 4 || userinfo.password.length < 8 || userinfo.password !== userinfo.confirmPass) {
    ElMessage.warning("账号或密码不符合规则");
    return;
  }

  registerAPI(userinfo).then(({data}) => {
    if (data.code === 0) {
      ElMessage.success("注册成功");
      if (autoLogin.value) {
        autoLoginFunc();
      } else {
        router.push('/user/login');
      }
    } else {
      ElMessage.warning(data.message);
    }
  })
}

const autoLogin = ref(true);
const autoLoginFunc = () => {
  delete userinfo.confirmPass;
  loginAPI(userinfo).then(({data}) => {
    httpInstance.defaults.headers.Authorization = data.data.token;
    // 设置token到本地
    localStorage.setItem('token', data.data.token);
    if (data.code === 0) {
      ElMessage.success("登录成功");
      userStore.userinfo = data.data;
      userStore.loginState = true;
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
              <i class="iconfont icon-user"></i>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="" prop="password">
          <el-input v-model="userinfo.password" autocomplete="off" show-password type="password"
                    placeholder="请输入密码">
            <template #prefix>
              <i class="iconfont icon-password"></i>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="" prop="confirmPass">
          <el-input v-model="userinfo.confirmPass" @keyup.enter.native="register" autocomplete="off" show-password
                    type="password" placeholder="请确认密码">
            <template #prefix>
              <i class="iconfont icon-password"></i>
            </template>
          </el-input>
        </el-form-item>

        <el-row>
          <el-checkbox v-model="autoLogin">自动登录</el-checkbox>
          <el-link type="primary" :underline="false" @click="router.push('/user/login')">
            已有账号？点此登录
          </el-link>
        </el-row>

        <el-row>
          <el-button type="primary" @click="register">注 册</el-button>
        </el-row>
      </el-form>
    </div>

  </div>
</template>

<style scoped>
.container {
  height: 100vh;
  width: 100vw;
  background: url('@/assets/background.png') no-repeat fixed;
  background-size: cover;
}

.main {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, calc(-50% - 50px));
  width: 400px;
  padding: 0 40px;
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
