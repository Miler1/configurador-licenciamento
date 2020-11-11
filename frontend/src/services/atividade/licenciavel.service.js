import ApiService from '../api.service';

export default {

	cadastrarAtividadeLicenciavel: async (atividadeLicenciavel) => ApiService.post('atividadeLicenciavel/salvar', atividadeLicenciavel ),

	editarAtividadeLicenciavel: async (atividadeLicenciavel) => ApiService.post('atividadeLicenciavel/editar', atividadeLicenciavel ),

	salvarRascunhoAtividadeLicenciavel: async (atividadeLicenciavel) => ApiService.post('atividadeLicenciavel/salvarRascunho', atividadeLicenciavel),

	ativarDesativarAtividadeLicenciavel: async (idAtividadeLicenciavel) => ApiService.post('atividadeLicenciavel/ativarDesativar/' + idAtividadeLicenciavel ),

	listarAtividadeLicenciavel: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`atividadeLicenciavel/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'nome,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('atividadeLicenciavel/listar/?page=0&size=10&sort=nome,asc', {});
		}

	},

	findByIdAtividadeLicenciavel: async (idAtividade) => ApiService.get('atividadeLicenciavel/findById/' + idAtividade ),

	excluirRascunhoAtividadeLicenciavel: async (idAtividadeLicenciavel) => ApiService.post('atividadeLicenciavel/excluirRascunho/' + idAtividadeLicenciavel)

};