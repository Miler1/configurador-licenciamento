import ApiService from './api.service';

export default {

	cadastrarAtividadeDispensavel: async (atividadeDispensavel) => ApiService.post('tipoCaracterizacaoAtividade/atividadeDispensavel/salvar', atividadeDispensavel ),

	editarAtividadeDispensavel: async (atividadeDispensavel) => ApiService.post('tipoCaracterizacaoAtividade/atividadeDispensavel/editar', atividadeDispensavel ),

	ativarDesativarAtividadeDispensavel: async (idAtividadeDispensavel) => ApiService.post('tipoCaracterizacaoAtividade/atividadeDispensavel/ativarDesativar/' + idAtividadeDispensavel ),

	listarAtividadeDispensavel: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`tipoCaracterizacaoAtividade/atividadeDispensavel/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'atividadeCnae.nome,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('tipoCaracterizacaoAtividade/atividadeDispensavel/listar/?page=0&size=10&sort=atividadeCnae.nome,asc', {});
		}

	},

	findById: async (idAtividadeDispensavel) => ApiService.get('tipoCaracterizacaoAtividade/atividadeDispensavel/findById/' + idAtividadeDispensavel )

};