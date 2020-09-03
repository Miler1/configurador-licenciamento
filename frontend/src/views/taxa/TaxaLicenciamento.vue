<template lang="pug">

	v-container
        
		GridListagem.pa-7(
			:tituloAba="tituloAba"
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:editarItem="editarItem",
			:parametrosFiltro="parametrosFiltro"
		)

</template>

<script>

import GridListagem from '@/components/GridListagem';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemTaxaLicenciamentoHeader';
import TaxaService from '@/services/taxaLicenciamento.service';

export default {

	name: "Taxa",

	components: {

		GridListagem
	},

	data: () => {
		return {
			tituloAba:' taxa',
			tituloListagem: 'Listagem de taxas cadastradas',
			placeholderPesquisa: "Pesquisar pelo código ou descrição",
			headerListagem: HEADER,
			dadosListagem: {
				nomeItem: "taxas",
			},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			taxa: {
				porteEmpreendimento: '',
				potencialPoluidor: '',
				licenca: '',
				codigo: '',
				valor: '',
				ativo: true
			},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
			isCadastro: true,

		};
	},

	methods: {

		clear() {

			// this.documento.nome= '';
			// this.documento.prefixoNomeArquivo= '';
			// this.documento.ativo= true;
			// this.errorMessageEmpty=true;
			// this.resetaDadosCadastro();

		},

		resetaDadosFiltragem() {
			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'dataCadastro,desc';
			this.parametrosFiltro.stringPesquisa = '';
		},


		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de Taxas";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},

		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {
					// this.cadastrar();
				} else {
					// this.editar();
				}

			} else {
				this.errorMessageEmpty = false;
			}

		},



		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.documento.editar : ERROR_MESSAGES.documento.cadastro;
			message += error.message;

			snackbar.alert(message);

		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			snackbar.alert(message, snackbar.type.SUCCESS);

			this.clear();
			this.updatePagination();
			this.resetaDadosFiltragem();

			if(edicao) this.dadosPanel.panel = [];

		},

		checkForm() {

			// return this.documento.nome
			// 	&& this.documento.nome != ''
			// 	&& this.documento.prefixoNomeArquivo
			// 	&& this.documento.prefixoNomeArquivo != '';

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

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar documento";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.documento = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		updatePagination(taxasFiltro) {

			TaxaService.listar(taxasFiltro)

				.then((response) => {

					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = 'taxas';

				})
				.catch(erro => {

					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.taxa.listagem + erro.message);

				});

		},

	}

};

</script>