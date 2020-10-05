import Vue from 'vue';
import VueRouter from 'vue-router';

import { GET_USUARIO } from '@/store/actions.type';
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

		component: () => import('@/views/MainWrapper.vue'),

		children: [
			{
				path: '/', 
				redirect: to => {
					return 'cnae';
				}
			},

			// CNAE
			{
				path: 'cnae',
				name: 'Cnae',
				component: () => import('@/views/cnae/Cnae.vue')
			},

			// LICENÇA
			{
				path: 'licenca',
				name: 'Licenca',
				component: () => import('@/views/licenca/Licenca.vue')
			},

			// TIPOLOGIA
			{
				path: 'tipologia',
				name: 'Tipologia',
				component: () => import('@/views/tipologia/Tipologia.vue')
			},

			// PARÂMETRO
			{
				path: 'parametro',
				name: 'Parametro',
				component: () => import('@/views/parametro/Parametro.vue')
			},

			// DOCUMENTO
			{
				path: 'documentos',
				name: 'Documentos',
				component: () => import('@/views/documento/Documento.vue')
			},

			// TAXA ADMINISTRATIVA
			{	
				path: 'taxa-administrativa',
				name: 'TaxaAdministrativa',
				component: () => import('@/views/taxa/TaxaAdministrativa.vue')
			},

			// TAXA LICENCIAMENTO
			{	path: 'taxa-licenciamento',
				name: 'TaxaLicenciamento',
				component: () => import('@/views/taxa/TaxaLicenciamento.vue')
			},
			{
				path: 'taxa-licenciamento/cadastrar',
				name: 'CadastrarTaxaLicenciamento',
				component: () => import('@/views/taxa/TelaCadastroTaxaLicenciamento.vue')
			},
			{
				path: 'taxa-licenciamento/editar/:idTaxaLicenciamento',
				name: 'EditarTaxaLicenciamento',
				component: () => import('@/views/taxa/TelaCadastroTaxaLicenciamento.vue')
			},

			// REQUISITO ADMINISTRATIVO
			{
				path: 'requisitos-administrativos',
				name: 'requisitos-administrativos',
				component: () => import('@/views/requisito/administrativo/RequisitoAdministrativo.vue')
			},

			// REQUISITO TÉCNICO
			{
				path: 'requisitos-tecnicos',
				name: 'RequisitosTecnicos',
				component: () => import('@/views/requisito/tecnico/RequisitoTecnico.vue')
			},
			{
				path: 'requisitos-tecnicos/cadastrar',
				name: 'CadastrarRequisitosTecnicos',
				component: () => import('@/views/requisito/tecnico/TelaCadastroRequisitoTecnico.vue')
			},
			{
				path: 'requisitos-tecnicos/editar/:idRequisito',
				name: 'EditarRequisitosTecnicos',
				component: () => import('@/views/requisito/tecnico/TelaCadastroRequisitoTecnico.vue')
			},

			// ATIVIDADE DISPENSÁVEL
			{
				path: 'perguntas',
				name: 'Pergunta',
				component: () => import('@/views/pergunta/Pergunta.vue')
			},
			{
				path: 'cnaes-dispensaveis',
				name: 'cnaesDispensaveis',
				component: () => import('@/views/atividade/dispensavel/atividadeDispensavel.vue')
			},
			{
				path: 'cnaes-dispensaveis/cadastrar',
				name: 'CadastrarAtividadeDispensavel',
				component: () => import('@/views/atividade/dispensavel/TelaCadastro.vue')
			},
			{
				path: 'cnaes-dispensaveis/editar/:idAtividadeDispensavel',
				name: 'EditarAtividadeDispensavel',
				component: () => import('@/views/atividade/dispensavel/TelaCadastro.vue')
			},
			{
				path: 'atividades-licenciaveis',
				name: 'CadastrarAtividadeLicenciavel',
				component: () => import('@/views/atividade/licenciavel/TelaCadastro.vue')
			},

			// UNDERCONSTRUCTION
			{ 
				path: '*', 
				component: () => import('@/views/UnderConstruction.vue') 
			}
		]
	},

	{
		path: '/login',
		name: 'Login',
		component: () => import('@/views/login/Login.vue'),

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