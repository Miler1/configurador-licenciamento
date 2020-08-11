<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
		)
			FormCadastroLicenca(
				:licenca="licenca",
				:submit="submit",
				:clear="clear",
				:resetErrorMessage="resetErrorMessage",
				:errorMessageEmpty="errorMessageEmpty",
				:checkErrorMessage="checkErrorMessage",
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroLicenca from '@/components/FormCadastroLicenca'
import LicencaService from '../services/licenca.service';
import { SET_SNACKBAR } from '../store/actions.type'
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils'

export default {

	name: "Licenca",

	components: {
		PanelCadastro,
		FormCadastroLicenca,
	},

	data: () => {
		return {
			panelTitle: "Cadastro de licença ambiental",
			errorMessageEmpty: true,
			licenca:{
				tipo: null,
				nome: null,
				validade: null,
				podeRenovar: false,
				naoExpira: false,
				finalidade: null,
			},
		}
	},

	methods: {

		submit() {
			if (this.checkForm()) {
					
				LicencaService.cadastrar(this.licenca)
					.then((response) => {
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'success', text: SUCCESS_MESSAGES.cadastro, timeout: '6000'}
						)
						this.clear();
					})
					.catch(erro => {
						console.error(erro)
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'error', text: ERROR_MESSAGES.cadastroLicenca + ': ' + erro.message, timeout: '6000'}
						)
					});

			} else {
				this.errorMessageEmpty = false;			
			}
		},

		clear() {
			this.tipo = ""
			this.nomenclatura = ""
			this.validade = ""
			this.podeRenovar = false
			this.naoExpira = false
			this.descricao = ""
			this.errorMessageEmpty = true
		},

		checkForm() {
			return this.licenca.tipo &&
				this.licenca.tipo != ''	&&
				this.licenca.nomenclatura &&
				this.licenca.nomenclatura != '' &&
				this.licenca.validade &&
				this.licenca.validade != ''
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true
		},

		checkErrorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		}
	}
}

</script>

<style lang="less">

</style>