<template lang="pug">

	v-container

		PanelCadastro.pa-7(
			:clear="clear",
			:dadosPanel="dadosPanel"
		)
			FormCadastroPergunta(
				:pergunta="pergunta",
				:clear="clear",
				:submit="submit",
				:resetErrorMessage="resetErrorMessage",
				:errorMessage="errorMessage",
				:labelBotaoCadastrarEditar="labelBotaoCadastrarEditar",
				:iconBotaoCadastrarEditar="iconBotaoCadastrarEditar"
			)

		GridListagem.pa-7(
			:tituloAba="tituloAba"
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:editarItem="editarItem",
			:parametrosFiltro="parametrosFiltro"
		)

</template>

<script>

import RelatorioService from '@/services/relatorio.service';
import PanelCadastro from '@/components/PanelCadastro';
import FormCadastroPergunta from '@/views/pergunta/FormCadastroPergunta';
import GridListagem from '@/components/GridListagem';
import PerguntaService from '@/services/pergunta.service';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemPerguntaHeader';

export default {

	name: "Pergunta",

	components: {
		PanelCadastro,
		FormCadastroPergunta,
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "Pergunta",
			tituloListagem: 'Listagem de tabelas de perguntas',
			placeholderPesquisa: "Pesquisar pelo título",
			headerListagem: HEADER,
			dadosListagem: {},
			labelBotaoCadastrarEditar: "Cadastrar",
			iconBotaoCadastrarEditar: "mdi-plus",
			errorMessageEmpty: true,
			pergunta: {
				codigo: '',
				texto: '',
				respostas: [
					{
						texto: "Sim",
						permiteLicenciamento: false,
					},
					{
						texto: "Não",
						permiteLicenciamento: false,
					}
				],
				ativo: true
			},
			isCadastro: true,
			dadosPanel: {
				items: 1,
				panel: [],
				readonly: true,
				title: "Cadastro de pergunta",
				tipo: "cadastro",
				iconName:'mdi-lock-question',
			},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
		};
	},

	methods: {

		clear() {

			this.pergunta.texto= '';

			this.pergunta.respostas = [
				{
					texto: "Sim",
					permiteLicenciamento: false,
				},
				{
					texto: "Não",
					permiteLicenciamento: false,
				}
			],

			this.pergunta.ativo = true;
			this.errorMessageEmpty = true;
			this.resetaDadosCadastro();

		},

		resetaDadosCadastro() {

			this.dadosPanel.title = "Cadastro de pergunta";
			this.dadosPanel.iconName = 'mdi-lock-question';
			this.dadosPanel.tipo = "cadastro";
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

					PerguntaService.cadastrar(this.pergunta)

						.then(() => {

							snackbar.alert(SUCCESS_MESSAGES.cadastro, snackbar.type.SUCCESS);

							this.clear();
							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {
							snackbar.alert(ERROR_MESSAGES.pergunta.cadastro + erro.message);
						});

				} else {

					PerguntaService.editar(this.pergunta)
						.then(() => {

							snackbar.alert(SUCCESS_MESSAGES.editar, snackbar.type.SUCCESS);

							this.clear();
							this.updatePagination();
							this.resetaDadosFiltragem();
							this.dadosPanel.panel = [];

						})
						.catch(erro => {

							console.error(erro);

							snackbar.alert(ERROR_MESSAGES.pergunta.editar);

							this.resetaDadosCadastro();

						});
				}
				
			} else {
				this.errorMessageEmpty = false;
			}

		},

		checkForm() {

			let perguntasValidas = false;

			this.pergunta.respostas.forEach((item) => {

				if(item.permiteLicenciamento)
					perguntasValidas = true;

			});

			return this.pergunta.texto != null
				&& this.pergunta.texto != ''
				&& perguntasValidas;

		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
		},

		errorMessage(value) {
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/pergunta/relatorio");
		},

		updatePagination(parametrosFiltro) {

			PerguntaService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = "perguntas";

				})
				.catch(erro => {

					console.error(erro);

					snackbar.alert(ERROR_MESSAGES.pergunta.listagem);
					
				});

		},

		editarItem(item) {
			
			this.dadosPanel.panel = [0];
			this.dadosPanel.title = "Editar pergunta";
			this.dadosPanel.tipo = "edição";
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.pergunta = { ... item};
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
					perguntaService.editar(item)
						.then(() => {
							
							if(!item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.pergunta.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.pergunta.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(erro => {

							console.error(erro);

							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.pergunta.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.pergunta.ativar);
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
