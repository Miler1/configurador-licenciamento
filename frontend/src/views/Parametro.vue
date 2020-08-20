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
				:codigoErrorMessage ="codigoErrorMessage",
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
				codigo: null,
				nome: null,
				casasDecimais: null,
				ativo: true
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
			this.parametro.nome = null;
			this.parametro.casasDecimais = null;
			this.parametro.ativo = true;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

		},
		
		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {

					ParametroService.salvar(this.parametro)
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
				&& this.parametro.nome
				&& this.parametro.nome != ''
				&& this.parametro.casasDecimais
				&& this.parametro.casasDecimais != '';
				
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		codigoErrorMessage(casasDecimais) {

			if (!this.errorMessageEmpty && !casasDecimais ) {

				return 'Obrigatório';

			} else if (this.errorMessageEmpty && (casasDecimais === '' || casasDecimais % 1 != 0)) {

				return 'Este campo permite apenas números inteiros';

			}
		}

	},

};

</script>