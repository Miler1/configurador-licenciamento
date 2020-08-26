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
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar",
				:cadastro="isCadastro"
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
import RelatorioService from '../services/relatorio.service';
import GridListagem from '@/components/GridListagem';
import { SET_SNACKBAR } from '@/store/actions.type';
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
			tituloListagem: 'Listagem de requisitos administrativos',
			placeholderPesquisa: "Pesquisar pelo nome do documento ou tipo licença",
			dadosListagem: {},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			requisitoAdministrativo: {
				documento: null,
				licencas: null,
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
			this.requisitoAdministrativo.licencas = null;
			this.requisitoAdministrativo.obrigatorio = null;
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
				.catch(erro => {
					this.handleError(erro);
				});

		},

		editar() {

			RequisitoAdministrativoService.editar(this.requisitoAdministrativo)

				.then(() => {
					this.handleSuccess();
				})
				.catch(erro => {
					this.handleError(erro, true);
				});
		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.requisitoAdministrativo.editar : ERROR_MESSAGES.requisitoAdministrativo.cadastro;
			message += error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: message, timeout: '9000'}
			);

		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.edicao : SUCCESS_MESSAGES.cadastro;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: message, timeout: '6000'}
			);

			if(edicao) this.dadosPanel.panel = [];

			this.clear();
			this.resetaDadosFiltragem();
			this.updatePagination();
			this.clear();

			if(edicao) this.dadosPanel.panel = [];

		},

		checkForm() {

			return this.requisitoAdministrativo.documento !== null
				&& this.requisitoAdministrativo.licencas !== null
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

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar requisito administrativo";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.requisitoAdministrativo = { ... item};
			this.requisitoAdministrativo.licencas = [item.licenca];
			this.isCadastro = false;

			console.log(this.requisitoAdministrativo);
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ?
					'<p class="title-modal-confirm">Desativar Requisito Administrativo - ' + item.documento.nome + '</p>' :
					'<p class="title-modal-confirm">Ativar Requisito Administrativo - ' + item.documento.nome + '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o requisito, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o requisito? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o requisito, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o requisito? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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

					RequisitoAdministrativoService.editar(item)
						.then(() => {
							
							this.handleSuccess();

						})
						.catch(erro => {

							this.handleError(erro, true);

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
				})
				.catch(erro => {

					console.error(erro);

					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.requisitoAdministrativo.listagem + erro.message, timeout: '6000'}
					);

				});

		},

	},

	created () {

		this.updatePagination();

	}

};

</script>