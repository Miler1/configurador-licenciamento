import ApiService from './api.service'

export default {

	cadastrar: async (atividadeCnae) => ApiService.post('/atividadeCnae/salvar', atividadeCnae)

}
