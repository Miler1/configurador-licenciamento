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

		GridListagem.pa-7(
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination"
		)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroLicenca from '@/components/FormCadastroLicenca'
import LicencaService from '../services/licenca.service'
import RelatorioService from '../services/relatorio.service'
import GridListagem from '@/components/GridListagem'
import { SET_SNACKBAR } from '../store/actions.type'
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils'
import { HEADER } from '@/utils/dadosMockados/ListagemLicencaHeader'

export default {

	name: "Licenca",

	components: {
		PanelCadastro,
		FormCadastroLicenca,
		GridListagem
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
			tituloListagem: "Listagem de licenças ambientais cadastradas",
			placeholderPesquisa: "Pesquisar por sigla ou nome da licença",
			dadosListagem: {},
			headerListagem: HEADER,
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