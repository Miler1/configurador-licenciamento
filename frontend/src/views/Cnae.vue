<template lang="pug">

	v-container  

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
		)
			FormCadastroCnae(
				:atividadeCnae="atividadeCnae",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessageEmpty="errorMessageEmpty"
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
import FormCadastroCnae from '@/components/FormCadastroCnae'
import GridListagem from '@/components/GridListagem'
import AtividadeCnaeService from '@/services/atividadeCnae.service';
import { SET_SNACKBAR } from '@/store/actions.type'
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils'
import { HEADER } from '@/utils/dadosMockados/ListagemCnaeHeader'

export default {
	name:"cnae",
	components: {
		PanelCadastro,
		FormCadastroCnae,
		GridListagem
	},
	data: () => {
		return {
			panelTitle: "Cadastro de CNAE",
			tituloListagem: "Listagem de CNAE cadastrados",
			placeholderPesquisa: "Pesquisar por código ou nome do CNAE",
			errorMessageEmpty: true,
			atividadeCnae: {
				codigo: '',
				nome: '',
				ativo: true
			},
			dadosListagem: {},
			headerListagem: HEADER
		}
	},

	methods: {

		clear() {

			this.atividadeCnae.codigo= ''
			this.atividadeCnae.nome= ''
			this.errorMessageEmpty=true

		},

		submit() {

			if (this.checkForm()) {

				AtividadeCnaeService.cadastrar(this.atividadeCnae)

					.then((response) => {
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'success', text: SUCCESS_MESSAGES.cadastroCnae, timeout: '6000'}
						)
						this.clear();
					})
					.catch(erro => {
						console.error(erro)
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'error', text: ERROR_MESSAGES.cadastroCnae + ': ' + erro.message, timeout: '6000'}
						)
					});

			} else {
				this.errorMessageEmpty = false
			}

		},

		checkForm() {
			return this.atividadeCnae.codigo 
				&& this.atividadeCnae.codigo != ''
				&& this.atividadeCnae.nome
				&& this.atividadeCnae.nome != ''
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true
		},

		gerarRelatorio() {
			console.log("Gerar Relatório")
		},

		updatePagination(parametrosFiltro) {

			AtividadeCnaeService.listar(parametrosFiltro)

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

		AtividadeCnaeService.listar()

			.then((response) => {
				this.dadosListagem = response.data
				console.log(response)
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
