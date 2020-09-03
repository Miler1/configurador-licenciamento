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
import FormCadastroDocumento from '@/views/documento/FormCadastroDocumento';
import GridListagem from '@/components/GridListagem';
import DocumentoService from '@/services/documento.service';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import RelatorioService from '@/services/relatorio.service';
import { HEADER } from '@/utils/dadosHeader/ListagemDocumentoHeader';

export default {

	name: "Documento",

	components: {
		PanelCadastro,
		FormCadastroDocumento,
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "documento",
			tituloListagem: 'Listagem de documentos cadastrados',
			placeholderPesquisa: "Pesquisar pelo nome do documento ou nome para exibição",
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
				title: "Cadastro de documento",
				iconName:'fa fa-file-text-o',
				tipo: "cadastro"
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

			this.dadosPanel.title = "Cadastro de documento";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.dadosPanel.tipo = "cadastro";
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

			DocumentoService.cadastrar(this.documento)

				.then(() => {
					this.handleSuccess();
				})
				.catch(erro => {
					this.handleError(erro);
				});

		},

		editar() {

			DocumentoService.editar(this.documento)
				.then(response => {
					this.handleSuccess(response, true);
				})
				.catch(error => {
					this.handleError(error, true);
				});
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
			RelatorioService.baixarRelatorio("/documento/relatorio");
		},

		editarItem(item) {

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar documento";
			this.dadosPanel.tipo = "edição";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.documento = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ? 
					'<p class="title-modal-confirm">Desativar documento - ' + item.nome+ '</p>' :
					'<p class="title-modal-confirm">Ativar documento - ' + item.nome+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o documento, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o documento? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o documento, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o documento? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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
					DocumentoService.editar(item)
						.then(() => {
							
							if(!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.documento.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.documento.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);
							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.documento.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.documento.ativar);
							}

							item.ativo = !item.ativo;

						});
				}
			}).catch((error) => {
				console.error(error);
			});		
		},

		updatePagination(documentosFiltro) {

			DocumentoService.listar(documentosFiltro)

				.then((response) => {

					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = "documentos";

				})
				.catch(erro => {
					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.documento.listagem + erro.message);
				});

		},

	}

};

</script>