import ApiService from './api.service';

export default {

	salvar: async (parametro) => ApiService.post('/parametro/salvar', parametro),

	editar: async (parametro) => ApiService.post('/parametro/editar', parametro),

	listar: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`parametro/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'dataCadastro,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('parametro/listar/?page=0&size=10&sort=dataCadastro,asc', {});
		}

	},

	findAll: async () => ApiService.get(`parametro/findAll`)

};