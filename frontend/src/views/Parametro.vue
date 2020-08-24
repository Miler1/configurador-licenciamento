<template lang="pug">
	
	v-container
	
		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel",
		)
			FormCadastroParametro(
				:parametro="parametro",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:codigoErrorMessage ="codigoErrorMessage",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar",
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
import FormCadastroParametro from '@/components/FormCadastroParametro';
import ParametroService from '@/services/parametro.service';
import RelatorioService from '../services/relatorio.service';
import GridListagem from '@/components/GridListagem';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosMockados/ListagemParametroHeader';

export default {

	name: "Parametro",

	components: {
		PanelCadastro,
		FormCadastroParametro,
		GridListagem
	},

	data: () => {
		return {
			errorMessageEmpty: true,
			isCadastro: true,
			tituloListagem: "Listagem de parâmetros cadastrados",
			placeholderPesquisa: "Pesquisar por código ou descrição do parâmetro",
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			parametro: {
				codigo: null,
				nome: null,
				casasDecimais: null,
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
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de parâmetros",
				iconName:'fa fa-sliders',
			},
		};
	},
	
	methods: {

		clear() {

			this.parametro.codigo = null;
			this.parametro.nome = null;
			this.parametro.casasDecimais = null;
			this.parametro.ativo = true;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de parâmetros";
			this.dadosPanel.iconName = "fa fa-sliders";
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

					ParametroService.salvar(this.parametro)
						.then(() => {

							this.clear();
							this.updatePagination();
							this.resetaDadosFiltragem();
							this.handlerSuccess(false);

						})
						.catch(erro => {

							console.error(erro);

							this.handlerError(erro, false);

						});
						
				} else {

					ParametroService.editar(this.parametro)
						.then(() => {

							this.handlerSuccess(true);

							this.clear();
							this.updatePagination();
							this.resetaDadosFiltragem();
							this.dadosPanel.panel = [];

						})
						.catch(erro => {

							console.error(erro);

							this.resetaDadosCadastro();
							this.handlerError(erro, true);
					
						});

				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		checkForm() {

			return this.parametro.codigo
				&& this.parametro.codigo != ''
				&& this.parametro.nome
				&& this.parametro.nome != ''
				&& this.parametro.casasDecimais
				&& this.parametro.casasDecimais != '';
				
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		codigoErrorMessage(casasDecimais) {

			let er = /^-?[0-9]+$/;	
			let onlyIntegers = 'Este campo permite apenas números inteiros e maiores e iguais a zero';
		
			if (casasDecimais && casasDecimais != '') {

				if (!er.test(casasDecimais)) {
					return onlyIntegers;
				}

				casasDecimais = parseInt(casasDecimais);

			}

			if (!this.errorMessageEmpty && casasDecimais === null) {

				return msgField('obrigatorio');

			} else if ((casasDecimais === '' || (casasDecimais != null && casasDecimais < 0))) {

				return onlyIntegers;

			}

		},

		handlerSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: message, timeout: '6000'}
			);

		},

		handlerError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.parametro.editar : ERROR_MESSAGES.parametro.cadastro;
			message += error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: message, timeout: '6000'}
			);

		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/parametro/relatorio");
		},

		editarItem(item) {

			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar parâmetro";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.parametro = { ... item};
			this.isCadastro = false;
			window.scrollTo(0,0);

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ? 
					'<p class="title-modal-confirm">Desativar Parâmetro - ' + item.codigo+ '</p>' :
					'<p class="title-modal-confirm">Ativar Parâmetro - ' + item.codigo+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o Parâmetro, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o Parâmetro? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o Parâmetro, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o Parâmetro? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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
					ParametroService.editar(item)
						.then(() => {
							
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.parametro.desativar, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.parametro.ativar, timeout: '6000'}
								);

							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.parametro.desativar + erro.message, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.parametro.ativar + erro.message, timeout: '6000'}
								);

							}

							item.ativo = !item.ativo;

						});

				}

			}).catch((error) => {
				console.error(error);
			});

		},

		updatePagination(parametrosFiltro) {

			ParametroService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
				})
				.catch(erro => {

					console.error(erro);

					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.parametro.listagem + ': ' + erro.message, timeout: '6000'}
					);

				});

		},

	},

	created () {

		this.updatePagination();

	}

};

</script>