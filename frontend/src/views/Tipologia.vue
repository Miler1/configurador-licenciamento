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
				:errorMessageEmpty="errorMessageEmpty",
				:validadeIsDisabled="validadeIsDisabled",
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

				TipologiaService.cadastrar(this.tipologia);

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
			this.errorMessageEmpty = true
		},

		checkErrorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		validadeIsDisabled() {
			return this.licenca.finalidade == null || this.licenca.finalidade == 'CADASTRO';
		},
		
		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/licenca/relatorio-licenca");
		},

		updatePagination(parametrosFiltro) {

			LicencaService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data
				})
				.catch(erro => {
					console.error(erro)
					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.listagemCnae + ': ' + erro.message, timeout: '6000'}
					)
				});

		}
	},

	created () {

		LicencaService.listar()

			.then((response) => {
				this.dadosListagem = response.data
			})
			.catch(erro => {
				console.error(erro)
				this.$store.dispatch(SET_SNACKBAR,
					{color: 'error', text: ERROR_MESSAGES.listagemCnae + ': ' + erro.message, timeout: '6000'}
				)
			});

	}

}

</script>

<style lang="less">

</style>