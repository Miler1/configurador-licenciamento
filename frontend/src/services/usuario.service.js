import ApiService from './api.service'

export default {

	getUsuario: async () => ApiService.get('/autenticacao/usuario-logado'),

	logout: async () => ApiService.post('/logout')

}