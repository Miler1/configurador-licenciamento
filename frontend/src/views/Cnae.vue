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
			
		// remover ao mergear história da listagem
		v-btn#QA-btn_gerar_relatorio(width="100%", @click='baixarRelatorio')
			v-icon(left) mdi-get_app
			span Gerar relatório

</template>

<script>

import RelatorioService from '../services/relatorio.service'
import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroCnae from '@/components/FormCadastroCnae'
import AtividadeCnaeService from '../services/atividadeCnae.service'
import { SET_SNACKBAR } from '../store/actions.type'
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils'

export default {

	name:"Cnae",

	components: {
		PanelCadastro,
		FormCadastroCnae
	},

	data: () => {
		return {
			panelTitle: "Cadastro de CNAE",
			errorMessageEmpty: true,
			atividadeCnae: {
				codigo: '',
				nome: '',
				ativo: true
			},
		}
	},

	methods: {

		submit() {
			if (this.checkForm()) {
					
				AtividadeCnaeService.cadastrar(this.atividadeCnae)
					
					.then((response) => {
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'success', text: SUCCESS_MESSAGES.cadastro, timeout: '6000'}
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

		clear() {
			this.atividadeCnae.codigo = ''
			this.atividadeCnae.nome = ''
			this.errorMessageEmpty = true
		},

		checkForm() {
			return this.atividadeCnae.codigo &&
				this.atividadeCnae.codigo != '' &&
				this.atividadeCnae.nome &&
				this.atividadeCnae.nome != ''
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true
		},
		
		/*remover ao mergear história da listagem*/
		baixarRelatorio() {
			RelatorioService.baixarRelatorio("/atividadeCnae/relatorio-cnae");
		}

	}
}

</script>

<style lang="less" scope>
</style>