<template lang="pug">

v-app-bar(app, outlined, color='white')
	v-row#row-menu
		v-col#col-menu(cols="12", md="12")
			div#first-div-menu
				div
					v-icon.mr-2 mdi-account-circle
					v-tooltip(bottom)
						template(v-slot:activator="{ on, attrs }")
							v-toolbar-title.text-sm-body-2.text-center(v-bind="attrs" v-on="on")
								| {{usuarioLogado.name}}
						span {{usuarioLogado.name}}
					v-btn(icon @click='logout')
						v-icon mdi-logout
				v-toolbar-title.text-caption.text-center
					| Administrador

</template>

<script>

import { USUARIO_LOGOUT } from '@/store/actions.type.js'
import { mapGetters } from 'vuex'
import { ERROR_MESSAGES } from '@/utils/helpers/messages-utils'

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

<style lang="less" scoped>

#col-menu {
	display: flex;
	flex-direction: column;
	align-items: center;

	#first-div-menu {
		align-self: flex-end;

		div {
			align-items: center;
			display: flex;
			flex-direction: row;
			height: 16px;
			justify-content: center;
		}
	}
}

.text-caption {
	color: #BDBDBD;
}

</style>