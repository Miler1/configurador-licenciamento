import Vue from 'vue'
import VueRouter from 'vue-router'

import { verificaPermissaoPelaRota } from '@/utils/helpers/permissoes/verificador'

Vue.use(VueRouter)

const routes = [
	{
		path: '/', redirect: to => {
			if(true) {
				return '/login'
			}
		}
	},
	{
		path: '/configurador',
		name: 'Configurador',
		component: () => import('../views/MainWrapper.vue'),
		children: [
		  {
				path: 'cnae',
				name: 'Cnae',
				component: () => import('../views/Login.vue')
		  }
		]
	},
	{
		path: '/login',
		name: 'Login',
		component: () => import('../views/Login.vue')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})


// function getPermissaoPelaRota (path, next) {
// 	if (verificaPermissaoPelaRota(path)) {
// 	  next()
// 	} else {
// 	  next(false)
// 	}
// }

export default router
