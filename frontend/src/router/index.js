import Vue from 'vue';
import VueRouter from 'vue-router';

import { GET_USUARIO } from '../store/actions.type';
import Index from '@/store/index.js';

Vue.use(VueRouter);

const routes = [
	{
		path: '/',
		redirect: to => {
			return '/home';
		}
	},

	{
		path: '/home',
		name: 'home',

		beforeEnter: (to, from, next) => {
			BuscaUsuarioLogado(next);
		},

		redirect: to => {
			return '/home/cnae';
		},

		meta: {
			title: 'Configurador do Licenciamento Ambiental'
		},

		component: () => import('../views/MainWrapper.vue'),

		children: [
			{
				path: '/', 
				redirect: to => {
					return 'cnae';
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
				path: 'tipologia',
				name: 'Tipologia',
				component: () => import('../views/Tipologia.vue')
			},
			{
				path: 'parametro',
				name: 'Parametro',
				component: () => import('../views/Parametro.vue')
			},
			{
				path: 'documentos',
				name: 'Documentos',
				component: () => import('../views/Documento.vue')
			},
			{
				path: 'requisitos-administrativos',
				name: 'requisitos-administrativos',
				component: () => import('../views/RequisitoAdministrativo.vue')
			},	
			{
				path: 'requisitos-tecnicos',
				name: 'RequisitosTecnicos',
				component: () => import('../views/RequisitoTecnico.vue')
			},
			{
				path: 'requisitos-tecnicos/cadastrar',
				name: 'CadastrarRequisitosTecnicos',
				component: () => import('../components/TelaCadastroRequisitoTecnico.vue')
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
];

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes
});

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
				next();
			} else {
				next('/login');
			}
		})
		.catch(() => {
			next(false);
		});
	
}

// function getPermissaoPelaRota (path, next) {
// 	if (verificaPermissaoPelaRota(path)) {
// 	  next()
// 	} else {
// 	  next(false)
// 	}
// }

export default router;
