import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { loading } from '@/utils/loading.js';
import { ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import snackbar from '@/services/snack.service';

var countRequest = 0;
const ApiService = {
	async init () {
		Vue.use(VueAxios, axios);
		Vue.axios.defaults.baseURL = process.env.BASE_URL;
		Vue.axios.defaults.timeout = 300000;
		await this.setHeader();

		axios.interceptors.request.use((config) => {
			if (countRequest <= 0) {
				loading.show();
				countRequest = 0;
			}

			countRequest++;
			return config;
		}, (error) => {
			countRequest--;
			this.verificarLoading();
			return Promise.reject(error);
		});

		axios.interceptors.response.use((response) => {
			countRequest--;
			this.verificarLoading();
			return response;
		}, (error) => {
			countRequest--;
			this.verificarLoading();
			return Promise.reject(error);
		});
	},

	async setHeader () {
		// Vue.axios.defaults.headers.common.Authorization = ;
		Vue.axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
	},

	async query (resource, params) {
		try {
			return await Vue.axios
				.get(resource,

					{
						params
					});
		} catch (error) {
			const result = { message: this.genericErrorHandling(error) };

			return Promise.reject(result);
		}
	},

	async get (resource, slug = '') {
		try {
			return await Vue.axios.get(`${resource}/${slug}`);
		} catch (error) {

			const result = { message: this.genericErrorHandling(error) };

			if(result){
				return Promise.reject(result);
			}
		}
	},

	async post (resource, params) {
		try {
			return await Vue.axios.post(`${resource}`, params);
		} catch (error) {
			const result = { message: this.genericErrorHandling(error) };

			return Promise.reject(result);
		}
	},

	async update (resource, slug, params) {
		try {
			return await Vue.axios.put(`${resource}/${slug}`, params);
		} catch (error) {
			const result = { message: this.genericErrorHandling(error) };

			return Promise.reject(result);
		}
	},

	async put (resource, params) {
		try {
			return await Vue.axios.put(`${resource}`, params);
		} catch (error) {
			const result = { message: this.genericErrorHandling(error) };

			return Promise.reject(result);
		}
	},

	async delete (resource) {
		try {
			return await Vue.axios.delete(resource);
		} catch (error) {
			const result = { message: this.genericErrorHandling(error) };

			return Promise.reject(result);
		}
	},

	genericErrorHandling (error) {
		let { message } = error;

		if (error && error.response && error.response.status) {
			switch (error.response.status) {
			case 401:
				this.handling401(error.response.data);
				message = '';
				break;
			case 404:
				message = error.response.data.message;
				break;
			case 500:
				message = error.response.data.message;
				break;
			default:
				message = error.response.data.message;
				break;
			}
		}

		console.log('genericErrorHandling', error.response);

		return message;
	},

	handling401 () {
		if (countRequest == 0) {
			setTimeout(() => snackbar.alert(ERROR_MESSAGES.unauthorized), 500);
			window.location.hash = '#/login';
		}
	},

	handling500 () {
		// const message = 'Erro ao tentar realizar a operação solicitada. Entre em contato com o administrador do sistema.'
		// return message
	},

	handlingWarning (error) {
		let message = '';

		if (error.response && error.response.data && error.response.data.message) {
			try {
				const msg = JSON.parse(error.response.data.message);
				message = msg.message;
			} catch (e) {
				message = error.response.data.message;
			}

			Vue.noty.warning(message);
		} else if (error.response && error.response.data && error.response.data.errors) {
			error.response.data.errors.forEach(e => {
				message = message + ',' + e;
				Vue.noty.warning(e);
			});
		}

		return message;
	},

	verificarLoading () {

		if (countRequest <= 0) {
			loading.hide();
		}

	}
};

export default ApiService;
