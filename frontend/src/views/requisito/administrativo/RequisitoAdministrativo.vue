<template lang="pug">

	div

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroRequisitoAdministrativo(
				ref="formCadastroRequisitoAdministrativo"
				:requisitoAdministrativo="requisitoAdministrativo",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar",
				:cadastro="isCadastro"
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
			:parametrosFiltro="parametrosFiltro",
		)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroRequisitoAdministrativo from '@/views/requisito/administrativo/FormCadastroRequisitoAdministrativo';
import RequisitoAdministrativoService from '@/services/requisitoAdministrativo.service';
import DocumentoService from '@/services/documento.service';
import RelatorioService from '@/services/relatorio.service';
import GridListagem from '@/components/GridListagem';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemRequisitoAdministrativoHeader';

export default {

	name: "RequisitoAdministrativo",

	components: {
		PanelCadastro,
		FormCadastroRequisitoAdministrativo,
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "requisito administrativo",
			tituloListagem: 'Listagem de requisitos administrativos cadastrados',
			placeholderPesquisa: "Pesquisar pelo nome do documento ou tipo de licença",
			dadosListagem: {},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			requisitoAdministrativo: {
				documento: null,
				licencas: null,
				obrigatorio: null,
				tipoPessoa: null,
				ativo: true
			},
			headerListagem: HEADER,
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'documento.nome,asc',
				stringPesquisa: ''
			},
			isCadastro: true,
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de requisito administrativo",
				iconName:'fa fa-list-alt',
				tipo: "cadastro"
			},
		};
	},

	methods: {

		clear() {

			this.requisitoAdministrativo.documento= null;
			this.requisitoAdministrativo.licencas = null;
			this.requisitoAdministrativo.obrigatorio = null;
			this.requisitoAdministrativo.tipoPessoa = null;
			this.requisitoAdministrativo.ativo= true;
			this.errorMessageEmpty=true;
			this.resetarDadosCadastro();
			this.$refs.formCadastroRequisitoAdministrativo.$refs.toggleOptionsPessoa.setModel(this.requisitoAdministrativo.tipoPessoa);
			this.$refs.formCadastroRequisitoAdministrativo.$refs.toggleOptionsRequisitoAdm.setModel(this.requisitoAdministrativo.obrigatorio);
		},

		resetarDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'documento.nome,asc';
			this.parametrosFiltro.stringPesquisa = '';

		},

		resetarDadosCadastro() {

			this.dadosPanel.title = "Cadastro de requisito administrativo";
			this.dadosPanel.tipo = "cadastro";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;
		},

		submit() {

			if (this.checkForm()) {

				if (this.isCadastro) {
					this.cadastrar();
				} else {
					this.editar();
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
				.catch(error => {
					this.handleError(error);
				});

		},

		editar() {

			RequisitoAdministrativoService.editar(this.requisitoAdministrativo)

				.then(() => {
					this.handleSuccess(true);
				})
				.catch(error => {
					this.handleError(error, true);
				});

		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.requisitoAdministrativo.editar : ERROR_MESSAGES.requisitoAdministrativo.cadastro;
			message += error.message;

			snackbar.alert(message);

		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			snackbar.alert(message, snackbar.type.SUCCESS);

			if(edicao) this.dadosPanel.panel = [];

			this.clear();
			this.resetarDadosFiltragem();
			this.updatePagination();
			this.clear();

			if(edicao) this.dadosPanel.panel = [];

		},

		checkForm() {

			return this.requisitoAdministrativo.documento
				&& this.requisitoAdministrativo.documento !== ""
				&& this.requisitoAdministrativo.licencas
				&& this.requisitoAdministrativo.licencas.length > 0
				&& this.requisitoAdministrativo.obrigatorio
				&& this.requisitoAdministrativo.obrigatorio !== ""
				&& this.requisitoAdministrativo.tipoPessoa
				&& this.requisitoAdministrativo.tipoPessoa !== "";

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {

			if (!this.isCadastro && Array.isArray(value)) {
				return 'Este campo não permite ser editado';
			}

			return this.errorMessageEmpty || value ? '' : 'Obrigatório';
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/requisitoAdministrativo/relatorio");
		},

		editarItem(item) {

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar requisito administrativo";
			this.dadosPanel.tipo = "edição";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			item.obrigatorio = item.obrigatorio === true ? "true" : "false";
			this.requisitoAdministrativo = { ... item};

			var that = this;

			setTimeout(function() {

				that.$refs.formCadastroRequisitoAdministrativo.$refs.toggleOptionsPessoa.setModel(that.requisitoAdministrativo.tipoPessoa);
				that.$refs.formCadastroRequisitoAdministrativo.$refs.toggleOptionsRequisitoAdm.setModel(that.requisitoAdministrativo.obrigatorio);

			}, 100);

			this.requisitoAdministrativo.licencas = [item.licenca];
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ?
					'<p class="title-modal-confirm">Desativar requisito administrativo - ' + item.documento.nome + '</p>' :
					'<p class="title-modal-confirm">Ativar requisito administrativo - ' + item.documento.nome + '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o requisito administrativo, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o requisito administrativo? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o requisito administrativo, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o requisito administrativo? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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
					RequisitoAdministrativoService.ativarDesativar(item.id)
						.then(() => {

							if(!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.requisitoAdministrativo.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.requisitoAdministrativo.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetarDadosFiltragem();

						})
						.catch(error => {

							console.error(error);

							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.requisitoAdministrativo.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.requisitoAdministrativo.ativar);
							}

							item.ativo = !item.ativo;

						});
				}
			}).catch((error) => {
				console.error(error);
			});

		},

		updatePagination(parametrosFiltro) {

			RequisitoAdministrativoService.listar(parametrosFiltro)

				.then((response) => {

					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = "requisitos administrativos";

				})
				.catch(error => {

					console.error(error);
					snackbar.alert(ERROR_MESSAGES.requisitoAdministrativo.listagem);

				});

		},

	}

};

</script>