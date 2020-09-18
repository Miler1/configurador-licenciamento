<template lang='pug'>

	div

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel",
		)

			FormCadastroTaxaAdministrativa(
				ref="formCadastroTaxaAdministrativa",
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
import FormCadastroTaxaAdministrativa from '@/views/taxa/FormCadastroTaxaAdministrativa';
import GridListagem from '@/components/GridListagem';
import RelatorioService from '@/services/relatorio.service';
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
				valor: null,
				atividadeDispensavel: null,
				atividadeLicenciavel: null,
				ativo: true,
				isento: null
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
			this.taxaAdministrativa.isento = null;
			this.taxaAdministrativa.atividadeDispensavel = null;
			this.taxaAdministrativa.atividadeLicenciavel = null;
			this.taxaAdministrativa.ativo = true;
			this.errorMessageEmpty = true;
			this.$refs.formCadastroTaxaAdministrativa.$refs.toggleOptionsIsento.clearModel();
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

			TaxaAdministrativaService.salvar(this.preparaPraSalvar())
				.then(() => {
					this.handlerSuccess(false);
				})
				.catch(error => {
					this.handlerError(error, false);
				});

		},

		editar() {

			TaxaAdministrativaService.editar(this.preparaPraSalvar())
				.then(() => {

					this.handlerSuccess(true);
					this.dadosPanel.panel = [];

				})
				.catch(error => {

					this.handlerError(error, true);
					
				});

		},

		preparaPraSalvar() {

			if (this.taxaAdministrativa.isento === 'true') {

				delete this.taxaAdministrativa.valor;
				this.taxaAdministrativa.atividadeDispensavel = null;
				this.taxaAdministrativa.atividadeLicenciavel = null;
				this.taxaAdministrativa.ativo = true;

			}

			let taxaAdm = {... this.taxaAdministrativa};

			taxaAdm.isento = taxaAdm.isento === 'true';
			taxaAdm.atividadeDispensavel = taxaAdm.atividadeDispensavel ? (taxaAdm.atividadeDispensavel === 'true') : null;
			taxaAdm.atividadeLicenciavel = taxaAdm.atividadeLicenciavel ? (taxaAdm.atividadeLicenciavel === 'true') : null;
			taxaAdm.valor = taxaAdm.valor ? parseFloat(taxaAdm.valor.replace(/R\$\s|\./g, '').replace(',', '.')) : 0.0;

			return taxaAdm;

		},

		checkForm() {

			if (this.taxaAdministrativa.isento === 'true') {

				return !!this.taxaAdministrativa.ano;

			} else if (this.taxaAdministrativa.isento === 'false') {

				return this.taxaAdministrativa.ano
					&& this.taxaAdministrativa.valor
					&& this.taxaAdministrativa.valor > 0
					&& this.taxaAdministrativa.valor != 'R$ 0,00'
					&& this.taxaAdministrativa.atividadeDispensavel
					&& this.taxaAdministrativa.atividadeLicenciavel;

			}

			return false;

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value, campo) {

			if(typeof value === 'string' && value.substring(0, 2) === "R$"){

				value = value ? parseFloat(value.replace(/R\$\s|\./g, '').replace(',', '.')) : 0.0;

				if( value < 0) {return 'Este campo permite apenas números decimais maiores ou iguais a 0,01.';}

				if (!this.errorMessageEmpty && value === 0.0) { return 'Obrigatório'; }

			}
			
			return this.errorMessageEmpty || value ? '' : 'Obrigatório';
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

			this.taxaAdministrativa.isento = this.taxaAdministrativa.isento ? 'true' : 'false';

			if (this.taxaAdministrativa.isento === 'false') {

				this.taxaAdministrativa.atividadeDispensavel = this.taxaAdministrativa.atividadeDispensavel ? 'true' : 'false';
				this.taxaAdministrativa.atividadeLicenciavel = this.taxaAdministrativa.atividadeLicenciavel ? 'true' : 'false';

			}

			this.isCadastro = false;
			window.scrollTo(0,0);

			let that = this;

			setTimeout(function() {

				that.$refs.formCadastroTaxaAdministrativa.setTextFieldValor(item.valor.toLocaleString('pt-BR', { minimumFractionDigits: 2}));
				that.$refs.formCadastroTaxaAdministrativa.$refs.toggleOptionsIsento.setModel(that.taxaAdministrativa.isento);
				that.$refs.formCadastroTaxaAdministrativa.$refs.toggleAtividadeDispensavel.setModel(that.taxaAdministrativa.atividadeDispensavel);
				that.$refs.formCadastroTaxaAdministrativa.$refs.toggleAtividadeLicenciavel.setModel(that.taxaAdministrativa.atividadeLicenciavel);

			}, 100);

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

							if (!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.taxaAdministrativa.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.taxaAdministrativa.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetarDadosFiltragem();

						})
						.catch(error => {

							console.error(error);

							if (!item.ativo) {
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