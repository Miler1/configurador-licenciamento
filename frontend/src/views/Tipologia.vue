<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
		)
			FormCadastroTipologia(
				:tipologia="tipologia",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessageEmpty="errorMessageEmpty"
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroTipologia from '@/components/FormCadastroTipologia'
import TipologiaService from '../services/tipologia.service'
import RelatorioService from '../services/relatorio.service'
import { SET_SNACKBAR } from '../store/actions.type'
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils'

export default {

	name: "Tipologia",

	components: {
		PanelCadastro,
		FormCadastroTipologia
	},

	data: () => {
		return {
			panelTitle: "Cadastro de tipologia",
			errorMessageEmpty: true,
			tipologia:{
				codigo: '',
				nome: ''
			}
		}
	},

	methods: {

		submit() {

			if (this.checkForm()) {

				this.tipologia.ativo = true;
				TipologiaService.cadastrar(this.tipologia)
					.then(response => {
						this.handleSuccess(response);
					})
					.catch(error => {
						this.handleError(error);
					});

			} else {
				this.errorMessageEmpty = false;
			}
		},

		clear() {

			this.tipologia.codigo = '';
			this.tipologia.nome = '';
			this.errorMessageEmpty=true;

		},

		checkForm() {
			return this.tipologia.nome && this.tipologia.nome !== '';
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		checkErrorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		handleError(error) {
			let message = error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: ERROR_MESSAGES.cadastroTipologia + ': ' + message, timeout: '6000'}
			)
		},

		handleSuccess(response) {
			
			let message = '';

			if(response.data.codigo !== this.tipologia.codigo) {
				message = ` A tipologia salva com o código: ${response.data.codigo}`;
			}

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: SUCCESS_MESSAGES.cadastro + message, timeout: '6000'}
			)

			this.clear();
		}
	}

}

</script>

<style lang="less">

</style>