import ApiService from './api.service';

export default {

	listar: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`taxaAdministrativa/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'ano,desc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('taxaAdministrativa/listar/?page=0&size=10&sort=ano,desc', {});
		}

	},

	salvar: async (taxaAdministrativa) => ApiService.post('/taxaAdministrativa/salvar', taxaAdministrativa),
	
};
