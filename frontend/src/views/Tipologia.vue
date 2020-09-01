<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroTipologia(
				:tipologia="tipologia",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessageEmpty="errorMessageEmpty",
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
import FormCadastroTipologia from '@/components/FormCadastroTipologia';
import GridListagem from '@/components/GridListagem';
import TipologiaService from '../services/tipologia.service';
import RelatorioService from '../services/relatorio.service';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemTipologiaHeader';

export default {

	name: "Tipologia",

	components: {
		PanelCadastro,
		FormCadastroTipologia,
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "tipologia",
			tituloListagem: "Listagem de tipologias cadastradas",
			placeholderPesquisa: "Pesquisar pelo nome da tipologia",
			headerListagem: HEADER,
			dadosListagem: {
				nomeItem: "tipologias",
			},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
			errorMessageEmpty: true,
			tipologia:{
				// codigo: '',
				nome: '',
				ativo: true
			},
			isCadastro: true,
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de tipologia",
				iconName:'mdi-format-list-bulleted-square',
				tipo: "cadastro"
			},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus"
		};
	},

	methods: {

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

			TipologiaService.cadastrar(this.tipologia)
				.then(response => {
					this.handleSuccess(response);
				})
				.catch(error => {
					this.handleError(error);
				});
		},

		editar() {

			TipologiaService.editar(this.tipologia)
				.then(response => {
					this.handleSuccess(response, true);
				})
				.catch(error => {
					this.handleError(error, true);
				});
		},

		clear() {

			//this.tipologia.codigo = '';
			this.tipologia.nome = '';
			this.tipologia.ativo = true;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		checkForm() {
			return this.tipologia.nome && this.tipologia.nome !== '';
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de tipologia";
			this.dadosPanel.iconName = "mdi-format-list-bulleted-square";
			this.dadosPanel.tipo = "cadastro";
			this.labelBotaoCadastrarEditar = "Cadastrar";
			this.iconBotaoCadastrarEditar = "mdi-plus";
			this.isCadastro = true;

		},

		resetaDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'nome,asc';
			this.parametrosFiltro.stringPesquisa = '';
			this.isCadastro = true;

		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.tipologia.editar : ERROR_MESSAGES.tipologia.cadastro;
			message += error.message;

			snackbar.alert(message);
			
		},

		handleSuccess(response, edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			// if(response.data.codigo !== this.tipologia.codigo) {
			// 	message = ` A tipologia salva com o código: ${response.data.codigo}`;
			// }

			snackbar.alert(message, snackbar.type.SUCCESS);

			this.clear();
			this.updatePagination();
			this.resetaDadosFiltragem();
			this.dadosPanel.panel = [];
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/tipologia/relatorio");
		},

		updatePagination(parametrosFiltro) {

			TipologiaService.listar(parametrosFiltro)

				.then((response) => {

					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = "tipologias";

				})
				.catch(erro => {

					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.tipologia.listagem + erro.message);

				});

		},

		editarItem(item) {
			
			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar tipologia";
			this.dadosPanel.tipo = "edição";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.tipologia = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {
			
			this.$fire({

				title: item.ativo ?
					'<p class="title-modal-confirm">Desativar Tipologia - ' + item.nome+ '</p>' :
					'<p class="title-modal-confirm">Ativar Tipologia - ' + item.nome+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar a Tipologia, ela não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar a Tipologia? Esta opção pode ser desfeita a qualquer momento ao ativá-la novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar a Tipologia, ela ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar a Tipologia? Esta opção pode ser desfeita a qualquer momento ao desativá-la novamente.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor: item.ativo ? '#E6A23C' : '#67C23A',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: item.ativo ? '<i class="fa fa-minus-circle"></i> Desativar' : '<i class="fa fa-check-circle"></i> Ativar',
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true,

			}).then((result) => {

				if(result.value) {

					item.ativo = !item.ativo;
					TipologiaService.editar(item)
						.then(() => {
							
							if(!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.tipologia.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.tipologia.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);
							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.tipologia.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.tipologia.ativar);
							}

							item.ativo = !item.ativo;

						});
				}
			}).catch((error) => {
				console.error(error);
			});
		},
	}

};

</script>