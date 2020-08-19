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
import { SET_SNACKBAR } from '../store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemTipologiaHeader';

export default {

	name: "Tipologia",

	components: {
		PanelCadastro,
		FormCadastroTipologia,
		GridListagem
	},

	data: () => {
		return {
			tituloListagem: "Listagem de tipologias",
			placeholderPesquisa: "Pesquisar pelo nome da tipologia",
			headerListagem: HEADER,
			dadosListagem: {},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 20,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
			errorMessageEmpty: true,
			tipologia:{
				codigo: '',
				nome: ''
			},
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de tipologia",
				iconName:'mdi-format-list-bulleted-square',
			},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus"
		};
	},

	methods: {

		submit() {

			if (this.checkForm()) {

				this.tipologia.ativo = true;
				TipologiaService.cadastrar(this.tipologia)
					.then(response => {
						this.handleSuccess(response);
					})
					.catch(error => {
						this.handleError(error);
					});

			} else {
				this.errorMessageEmpty = false;
			}
		},

		clear() {

			this.tipologia.codigo = '';
			this.tipologia.nome = '';
			this.errorMessageEmpty=true;

		},

		checkForm() {
			return this.tipologia.nome && this.tipologia.nome !== '';
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		checkErrorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		handleError(error) {
			let message = error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: ERROR_MESSAGES.cadastroTipologia + ': ' + message, timeout: '6000'}
			);
		},

		handleSuccess(response) {
			
			let message = '';

			if(response.data.codigo !== this.tipologia.codigo) {
				message = ` A tipologia salva com o código: ${response.data.codigo}`;
			}

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: SUCCESS_MESSAGES.cadastro + message, timeout: '6000'}
			);

			this.clear();
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/tipologia/relatorio");
		},

		updatePagination(parametrosFiltro) {

			TipologiaService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
				})
				.catch(erro => {
					console.error(erro);
					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.listagemCnae + ': ' + erro.message, timeout: '6000'}
					);
				});

		},

		editarItem(item) {
			
			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar CNAE";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.atividadeCnae = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {
			
			this.$fire({

				title: item.ativo ? 
					'<p class="title-modal-confirm">Desativar CNAE - ' + item.codigo+ '</p>' : 
					'<p class="title-modal-confirm">Ativar CNAE - ' + item.codigo+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o CNAE, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o CNAE? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o CNAE, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o CNAE? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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
					TipologiaService.editar(item)
						.then(() => {
							
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.desativarCnae, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.ativarCnae, timeout: '6000'}
								);

							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.desativarCnae, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.ativarCnae, timeout: '6000'}
								);

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

<style lang="less">

</style>