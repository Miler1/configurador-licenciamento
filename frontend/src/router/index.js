import Vue from 'vue'
import VueRouter from 'vue-router'

import { GET_USUARIO } from '../store/actions.type'
import Index from '@/store/index.js'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		redirect: to => {
			return '/home'
		}
	},

	{
		path: '/home',
		name: 'home',

		beforeEnter: (to, from, next) => {
			BuscaUsuarioLogado(next)
		},

		redirect: to => {
			return '/home/cnae'
		},

		meta: {
			title: 'Configurador do Licenciamento Ambiental'
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
				path: 'cnae',
				name: 'Cnae',
				component: () => import('../views/Cnae.vue')
			},
			{
				path: 'licenca',
				name: 'Licenca',
				component: () => import('../views/Licenca.vue')
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
		component: () => import('../views/Login.vue'),

		meta: {
			title: 'Login | Configurador do Licenciamento Ambiental'
		},
	}
]

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes
})

router.beforeEach((to, from, next) => {

	const maisProximaComTitulo = to.matched.slice().reverse().find(r => r.meta && r.meta.title);

	if (maisProximaComTitulo) {
		document.title = maisProximaComTitulo.meta.title;
	}

	next();
});

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
