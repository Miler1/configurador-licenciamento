import Vue from 'vue'
import {
	USUARIO_LOGOUT,
	GET_USUARIO
} from '../actions.type'
import {
	START_USUARIO_LOGOUT,
	END_USUARIO_LOGOUT,
	UPDATE_USUARIO_LOGOUT,
	START_USUARIO_LOGIN,
	END_USUARIO_LOGIN,
	UPDATE_USUARIO_LOGIN
} from '../mutations.type'
import UsuarioService from '../../services/usuario.service'

const INITIAL_STATE = {
	logado: false,
	usuarioLogado: null
}

export const state = Object.assign({}, INITIAL_STATE)

export const getters = {

	logado: state => state.logado,

	usuarioLogado: state => state.usuarioLogado

}

export const actions = {

	[USUARIO_LOGOUT]: ({ commit }, state) => {
		commit(START_USUARIO_LOGOUT)
		return UsuarioService.logout(state)
			.then(() => {
				commit(UPDATE_USUARIO_LOGOUT)
			})
			.catch(() => {
				commit(END_USUARIO_LOGOUT)
			})
	},

	[GET_USUARIO]: ({ commit }, state) => {
		commit(START_USUARIO_LOGIN)
		return UsuarioService.getUsuario().then(({ data }) => {
			commit(UPDATE_USUARIO_LOGIN, data)
			return data
		}).catch(() => {
			commit(END_USUARIO_LOGIN)
		})
	}
}

export const mutations = {
	[START_USUARIO_LOGOUT]: state => (state.logado = true),

	[END_USUARIO_LOGOUT]: state => (state.usuarioLogado = null, state.logado = false),

	[UPDATE_USUARIO_LOGOUT]: (state) => (state.usuarioLogado = null, state.logado = false),

	[START_USUARIO_LOGIN]: state => (state.usuarioLogado = null, state.logado = false),

	[END_USUARIO_LOGIN]: state => (state.usuarioLogado = null, state.logado = false),

	[UPDATE_USUARIO_LOGIN]: (state, data) => (state.usuarioLogado = data, state.logado = true)
}

export default {
	state,

	getters,

	actions,

	mutations
}
