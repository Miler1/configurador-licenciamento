import ApiService from './api.service';

export default {

	cadastrar: async (pergunta) => ApiService.post('/pergunta/salvar', pergunta)

};
