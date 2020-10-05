import ApiService from './api.service';

export default {

	cadastrar: async (tipologia) => ApiService.post('/tipologia/salvar', tipologia),

	editar: async (tipologia) => ApiService.post('/tipologia/editar', tipologia),

	listar: async (parametrosFiltro) => {
		
		if (parametrosFiltro) {

			return ApiService.post(`tipologia/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'nome,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('tipologia/listar/?page=0&size=10&sort=nome,asc', {});
		}

	},

	buscarTipologiasAtivas: async () => ApiService.get(`tipologia/buscarTipologiasAtivas`)

};
