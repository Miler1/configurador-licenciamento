import Vue from 'vue'
import VueRouter from 'vue-router'

import { verificaPermissaoPelaRota } from '@/utils/helpers/permissoes/verificador'
import { GET_USUARIO } from '../store/actions.type'
import Index from '@/store/index.js'

Vue.use(VueRouter)

const routes = [
	{
		path: '/', 
		redirect: to => {
			return '/configurador'
		}
	},

	{
		path: '/configurador',
		name: 'Configurador',

		beforeEnter: (to, from, next) => {
			BuscaUsuarioLogado(next)
		},

		redirect: to => {
			return '/configurador/cnae'
		},

		component: () => import('../views/MainWrapper.vue'),

		children: [
			{
				path: '/', 
				redirect: to => {
					return 'cnae'
				}
			},
			{ 
				path: '*', 
				component: () => import('../views/UnderConstruction.vue') 
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

function BuscaUsuarioLogado(next) {

	Index.dispatch(GET_USUARIO)
		.then((usuario) => {
			if (usuario.authenticated) {
				next()
			} else {
				next('/login')
			}
		})
		.catch(() => {
			next(false)
		})
	
}

// function getPermissaoPelaRota (path, next) {
// 	if (verificaPermissaoPelaRota(path)) {
// 	  next()
// 	} else {
// 	  next(false)
// 	}
// }

export default router
