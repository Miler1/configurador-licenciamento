<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroRequisitoAdministrativo(
				:requisitoAdministrativo="requisitoAdministrativo",
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
			:parametrosFiltro="parametrosFiltro",
		)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroRequisitoAdministrativo from '@/components/FormCadastroRequisitoAdministrativo';
import RequisitoAdministrativoService from '@/services/requisitoAdministrativo.service';
import DocumentoService from '@/services/documento.service';
import GridListagem from '@/components/GridListagem';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemRequisitoAdministrativoHeader';

export default {

	name: "RequisitoAdministrativo",

	components: {
		PanelCadastro,
		FormCadastroRequisitoAdministrativo,
		GridListagem
	},

	data: () => {
		return {
			tituloListagem: 'Listagem de requisitos administrativos',
			placeholderPesquisa: "Pesquisar pelo nome do requisito",
			dadosListagem: {},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			requisitoAdministrativo: {
				documento: null,
				licenca: null,
				obrigatorio:null,
				tipoPessoa:null,
				ativo: true
			},
			headerListagem: HEADER,
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
				title: "Cadastro de requisitos administrativos",
				iconName:'fa fa-file-text-o',

			},
		};
	},

	methods: {

		clear() {

			this.requisitoAdministrativo.documento= null;
			this.requisitoAdministrativo.licenca = null;
			this.requisitoAdministrativo.obrigadorio = null;
			this.requisitoAdministrativo.tipoPessoa = null;
			this.requisitoAdministrativo.ativo= true;
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

			this.dadosPanel.title = "Cadastro de requisitos administrativos";
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

			RequisitoAdministrativoService.cadastrar(this.requisitoAdministrativo)

				.then(() => {
					this.handleSuccess();
				})
				.catch(erro => {
					this.handleError(erro);
				});

		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.requisitoAdministrativo.editar : ERROR_MESSAGES.requisitoAdministrativo.cadastro;
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

			return this.requisitoAdministrativo.documento !== null
				&& this.requisitoAdministrativo.licenca !== null
				&& this.requisitoAdministrativo.obrigatorio !== null
				&& this.requisitoAdministrativo.tipoPessoa !== null;

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/requisitoAdministrativo/relatorio");
		},

		editarItem(item) {

		},

		ativarDesativarItem(item) {

		},

		updatePagination(parametrosFiltro) {

			RequisitoAdministrativoService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
				})
				.catch(erro => {

					console.error(erro);

					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.parametro.listagem + ': ' + erro.message, timeout: '6000'}
					);

				});

		},

	},

	created () {

		this.updatePagination();

	}

};

</script>