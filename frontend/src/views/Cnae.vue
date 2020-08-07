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

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro'
import FormCadastroCnae from '@/components/FormCadastroCnae'
import AtividadeCnaeService from '../services/atividadeCnae.service';
import { SET_SNACKBAR } from '../store/actions.type'
import { SUCCESS_MESSAGES } from '@/utils/helpers/success-messages'

export default {
	name:"cnae",
	components: {
		PanelCadastro,
		FormCadastroCnae
	},
	data: ()=> {
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
							{color: 'success', text: SUCCESS_MESSAGES.cadastrarCnae, timeout: '6000'}
						)
						this.clear();
					})
					.catch(erro => {
						console.error(error)
						this.$store.dispatch(SET_SNACKBAR,
							{color: 'error', text: ERROR_MESSAGES.cadastrarCnae, timeout: '6000'}
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
		}
	}
}

</script>

<style lang="less" scope>
</style>