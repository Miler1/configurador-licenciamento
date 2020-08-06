import ApiService from './api.service'

export default {

	cadastrar: async (atividadeCnae) => ApiService.post('/configurador/cnae', atividadeCnae) 

}
