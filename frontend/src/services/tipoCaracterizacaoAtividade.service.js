import ApiService from './api.service';

export default {

	listarAtividadeDispensavel: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`tipoCaracterizacaoAtividade/atividadeDispensavel/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'id,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('tipoCaracterizacaoAtividade/atividadeDispensavel/listar/?page=0&size=10&sort=id,asc', {});
		}

	}

};