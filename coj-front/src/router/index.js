import {createRouter, createWebHashHistory} from 'vue-router'
import RoleEnum from "@/common/roleEnum";

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home/question',
    },
    {
      path: '/home',
      component: () => import('@/views/home/index.vue'),
      children: [
        {
          path: 'question',
          name: '题目列表',
          component: () => import('@/views/home/question/question.vue')
        },
        {
          path: 'upload',
          name: '上传题目',
          meta: {
            role: RoleEnum.ADMIN,
          },
          component: () => import('@/views/home/upload/upload.vue')
        },
        {
          path: 'answer',
          name: '答案信息',
          meta: {
            role: RoleEnum.ADMIN,
            hidden: true
          },
          component: () => import('@/views/home/answer/answer.vue')
        },
        {
          path: 'about',
          name: '关于项目',
          component: () => import('@/views/home/about/about.vue')
        },

      ]
    },
    {
      path: '/user',
      children: [
        {
          path: '',
          redirect: '/user/login',
        },
        {
          path: 'login',
          name: 'login',
          component: () => import('@/views/user/login/login.vue')
        },
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/user/register/register.vue')
        },
        {
          path: 'info',
          name: 'info',
          component: () => import('@/views/user/info/info.vue')
        }

      ]
    },
    {
      path: '/problem',
      component: () => import('@/views/problem/index.vue'),
      children: [
        {
          path: 'solve',
          name: 'solve',
          component: () => import('@/views/problem/solve/solve.vue'),
        },
        {
          path: 'detail',
          name: 'detail',
          component: () => import('@/views/problem/detail/detail.vue'),
        }
      ]
    },
    {
      path: '/forbidden',
      component: () => import('@/views/forbidden/index.vue')
    }
  ]
})

export default router
