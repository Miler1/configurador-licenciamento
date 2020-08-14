<template lang="pug">

v-app-bar(app, outlined, color='white')
	v-row
		v-col.d-flex.flex-column.align-center(cols="12", md="12")
			div.align-self-end
				div#div-toolbar.d-flex.flex-row.justify-center.align-center
					v-icon.mr-2 mdi-account-circle
					v-tooltip(bottom)
						template(v-slot:activator="{ on, attrs }")
							v-toolbar-title#toolbar-title.text-sm-body-2.text-center(v-bind="attrs", v-on="on")
								| {{usuarioLogado && usuarioLogado.name}}
						span {{usuarioLogado && usuarioLogado.name}}
					v-tooltip(bottom)
						template(v-slot:activator="{ on, attrs }")
							v-btn(icon, @click='logout', v-on="on")
								v-icon mdi-logout
						span Sair			
				v-toolbar-title.text-caption.text-center
					| Administrador

</template>

<script>

import { USUARIO_LOGOUT } from '@/store/actions.type.js';
import { mapGetters } from 'vuex';
import { ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: 'MenuSuperior',

	methods: {
		logout () {
			this.$store.dispatch(USUARIO_LOGOUT)
				.then (() => {
					this.$router.push('/login');
				}).catch((error) => {
					console.error(error);
					this.$store.dispatch(SET_SNACKBAR, {color: 'error', text: ERROR_MESSAGES.logout, timeout: '6000'});
				});
		}
	},

	computed: {
		...mapGetters(['usuarioLogado'])
	},
};

</script>

<style lang="less" scoped>

#div-toolbar {
	height: 16px;

	#toolbar-title {
		cursor: default;
	}
}

.text-caption {
	color: #BDBDBD;
}

</style>