import Vue from 'vue';
import Vuex from 'vuex';

import usuarioService from './modules/usuarioService';
import snackbarService from './modules/snackbarService';

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		usuarioService,
		snackbarService
	}
});
