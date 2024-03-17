<script setup>

import COJ_Header from "@/compoment/COJ_Header.vue";
import Container from "@/compoment/Container.vue";

import {reactive, ref} from "vue";
import {getUserInfoAPI, updatePasswordAPI, updateUserInfoAPI} from "@/apis/user";
import {useUserStore} from "@/stores/user";
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
  updateUserInfoAPI(form).then(({data}) => {
    if (data.code === 0) {
      ElMessage.success("修改成功");
      getUserInfoAPI().then(({data}) => {
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
  updatePasswordAPI(form).then(({data}) => {
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
  rawPass: [{validator: checkPass, trigger: 'blur'}],
  newPass: [{validator: validatePass, trigger: 'blur'}],
  confirmPass: [{validator: validatePass2, trigger: 'blur'}],
});

const reloadUserInfo = async () => {
  const ret = await getUserInfoAPI();
  if (ret.data.code === 0) {
    userStore.userinfo = ret.data.data;
  }
}

const checkImageInfo = (rawFile) => {
  if (rawFile.size / 1024 / 1024 > 1) {
    ElMessage.warning("图片太大")
    return false;
  }
  if (!['image/jpeg', 'image/png'].includes(rawFile.type)) {
    ElMessage.warning("图片类型不支持")
    return false;
  }

  return true;
}

</script>

<template>
  <el-header>
    <COJ_Header/>
  </el-header>

  <Container>
    <el-tabs class="el-tabs" v-model="info" tab-position="left">
      <el-tab-pane label="个人信息" name="info">
        <el-form label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="form.account" disabled></el-input>
          </el-form-item>

          <el-form-item label="昵称">
            <el-input v-model="form.nickname" @keyup.enter.native="submit" maxlength="15" show-word-limit></el-input>
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
                      @keyup.enter.native="submitPassword"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitPassword">修 改</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="修改头像" name="avatar">
        <!-- 当前头像-->
        <div class="avatar">
          <el-image :src="userStore.userinfo.avatar" fit="cover" class="avatar"
                    @error="reloadUserInfo"/>
        </div>

        <el-form label-width="200px" style="padding: 0 50px 0 40px">
          <el-upload
              class="upload-demo"
              drag
              action="api/user/update_avatar"
              name="file"
              :headers="{'Authorization': httpInstance.defaults.headers.Authorization}"
              :on-success="response => userStore.userinfo.avatar = response.data.data"
              :before-upload="checkImageInfo"
          >
            <div style="margin-bottom: 10px">
              <i class="iconfont icon-shangchuan" style="font-size: 40px"/>
            </div>
            <div class="el-upload__text">
              拖拽文件到此处 或<em>点此上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                仅支持 JPG / PNG 文件，且不超过 1Mb
              </div>
            </template>
          </el-upload>

        </el-form>
      </el-tab-pane>
    </el-tabs>
  </Container>
</template>

<style scoped>
.el-tabs {
  height: 100%;
  width: 100%;
  margin: 0 auto;
}

.el-input {
  width: 300px;
}

.avatar {
  display: flex;
  margin: 30px 0;
}

.avatar > .el-image {
  margin: 0 auto;
  width: 100px;
  height: 100px;
}

.avatar > .el-text {
  margin: 0 auto;
  padding: 50px;
}

</style>