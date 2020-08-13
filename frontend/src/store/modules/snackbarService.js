import {
	SET_SNACKBAR
} from '../actions.type';
import {
	UPDATE_SNACKBAR
} from '../mutations.type';

const INITIAL_STATE = {
	snackbars: []
};

export const state = Object.assign({}, INITIAL_STATE);

export const getters = {

	snackbars: state => state.snackbars,

};

export const actions = {

	[SET_SNACKBAR]: ({ commit }, snackbar) => {
		snackbar.showing = true;
		snackbar.color = snackbar.color || 'success';
		commit(UPDATE_SNACKBAR, snackbar);
	}

};

export const mutations = {
	
	[UPDATE_SNACKBAR]: (state, snackbar) => (state.snackbars = state.snackbars.concat(snackbar))

};

export default {
	state,

	getters,

	actions,

	mutations
};
