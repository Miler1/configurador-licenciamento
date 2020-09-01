import { SET_SNACKBAR } from '@/store/actions.type';
import store from '@/store/index';

let types = {
	ERROR: 'error',
	SUCCESS: 'success',
	WARN: 'warning'
};

export default {

	alert: (mensagem, tipo = types.ERROR, timeout = '6000') =>
				store.dispatch(SET_SNACKBAR, {color: tipo, text: mensagem, timeout: timeout}),

	type: types

};