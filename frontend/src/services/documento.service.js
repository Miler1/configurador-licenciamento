import ApiService from './api.service';

export default {

	cadastrar: async (documento) => ApiService.post('/documento/salvar', documento),

};
