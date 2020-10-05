
import ApiService from './api.service';

export default {

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

	}

};