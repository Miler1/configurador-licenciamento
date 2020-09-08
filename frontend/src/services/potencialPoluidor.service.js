import ApiService from './api.service';

export default {

    findAll: async () => ApiService.get(`potencialPoluidor/findAll`)

};