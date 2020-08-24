import ApiService from './api.service';

export default {

	cadastrar: async (requisitoAdministrativo) => ApiService.post('/requisitoAdministrativo/salvar', requisitoAdministrativo ),

};
