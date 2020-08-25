import ApiService from './api.service';

export default {

	cadastrar: async (requisitoAdministrativo) => ApiService.post('/requisitoAdministrativo/salvar', requisitoAdministrativo ),

	editar: async (tipologia) => ApiService.post('/requisitoAdministrativo/editar', tipologia),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`requisitoAdministrativo/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'dataCadastro,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('parametro/listar/?page=0&size=10&sort=dataCadastro,asc', {});
		}

	}

};
