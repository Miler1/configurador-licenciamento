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
		GridListagem.pa-7(
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:editarItem="editarItem",
			:ativarDesativarItem="ativarDesativarItem",
			:parametrosFiltro="parametrosFiltro"
		)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroDocumento from '@/components/FormCadastroDocumento';
import GridListagem from '@/components/GridListagem';
import DocumentoService from '@/services/documento.service';
import RelatorioService from '../services/relatorio.service';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemDocumentoHeader';

export default {

	name: "Documento",

	components: {
		PanelCadastro,
		FormCadastroDocumento,
		GridListagem
	},

	data: () => {
		return {
			tituloListagem: 'Listagem de documentos',
			placeholderPesquisa: "Pesquisar pelo nome do documento",
			headerListagem: HEADER,
			dadosListagem: {},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			documento: {
				nome: '',
				prefixoNomeArquivo: '',
				ativo: true
			},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
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

		resetaDadosFiltragem() {
			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'dataCadastro,desc';
			this.parametrosFiltro.stringPesquisa = '';
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
			this.updatePagination();
			this.resetaDadosFiltragem();

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
		},
		gerarRelatorio() {
			
		},

		editarItem(item) {

		},

		ativarDesativarItem(item) {
		
		},

		updatePagination(documentosFiltro) {

			DocumentoService.listar(documentosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
				})
				.catch(erro => {
					console.error(erro);
					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.documento.listagem + ': ' + erro.message, timeout: '6000'}
					);
				});

		},

	},

	created () {

		this.updatePagination();

	}

};

</script>