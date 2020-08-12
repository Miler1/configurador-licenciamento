import ApiService from './api.service'

export default {
	
	salvar: async (licenca) => ApiService.post('/licenca/salvar', licenca)

}