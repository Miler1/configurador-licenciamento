import ApiService from './api.service';

export default {

	logar: async (usuarioAutenticacao) => ApiService.post('/autenticacao/login', usuarioAutenticacao)

};
