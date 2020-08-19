<template lang="pug">
	
	v-container
	
		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel",
		)
			FormCadastroParametro(
				:parametro="parametro",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar",
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroParametro from '@/components/FormCadastroParametro';
import ParametroService from '@/services/parametro.service';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "Parametro",

	components: {
		PanelCadastro,
		FormCadastroParametro,
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			isCadastro: true,
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			parametro: {
				codigo: '',
				descricao: '',
				unidade: '',
			},
			dadosListagem: {},
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de parâmetros",
				iconName:'fa fa-sliders',
			},
		};
	},
	
	methods: {

		clear() {

			this.parametro.codigo = null;
			this.parametro.descricao = null;
			this.parametro.unidade = null;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

		},
		
		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {

					ParametroService.salvar(this.atividadeCnae)

						.then(() => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.cadastro, timeout: '6000'}
							);

							this.clear();

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.cadastroParametro + ': ' + erro.message, timeout: '6000'}
							);

						});
						
				} else {

					ParametroService.editar(this.parametro)
					
						.then(() => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.editar, timeout: '6000'}
							);

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: "", timeout: '6000'}
							);
					
						});
				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		checkForm() {

			return this.parametro.codigo 
				&& this.parametro.codigo != '' 
				&& this.parametro.descricao 
				&& this.parametro.descricao != '' 
				&& this.parametro.unidade 
				&& this.parametro.unidade != '';
				
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

	},

};

</script>