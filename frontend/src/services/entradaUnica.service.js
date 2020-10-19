
import ApiService from './api.service';

export default {

	findSetores: async () => {
		return ApiService.get('/entradaUnica/setores');
	}

};