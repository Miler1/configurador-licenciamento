import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import { loading } from '@/utils/loading.js';
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

			return Promise.reject(result);
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

		console.log('message', message);
		if (error && error.response && error.response.status) {
			switch (error.response.status) {
			case 401:
				this.handling401(error.response.data);
				break;
			case 404:
				message = error.response.data.message;
				break;
			case 500:
				message = this.handling500();
				break;
			default:
				message = this.handlingWarning(error);
				break;
			}
		}

		console.log('genericErrorHandling', error.response);

		return message;
	},

	handling401 () {
		//redireciona para página de login
		window.location.hash = '#/login';
	},

	handling500 () {
		const message = 'Erro ao tentar realizar a operação solicitada. Entre em contato com o administrador do sistema.';
		Vue.noty.error(message);

		return message;
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
			setTimeout(() => {
				loading.hide();

				countRequest = 0;
			}, 400);
		}
	}
};

export default ApiService;
