<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroDocumento(
				:documento="documento",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar"
			)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroDocumento from '@/components/FormCadastroDocumento';
import DocumentoService from '@/services/documento.service';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "Documento",

	components: {
		PanelCadastro,
		FormCadastroDocumento
	},

	data: () => {
		return {
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			documento: {
				nome: '',
				prefixoNomeArquivo: '',
				ativo: true
			},
			isCadastro: true,
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de Documentos",
				iconName:'fa fa-file-text-o',

			}
		};
	},

	methods: {

		clear() {

			this.documento.nome= '';
			this.documento.prefixoNomeArquivo= '';
			this.documento.ativo= true;
			this.errorMessageEmpty=true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de Documentos";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},

		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {
					this.cadastrar();
				} else {
					// Edição
				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		cadastrar() {

			DocumentoService.cadastrar(this.documento)

				.then(() => {
					this.handleSuccess();
				})
				.catch(erro => {
					this.handleError(erro);
				});

		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.documento.editar : ERROR_MESSAGES.documento.cadastro;
			message += error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: message, timeout: '9000'}
			);

			item.ativo = !item.ativo;
			this.resetaDadosCadastro();
		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.edicao : SUCCESS_MESSAGES.cadastro;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: message, timeout: '6000'}
			);

			this.clear();

			// Descomentar quando fizer a edição
			// this.updatePagination();
			// this.resetaDadosFiltragem();

			if(edicao) this.dadosPanel.panel = [];
		},

		checkForm() {

			return this.documento.nome
				&& this.documento.nome != ''
				&& this.documento.prefixoNomeArquivo
				&& this.documento.prefixoNomeArquivo != '';

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		}
	}

};

</script>