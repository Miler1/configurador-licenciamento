import ApiService from '../api.service';

export default {

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

};