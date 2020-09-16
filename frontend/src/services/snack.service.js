import { SET_SNACKBAR } from '@/store/actions.type';
import store from '@/store/index';

let types = {
	ERROR: 'error',
	INFO: 'info',
	SUCCESS: 'success',
	WARN: 'warning'
};

let iconDecider = type => {

	switch(type){

		case types.ERROR:
			return 'mdi-alert';

		case types.SUCCESS:
			return 'mdi-check-circle';

		case types.INFO:
			return 'alert-circle';

		case types.WARN:
			return 'mdi-information';

		default:
			return;
	}

};

export default {

	alert: (mensagem, tipo = types.ERROR, timeout = '8000') => {
		store.dispatch(SET_SNACKBAR, {color: tipo, text: mensagem, timeout: timeout, icon: iconDecider(tipo)});
	},

	type: types

};