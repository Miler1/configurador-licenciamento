<template lang='pug'>

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel",
		)

			FormCadastroTaxaAdministrativa(
				:taxaAdministrativa="taxaAdministrativa",
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
import FormCadastroTaxaAdministrativa from '@/components/FormCadastroTaxaAdministrativa';
import TaxaAdministrativaService from '@/services/taxaAdministrativa.service';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: 'TaxaAdministrativa',

	components: {

		PanelCadastro,
		FormCadastroTaxaAdministrativa,
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			isCadastro: true,
			tituloAba: "taxa administrativa",
			tituloListagem: "Listagem de taxas administrativas",
			placeholderPesquisa: "Pesquisar por código ou descrição da taxa administrativa",
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			taxaAdministrativa: {
				ano: null,
				valor: 0,
				atividadeDispensavel: false,
				atividadeLicenciavel: false,
				ativo: true
			},
			// dadosListagem: {},
			// headerListagem: HEADER,
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
				title: "Cadastro de taxa administrativa",
				iconName:'mdi-cash-usd-outline',
				tipo: "cadastro"
			},
		};
	},
	
	methods: {

		clear() {

			this.taxaAdministrativa.ano = null;
			this.taxaAdministrativa.valor = 0;
			this.taxaAdministrativa.atividadeDispensavel = false;
			this.taxaAdministrativa.atividadeLicenciavel = false;
			this.taxaAdministrativa.ativo = true;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de taxa administrativa";
			this.dadosPanel.iconName = "mdi-cash-usd-outline";
			this.dadosPanel.tipo = "cadastro";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},
		
		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {
					this.cadastrar();
				} else {
					this.editar();
				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		cadastrar() {

			TaxaAdministrativaService.salvar(this.taxaAdministrativa)
				.then(() => {
					this.handlerSuccess(false);
				})
				.catch(error => {
					this.handlerError(error, false);
				});

		},

		editar() {

			ParametroService.editar(this.parametro)
				.then(() => {

					this.handlerSuccess(true);
					this.dadosPanel.panel = [];

				})
				.catch(error => {

					this.handlerError(error, true);
					
				});

		},

		checkForm() {
			return this.taxaAdministrativa.ano
				&& this.taxaAdministrativa !== "";
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		handlerSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			snackbar.alert(message, snackbar.type.SUCCESS);
			
			this.clear();
			// this.updatePagination();
			// this.resetaDadosFiltragem();

		},

		handlerError(error, edicao = false) {

			console.error(error);

			let message = edicao ? ERROR_MESSAGES.parametro.editar : ERROR_MESSAGES.parametro.cadastro;
			message += error.message;

			snackbar.alert(message);

		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/taxaAdministrativa/relatorio");
		},

		editarItem(item) {

		},

		ativarDesativarItem(item) {

		},

		updatePagination(parametrosFiltro) {

		},

	},

	created () {

		this.updatePagination();

	}

};
</script>

<style>

</style>