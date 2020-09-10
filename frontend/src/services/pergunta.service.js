import ApiService from './api.service';

export default {

	cadastrar: async (pergunta) => ApiService.post('/pergunta/salvar', pergunta),

	listar: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`pergunta/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'dataCadastro,desc'}`,

						parametrosFiltro
			);

		} else {
			return ApiService.post('pergunta/listar/?page=0&size=10&sort=dataCadastro,desc', {});
		}

	},

};
