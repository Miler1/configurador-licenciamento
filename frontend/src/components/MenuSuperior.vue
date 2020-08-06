<template lang="pug">

v-app-bar(app, outlined, color='white')
	v-spacer
	v-row
		v-spacer
		v-icon.mr-2 mdi-account-circle

		v-col(cols="2")
			v-tooltip(bottom)
				template(v-slot:activator="{ on, attrs }")
					v-toolbar-title.text-sm-body-2.text-center(v-bind="attrs" v-on="on")
						| {{usuarioLogado.name}}
				span {{usuarioLogado.name}}
			v-toolbar-title.text-caption.text-center
				| Administrador

		v-btn(icon @click='logout').mt-2
			v-icon mdi-logout

</template>

<script>

import { USUARIO_LOGOUT } from '@/store/actions.type.js'
import { mapGetters } from 'vuex'
import { ERROR_MESSAGES } from '@/utils/helpers/error-messages'

export default {

	name: 'MenuSuperior',

	methods: {
		logout () {
			this.$store.dispatch(USUARIO_LOGOUT)
				.then (() => {
					this.$router.push('/login')
				}).catch((error) => {
					console.error(error)
					this.$store.dispatch(SET_SNACKBAR, {color: 'error', text: ERROR_MESSAGES.logout, timeout: '6000'})
				})
		}
	},

	computed: {
		...mapGetters(['usuarioLogado'])
	},
}
</script>

<style>

.text-caption {
	color: #BDBDBD;
}

</style>