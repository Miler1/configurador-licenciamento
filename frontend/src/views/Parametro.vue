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
import FormCadastroParametro from '@/components/FormCadastroParametro';
import ParametroService from '@/services/parametro.service';
import RelatorioService from '../services/relatorio.service';
import GridListagem from '@/components/GridListagem';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemParametroHeader';

export default {

	name: "Parametro",

	components: {
		PanelCadastro,
		FormCadastroParametro,
		GridListagem
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			isCadastro: true,
			tituloListagem: "Listagem de parâmetros cadastrados",
			placeholderPesquisa: "Pesquisar por código ou nome do parâmetro",
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			parametro: {
				codigo: null,
				nome: null,
				casasDecimais: null,
				ativo: true
			},
			dadosListagem: {},
			headerListagem: HEADER,
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
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

			this.dadosPanel.title = "Cadastro de parâmetros";
			this.dadosPanel.iconName = "fa fa-sliders";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},

		resetaDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'dataCadastro,desc';
			this.parametrosFiltro.stringPesquisa = '';

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
							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.parametro.cadastro + ': ' + erro.message, timeout: '6000'}
							);

						});
						
				} else {

					ParametroService.editar(this.parametro)
						.then(() => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.parametro.editar, timeout: '6000'}
							);

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.parametro.editar + erro.message, timeout: '6000'}
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

		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/parametro/relatorio");
		},

		editarItem(item) {

		},

		ativarDesativarItem(item) {
		
		},

		updatePagination(parametrosFiltro) {

			ParametroService.listar(parametrosFiltro)

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