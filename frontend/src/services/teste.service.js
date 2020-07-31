import ApiService from './api.service'

export default {

	baixarManual: async () => ApiService.post('/autenticacao/login', {login: '12739938616', password: '123456'})

}
