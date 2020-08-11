import ApiService from './api.service'

export default {
	
	cadastrar: async (licenca) => ApiService.post('/licenca/salvar', licenca)

}