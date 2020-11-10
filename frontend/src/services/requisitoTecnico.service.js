import ApiService from './api.service';

export default {

	cadastrar: async (requisitoTecnico) => ApiService.post('/requisitoTecnico/salvar', requisitoTecnico ),

	editar: async (requisitoTecnico) => ApiService.post('/requisitoTecnico/editar', requisitoTecnico ),

	ativarDesativar: async (idRequisito) => ApiService.post('/requisitoTecnico/ativarDesativar/' + idRequisito ),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`requisitoTecnico/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'codigo,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('requisitoTecnico/listar/?page=0&size=10&sort=codigo,asc', {});
		}

	},

	findById: async (idRequisito) => ApiService.get('requisitoTecnico/findById/' + idRequisito ),

	buscarRequisitosTecnicosAtivos: async () => ApiService.get(`requisitoTecnico/buscarRequisitosAtivos`)

};