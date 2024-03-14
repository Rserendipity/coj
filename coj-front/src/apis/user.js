import httpInstance from "@/utils/httpInstance";

export function loginAPI(userinfo) {
  return httpInstance.post('/user/login', userinfo);
}

export function registerAPI(userinfo) {
  return httpInstance.post('/user/register', userinfo);
}

export function getUserInfoAPI() {
  return httpInstance.get('/user/userinfo');
}

export function updateUserInfoAPI(userinfo) {
  return httpInstance.post('/user/update_info', userinfo);
}

export function updatePasswordAPI(password) {
  return httpInstance.post('/user/update_password', password);
}