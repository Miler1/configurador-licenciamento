<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroTipologia(
				:tipologia="tipologia",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessageEmpty="errorMessageEmpty",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar"
			)

		//- Remover ao criar listagem
		v-btn.text-xs-center(
			@click="gerarRelatorio",
			width="100%",
			color="#000"
		)
			span(style="color: #ffffff") 007, gerar relatório da missão

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroTipologia from '@/components/FormCadastroTipologia';
import TipologiaService from '../services/tipologia.service';
import RelatorioService from '../services/relatorio.service';
import { SET_SNACKBAR } from '../store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "Tipologia",

	components: {
		PanelCadastro,
		FormCadastroTipologia
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			tipologia:{
				codigo: '',
				nome: ''
			},
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de tipologia",
				iconName:'mdi-format-list-bulleted-square',
			},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus"
		};
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
			);
		},

		handleSuccess(response) {
			
			let message = '';

			if(response.data.codigo !== this.tipologia.codigo) {
				message = ` A tipologia salva com o código: ${response.data.codigo}`;
			}

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: SUCCESS_MESSAGES.cadastro + message, timeout: '6000'}
			);

			this.clear();
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/tipologia/relatorio");
		}
	}

};

</script>

<style lang="less">

</style>