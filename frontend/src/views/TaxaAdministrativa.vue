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

		GridListagem.pa-7(
			:tituloAba="tituloAba",
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
import FormCadastroTaxaAdministrativa from '@/components/FormCadastroTaxaAdministrativa';
import GridListagem from '@/components/GridListagem';
import RelatorioService from '../services/relatorio.service';
import TaxaAdministrativaService from '@/services/taxaAdministrativa.service';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemTaxaAdministrativoHeader';

export default {

	name: 'TaxaAdministrativa',

	components: {

		PanelCadastro,
		FormCadastroTaxaAdministrativa,
		GridListagem
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			isCadastro: true,
			tituloAba: "taxa administrativa",
			tituloListagem: "Listagem de taxas administrativas cadastradas",
			placeholderPesquisa: "Pesquisar pelo ano da taxa administrativa",
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			taxaAdministrativa: {
				ano: null,
				valor: 0,
				atividadeDispensavel: false,
				atividadeLicenciavel: false,
				ativo: true
			},
			dadosListagem: {},
			headerListagem: HEADER,
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'ano,desc',
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
			this.resetarDadosCadastro();

		},

		resetarDadosCadastro() {

			this.dadosPanel.title = "Cadastro de taxa administrativa";
			this.dadosPanel.iconName = "mdi-cash-usd-outline";
			this.dadosPanel.tipo = "cadastro";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},

		resetarDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'ano,desc';
			this.parametrosFiltro.stringPesquisa = '';

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

			TaxaAdministrativaService.editar(this.taxaAdministrativa)
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
			this.updatePagination();
			this.resetarDadosFiltragem();

		},

		handlerError(error, edicao = false) {

			console.error(error);

			let message = edicao ? ERROR_MESSAGES.taxaAdministrativa.editar : ERROR_MESSAGES.taxaAdministrativa.cadastro;
			message += error.message;

			snackbar.alert(message);

		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/taxaAdministrativa/relatorio");
		},

		editarItem(item) {

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar taxa administrativa";
			this.dadosPanel.tipo = "edição";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.taxaAdministrativa = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ?
					'<p class="title-modal-confirm">Desativar taxa administrativa - ' + item.ano+ '</p>' :
					'<p class="title-modal-confirm">Ativar taxa administrativa - ' + item.ano+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar a taxa administrativa, ela não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar a taxa administrativa? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar a taxa administrativa, ela ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar a taxa administrativa? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor: item.ativo ? '#E6A23C' : '#67C23A',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: item.ativo ? '<i class="fa fa-minus-circle"></i> Desativar' : '<i class="fa fa-check-circle"></i> Ativar',
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {

				if(result.value) {

					item.ativo = !item.ativo;
					TaxaAdministrativaService.editar(item)
						.then(() => {

							if(!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.taxaAdministrativa.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.taxaAdministrativa.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetarDadosFiltragem();

						})
						.catch(error => {

							console.error(error);

							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.taxaAdministrativa.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.taxaAdministrativa.ativar);
							}

							item.ativo = !item.ativo;

						});

				}

			}).catch((error) => {
				console.error(error);
			});

		},

		updatePagination(parametrosFiltro) {
			
			TaxaAdministrativaService.listar(parametrosFiltro)
				
				.then((response) => {
					
					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = "taxas administrativas";

				})
				.catch(error => {

					console.error(error);

					snackbar.alert(ERROR_MESSAGES.taxaAdministrativa.listagem);
					
				});

		},

	},

	created () {

		this.updatePagination();

	}

};
</script>

<style>

</style>