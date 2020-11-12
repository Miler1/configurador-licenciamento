import ApiService from './api.service';

export default {

	salvar: async (licenca) => ApiService.post('/licenca/salvar', licenca),

	editar: async (licenca) => ApiService.post('/licenca/editar', licenca),

	ativarDesativar: async (idLicenca) => ApiService.post('licenca/ativarDesativar/' + idLicenca ),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`licenca/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'sigla,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('licenca/listar/?page=0&size=10&sort=sigla,asc', {});
		}

	},

	findAll: async () => ApiService.get(`licenca/findAll`)

};