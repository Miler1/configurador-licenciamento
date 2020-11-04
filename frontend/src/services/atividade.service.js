
import ApiService from './api.service';

export default {

	//ATIVIDADE DISPENSAVEL

	cadastrarAtividadeDispensavel: async (atividadeDispensavel) => ApiService.post('atividadeDispensavel/salvar', atividadeDispensavel ),

	editarAtividadeDispensavel: async (atividadeDispensavel) => ApiService.post('atividadeDispensavel/editar', atividadeDispensavel ),

	ativarDesativarAtividadeDispensavel: async (idAtividadeDispensavel) => ApiService.post('atividadeDispensavel/ativarDesativar/' + idAtividadeDispensavel ),

	listarAtividadeDispensavel: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`atividadeDispensavel/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'nome,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('atividadeDispensavel/listar/?page=0&size=10&sort=nome,asc', {});
		}

	},

	findByIdAtividadeDispensavel: async (idAtividadeDispensavel) => ApiService.get('atividadeDispensavel/findById/' + idAtividadeDispensavel ),
	
	// ATIVIDADELICENCIAVEL

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