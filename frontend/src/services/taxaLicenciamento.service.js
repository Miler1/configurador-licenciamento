import ApiService from './api.service';

export default {

	cadastrar: async (taxaLicenciamento) => ApiService.post('/codigoTaxaLicenciamento/salvar', taxaLicenciamento ),

	editar: async (taxaLicenciamento) => ApiService.post('/codigoTaxaLicenciamento/editar', taxaLicenciamento ),

	ativarDesativar: async (idTaxaLicenciamento) => ApiService.post('/codigoTaxaLicenciamento/ativarDesativar/' + idTaxaLicenciamento ),

	findById: async (idTaxaLicenciamento) => ApiService.get('codigoTaxaLicenciamento/findById/' + idTaxaLicenciamento ),

	listar: async (parametrosFiltro) => {

		if (parametrosFiltro) {

			return ApiService.post(`codigoTaxaLicenciamento/listar/?
						page=${parametrosFiltro.pagina > 0 ? parametrosFiltro.pagina : 0}
						&size=${parametrosFiltro.itemsPorPagina > 0 ? parametrosFiltro.itemsPorPagina : 10}
						&sort=${parametrosFiltro.tipoOrdenacao ? parametrosFiltro.tipoOrdenacao : 'codigo,desc'}`,

			parametrosFiltro
			);

		} else {
			return ApiService.post('codigoTaxaLicenciamento/listar/?page=0&size=10&sort=codigo,desc', {});
		}

	}

};
