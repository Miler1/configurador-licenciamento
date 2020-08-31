import ApiService from './api.service';

export default {

    salvar: async (taxaAdministrativa) => ApiService.post('/taxaAdministrativa/salvar', taxaAdministrativa),
    
};
