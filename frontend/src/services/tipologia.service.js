import ApiService from './api.service';

export default {

	cadastrar: async (tipologia) => ApiService.post('/tipologia/salvar', tipologia),

};
