import ApiService from './api.service'

export default {

	cadastrar: async (atividadeCnae) => ApiService.post('/atividadeCnae/salvar', atividadeCnae),

	listar: async (parametrosFiltro) => {
		if(parametrosFiltro){

			return ApiService.post(`atividadeCnae/lista/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'dataCadastro,asc'}`,

			parametrosFiltro
			);

		}else {
			return ApiService.post('atividadeCnae/lista/?page=0&size=10&sort=dataCadastro,asc', {});
		}
	}

}
