import ApiService from './api.service';

export default {

	cadastrar: async (atividadeCnae) => ApiService.post('/atividadeCnae/salvar', atividadeCnae),

	editar: async (atividadeCnae) => ApiService.post('/atividadeCnae/editar', atividadeCnae),

	ativarDesativar: async (idAtividadeCnae) => ApiService.post('atividadeCnae/ativarDesativar/' + idAtividadeCnae),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`atividadeCnae/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'nome,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('atividadeCnae/listar/?page=0&size=10&sort=nome,asc', {});
		}

	},

	buscarCnaesAtivosNaoVinculados: async () => ApiService.get(`atividadeCnae/buscarCnaesAtivosNaoVinculados`),

	buscarAtividadesCnaesAtivos: async () => ApiService.get(`atividadeCnae/buscarAtividadesCnaesAtivos`)

};