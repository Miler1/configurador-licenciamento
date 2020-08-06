<template lang="pug">

	v-container  

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
		)
			FormCadastroCnae(
				:formulario="form",
				:clear="clear",
				:submit="submit",
				:resetErrors="resetErrors",
				:notEmpty="notEmpty"
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroCnae from '@/components/FormCadastroCnae'

export default {
	name:"cnae",
	components: {
		PanelCadastro,
		FormCadastroCnae
	},
	data: ()=> {
		return {
			panelTitle: "Cadastro de CNAE",
			notEmpty: true,
			form: {
				codigo: '',
				nome: ''
			},
		}
	},
	methods: {
		clear() {
			this.form.codigo= ''
			this.form.nome= ''
			this.notEmpty=true
		},
		submit() {
			if (this.checkForm()) {

				console.log("foi")

			} else {
				this.notEmpty = false
			}
		},
		checkForm() {
			return this.form.codigo 
				&& this.form.codigo != ''
				&& this.form.nome
				&& this.form.nome != ''
		},
		resetErrors() {
			this.notEmpty = true
		}
	}
}

</script>

<style lang="less" scope>
</style>