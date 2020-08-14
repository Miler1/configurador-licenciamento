<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:title="panelTitle",
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroLicenca(
				:licenca="licenca",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:validadeErrorMessage="validadeErrorMessage"
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
			:parametrosFiltro="parametrosFiltro",
		)

</template>

<script>

import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroLicenca from '@/components/FormCadastroLicenca';
import LicencaService from '../services/licenca.service';
import RelatorioService from '../services/relatorio.service';
import GridListagem from '@/components/GridListagem';
import { SET_SNACKBAR } from '../store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemLicencaHeader';

export default {

	name: "Licenca",

	components: {
		PanelCadastro,
		FormCadastroLicenca,
		GridListagem
	},

	data: () => {
		return {
			panelTitle: "Cadastro de licença ambiental",
			errorMessageEmpty: true,
			licenca:{
				sigla: null,
				nome: null,
				finalidade: null,
				validade: null,
				ativo: true
			},
			tituloListagem: "Listagem de licenças ambientais cadastradas",
			placeholderPesquisa: "Pesquisar por tipo ou nomenclatura da licença",
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
			},
		};
	},

	methods: {

		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {

						
					LicencaService.salvar(this.licenca)
						.then((response) => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.cadastro, timeout: '6000'}
							);
							this.clear();
							this.updatePagination();
							this.parametrosFiltro.pagina = 0;

						})

						.catch(erro => {
							console.error(erro);
							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.cadastroLicenca + ': ' + erro.message, timeout: '6000'}
							);
						});

				} else {

					LicencaService.editar(this.licenca)
						.then(() => {

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'success', text: SUCCESS_MESSAGES.editar, timeout: '6000'}
							);

							this.clear();
							this.updatePagination();
							this.parametrosFiltro.pagina = 0;

						})
						.catch(erro => {

							console.error(erro);

							this.$store.dispatch(SET_SNACKBAR,
								{color: 'error', text: ERROR_MESSAGES.editarLicenca, timeout: '6000'}
							);

							item.ativo = !item.ativo;
							this.resetaDadosCadastro();

						});
				}

			} else {
				this.errorMessageEmpty = false;
			}
		},

		clear() {

			this.licenca.sigla = null;
			this.licenca.nome = null;
			this.licenca.validade = null;
			this.licenca.finalidade = null;
			this.errorMessageEmpty = true;

		},

		checkForm() {

			if (this.licenca.finalidade === 'CADASTRO') {

				return this.licenca.sigla &&
					this.licenca.sigla != ''	&&
					this.licenca.nome &&
					this.licenca.nome != '' &&
					this.licenca.finalidade &&
					this.licenca.finalidade != '';

			} else {

				return this.licenca.sigla &&
					this.licenca.sigla != ''	&&
					this.licenca.nome &&
					this.licenca.nome != '' &&
					this.licenca.finalidade &&
					this.licenca.finalidade != '' &&
					this.licenca.validade &&
					this.licenca.validade != '';
			}

		},	

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		validadeErrorMessage() {
			
			if (!this.errorMessageEmpty && !this.licenca.validade && this.licenca.finalidade && this.licenca.finalidade != 'CADASTRO') {
				
				return 'Obrigatório';
			}

			return [];
		},	

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},
		
		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/licenca/relatorio-licenca");
		},

		updatePagination(parametrosFiltro) {

			LicencaService.listar(parametrosFiltro)

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
			this.panelTitle = "Editar licença ambiental";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.licenca = { ... item};
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
						<b>Tem certeza que deseja desativar o CNAE? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente o CNAE.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o CNAE, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o CNAE? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente o CNAE.</b>
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
							this.parametrosFiltro.pagina = 0;

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
		}
	},

	created () {

		this.updatePagination();

	}

};

</script>

<style lang="less">

</style>