import ApiService from './api.service'

export default {

  baixarManual: async () => ApiService.get('/autenticacao/teste')

}
