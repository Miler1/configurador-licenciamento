<template lang="pug">

v-app-bar(app, outlined, color='white')
	v-spacer
	v-row
		v-spacer
		v-btn(icon).mt-2
			v-icon mdi-account-circle
		v-col(cols="2")
			v-toolbar-title.text-sm-body-2.text-center   
				| {{usuario.nome}}
			v-toolbar-title.text-caption.text-center
				| {{usuario.perfil}}
		v-btn(icon).mt-2
			v-icon mdi-logout

</template>

<script>

export default {

	name: 'MenuSuperior',

	data() {
		return {
			usuario: {
				nome: 'Guilherme Borges',
				perfil: 'Administrador'
			}
		}
	}
}
</script>

<style>

.text-caption {
	color: #BDBDBD;
}

</style>