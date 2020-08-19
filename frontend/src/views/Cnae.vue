<template lang="pug">

	v-container  

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroCnae(
				:atividadeCnae="atividadeCnae",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
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

import RelatorioService from '../services/relatorio.service';
import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroCnae from '@/components/FormCadastroCnae';
import GridListagem from '@/components/GridListagem';
import AtividadeCnaeService from '@/services/atividadeCnae.service';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemCnaeHeader';

export default {

	name:"Cnae",

	components: {
		PanelCadastro,
		FormCadastroCnae,
		GridListagem
	},

	data: () => {
		return {
			tituloListagem: "Listagem de CNAEs cadastrados",
			placeholderPesquisa: "Pesquisar por código ou nome do CNAE",
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			atividadeCnae: {
				codigo: '',
				nome: '',
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
			isCadastro: true,
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de CNAE",
				iconName:'fa fa-industry',
			}
		};
	},

	methods: {

		clear() {

			this.atividadeCnae.codigo= '';
			this.atividadeCnae.nome= '';
			this.atividadeCnae.ativo= true;
			this.errorMessageEmpty=true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de CNAE";
			this.dadosPanel.iconName = 'fa fa-industry';
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

					AtividadeCnaeService.cadastrar(this.atividadeCnae)

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
								{color: 'error', text: ERROR_MESSAGES.cadastroCnae + ': ' + erro.message, timeout: '6000'}
							);
						});
				} else {

					AtividadeCnaeService.editar(this.atividadeCnae)
						.then(() => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.editar, timeout: '6000'}
							);

							this.clear();
							this.updatePagination();
							this.resetaDadosFiltragem();
							this.dadosPanel.panel = [];

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.editarCnae, timeout: '6000'}
							);

							item.ativo = !item.ativo;
							this.resetaDadosCadastro();

						});
				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		checkForm() {

			return this.atividadeCnae.codigo
				&& this.atividadeCnae.codigo != ''
				&& this.atividadeCnae.nome
				&& this.atividadeCnae.nome != '';

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/atividadeCnae/relatorio");
		},

		updatePagination(parametrosFiltro) {

			AtividadeCnaeService.listar(parametrosFiltro)

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
					AtividadeCnaeService.editar(item)
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
	},

	created () {

		this.updatePagination();

	}

};

</script>
