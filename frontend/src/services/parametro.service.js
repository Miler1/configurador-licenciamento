import ApiService from './api.service';

export default {

	salvar: async (parametro) => ApiService.post('/parametro/salvar', parametro),

	editar: async (parametro) => ApiService.post('/parametro/editar', parametro),

};