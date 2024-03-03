<script setup>

import COJ_Header from "@/compoment/COJ_Header.vue";
import Container from "@/compoment/Container.vue";

import { ref, reactive } from "vue";
import { getUserInfoAPI, updateUserInfoAPI, updatePasswordAPI } from "@/apis/user";
import { useUserStore } from "@/stores/user";
import roleEnum from "@/common/roleEnum";
import httpInstance from "@/utils/httpInstance";
import router from "@/router";

const userStore = useUserStore();

const form = reactive({
  account: userStore.userinfo.account,
  nickname: userStore.userinfo.nickname,
  rawPass: "",
  newPass: "",
  confirmPass: "",
  avatar: "",
});

const submit = () => {
  updateUserInfoAPI(form).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("修改成功");
      getUserInfoAPI().then(({ data }) => {
        userStore.userinfo = data.data;
        form.account = data.data.account;
        form.nickname = data.data.nickname;
        form.rawPass = "";
        form.newPass = "";
        form.confirmPass = "";
        form.avatar = "";
      });
    } else {
      ElMessage.warning(data.message);
    }
  })
};

const logout = () => {
  userStore.userinfo.id = -1;
  userStore.userinfo.role = roleEnum.NOT_LONGIN;
  localStorage.removeItem('token');
  httpInstance.defaults.headers.Authorization = "";
  router.push('/');
}

const submitPassword = () => {
  updatePasswordAPI(form).then(({ data }) => {
    if (data.code === 0) {
      ElMessage.success("修改成功");
      ElMessage.warning("请重新登录");
      logout();
    } else {
      ElMessage.warning(data.message);
    }
  })
};

const info = ref("info");


const checkPass = (rule, value, callback) => {
  if (value === '') {
    return callback(new Error('密码不能为空'))
  }
  if (value.length < 8) {
    callback(new Error('密码长度小于8'))
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
  } else if (value !== form.newPass) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
};

const rules = reactive({
  rawPass: [{ validator: checkPass, trigger: 'blur' }],
  newPass: [{ validator: validatePass, trigger: 'blur' }],
  confirmPass: [{ validator: validatePass2, trigger: 'blur' }],
});

</script>

<template>
  <el-header>
    <COJ_Header />
  </el-header>

  <Container>
    <el-tabs class="el-tabs" type="border-card" v-model="info">
      <el-tab-pane label="个人信息" name="info">
        <el-form label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="form.account" disabled></el-input>
          </el-form-item>

          <el-form-item label="昵称">
            <el-input v-model="form.nickname" @keyup.enter.native="submit" maxlength="10" show-word-limit></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submit">更 改</el-button>
          </el-form-item>

        </el-form>
      </el-tab-pane>

      <el-tab-pane label="修改密码" name="password">
        <el-form label-width="100px" :rules="rules" :model="form">
          <el-form-item label="旧密码" prop="rawPass">
            <el-input v-model="form.rawPass" autocomplete="off" show-password type="password"></el-input>
          </el-form-item>

          <el-form-item label="新密码" prop="newPass">
            <el-input v-model="form.newPass" autocomplete="off" show-password type="password"></el-input>
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPass">
            <el-input v-model="form.confirmPass" autocomplete="off" show-password type="password"
              @keyup.enter.native="submitPassword" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitPassword">修 改</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="上传头像" name="avatar">
        <el-form label-width="100px">
          <el-text>
            开发中...
          </el-text>
          
          <!-- <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="form.avatar" :src="form.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="uploadAvatar">上 传</el-button>
          </el-form-item> -->
        </el-form>
      </el-tab-pane>


    </el-tabs>
  </Container>
</template>

<style scoped>
.el-tabs {
  width: 500px;
  margin: 0 auto;
}
.el-input {
  width: 300px;
}
</style>