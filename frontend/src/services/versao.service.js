import ApiService from './api.service';

export default {

	getVersao: async () => ApiService.get('/versao'),

};