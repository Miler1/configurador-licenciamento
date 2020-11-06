import ApiService from './api.service';

export default {

	cadastrar: async (documento) => ApiService.post('/documento/salvar', documento),

	editar: async (documento) => ApiService.post('/documento/editar', documento),

	ativarDesativar: async (idDocumento) => ApiService.post('/documento/ativarDesativar/' + idDocumento),

	listar: async (documentosFiltro) => {
		if(documentosFiltro){
			return ApiService.post(`documento/listar/?
						page=${documentosFiltro.pagina > 0 ? documentosFiltro.pagina : 0}
						&size=${documentosFiltro.itemsPorPagina > 0 ? documentosFiltro.itemsPorPagina : 10}
						&sort=${documentosFiltro.tipoOrdenacao ? documentosFiltro.tipoOrdenacao : 'nome,asc'}`,
						documentosFiltro
			);
		}else {
			return ApiService.post('documento/listar/?page=0&size=10&sort=nome,asc', {});
		}
	},

	buscarDocumentosAtivos: async () => ApiService.get(`documento/buscarDocumentosAtivos`)

};
