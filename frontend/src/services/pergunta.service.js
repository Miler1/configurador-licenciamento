import ApiService from './api.service';

export default {

	cadastrar: async (pergunta) => ApiService.post('/pergunta/salvar', pergunta),

	editar: async (pergunta) => ApiService.post('/pergunta/editar', pergunta),

	ativarDesativar: async (idPergunta) => ApiService.post('/pergunta/ativarDesativar/' + idPergunta),

	listar: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`pergunta/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'texto,asc'}`,

						parametrosFiltro
			);

		} else {
			return ApiService.post('pergunta/listar/?page=0&size=10&sort=texto,asc', {});
		}

	},

	listarPerguntasAtivas: async () => ApiService.get(`pergunta/listarPerguntasAtivas`)

};
