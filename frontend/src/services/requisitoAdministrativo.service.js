import ApiService from './api.service';

export default {

	cadastrar: async (requisitoAdministrativo) => ApiService.post('/requisitoAdministrativo/salvar', requisitoAdministrativo ),

	editar: async (requisitoAdministrativo) => ApiService.post('/requisitoAdministrativo/editar', requisitoAdministrativo),

	ativarDesativar: async (idRequisitoAdministrativo) => ApiService.post('/requisitoAdministrativo/ativarDesativar/' + idRequisitoAdministrativo),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`requisitoAdministrativo/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacaoDocumento ? parametrosFiltro.tipoOrdenacaoDocumento : 'documento.nome,asc'},
						&sort=${parametrosFiltro.tipoOrdenacaoLicenca ? parametrosFiltro.tipoOrdenacaoLicenca : 'licenca.sigla,asc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('requisitoAdministrativo/listar/?page=0&size=10&sort=documento.nome,asc&sort=licenca.sigla,asc', {});
		}

	}

};
