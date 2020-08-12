<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
		)
			FormCadastroLicenca(
				:licenca="licenca",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:checkErrorMessage="checkErrorMessage",
				:validadeIsDisabled="validadeIsDisabled",
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroLicenca from '@/components/FormCadastroLicenca'
import LicencaService from '../services/licenca.service'
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
				sigla: null,
				nome: null,
				finalidade: null,
				validade: null,
			},
		}
	},

	methods: {

		submit() {

			if (this.checkForm()) {

				LicencaService.salvar(this.licenca)
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

			this.licenca.sigla = null
			this.licenca.nome = null
			this.licenca.validade = null
			this.licenca.finalidade = null
			this.licenca.errorMessageEmpty = true

		},

		checkForm() {

			return this.licenca.sigla &&
				this.licenca.sigla != ''	&&
				this.licenca.nome &&
				this.licenca.nome != '' &&
				this.licenca.validade &&
				this.licenca.validade != '' &&
				this.licenca.finalidade &&
				this.licenca.finalidade != ''

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true
		},

		checkErrorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		validadeIsDisabled() {
			return this.licenca.finalidade == null || this.licenca.finalidade == 'CADASTRO';
		}
	}

}

</script>

<style lang="less">

</style>