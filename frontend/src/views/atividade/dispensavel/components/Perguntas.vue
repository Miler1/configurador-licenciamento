<template lang="pug">

#step-cnaes-atividade-dispensavel-pergunta
	div.pb-7
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Perguntas
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="requisito")
						v-container.pa-0
							v-row
								v-col(cols="12", md="12")
									v-label Pergunta
									v-autocomplete#QA-select-atividade-dispensavel-pergunta(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="pergunta",
										:items="perguntasDisponiveis",
										item-text="texto",
										:error-messages="errorMessage(pergunta)",
										no-data-text="Nenhuma pergunta encontrada",
										@click.native="resetErrorMessage",
										required,
										return-object=true
									)

							v-row
								v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
									a#QA-limpar-dados-requisito-tecnico.d-flex.flex-row.align-center.justify-end(@click="clearPergunta")
										v-icon mdi-delete
										span Limpar dados

									v-btn#QA-btn-adicionar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
										v-icon mdi-plus
										span Adicionar

									v-btn#QA-btn-editar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusao")
										v-icon mdi-pencil
										span Editar

		GridListagemInclusao.px-7(
			:tituloListagem="tituloListagem",
			:headers="headerListagem",
			:dadosListagem="atividadeDispensavel.perguntas",
			:editarItem="editarItem",
			:excluirItem="excluirItem",
			:labelNoData="labelNoData",
			:placeholderPesquisa="placeholderPesquisa",
			:tituloTooltip="tituloTooltip",
			:labelNoResultset="semResultados"
		)

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import perguntaService from '@/services/pergunta.service';
import { HEADER } from '@/utils/dadosHeader/ListagemAtividadeDispensavelPerguntasHeader';

export default {

	name:'PassoPerguntas',

	components: {
		GridListagemInclusao
	},
	
	data: () => {

		return {

			perguntasCadastradas: [],
			perguntasDisponiveis: [],

			pergunta: null,

			dadosPanel: {
				panel: [0, 1],
				readonly: true,
			},

			headerListagem: HEADER,

			tituloTooltip: "pergunta",
			placeholderSelect: "Selecione",
			errorMessageEmpty: true,
			tituloListagem: "Listagem de perguntas adicionadas para esta atividade",
			placeholderPesquisa: "Pesquisar pela pergunta ou respostas esperadas",
			labelNoData: 'Não existem perguntas adicionadas.',
			semResultados: 'Nenhuma pergunta encontrada com a pesquisa informada.',
			dadosListagem: [],
			isInclusao: true,
			indexItemEdicao: null,
			allowRedirect: false
		};

	},

	methods: {

		editarItem(item) {

			window.scrollTo(0,0);

			this.perguntasDisponiveis.push(item);

			this.pergunta = item;

			this.indexItemEdicao = this.atividadeDispensavel.perguntas.indexOf(item);
			this.isInclusao = false;

		},

		excluirItem(item) {

			let modal = {};

			modal.title = `Remover pergunta - ${item.texto}`;
			modal.mensagem = `Ao remover a pergunta, ela não estará mais vinculada nesse(s) CNAE(s) dispensável(eis).`;
			modal.textoConfirmacao = `Tem certeza que deseja remover a pergunta? Esta opção pode ser desfeita a qualquer momento ao adicioná-la novamente.`;

			modal.textoConfirmar = `Confirmar`;
			modal.textoCancelar = `Cancelar`;

			modal.acao = () => {

				let indexItemExclusao = this.dadosListagem.indexOf(item);
				this.atividadeDispensavel.perguntas.splice(indexItemExclusao, 1);

			};

			this.modalConfirmacao(modal);
		},

		errorMessage(item) {

			if(this.erro.invalido && this.pergunta) {
				return 'Adicione os itens primeiro.';
			}

			return (this.errorMessageEmpty || item) && !this.erro.invalido ? '' : 'Obrigatório';

		},

		resetErrorMessage() {

			this.errorMessageEmpty = true;
			this.erro.invalido = false;

		},

		clearPergunta() {

			this.pergunta = null;
			this.isInclusao = true;
			this.filtrarPerguntasDisponiveis();
			this.resetErrorMessage();

		},

		incluirDados() {

			if (this.pergunta) {

				if (this.isInclusao) {

					this.atividadeDispensavel.perguntas.push(this.pergunta);
					this.clearPergunta();

				} else {

					this.atividadeDispensavel.perguntas.splice(this.indexItemEdicao, 1, this.pergunta);

					this.indexItemEdicao = null;
					this.isInclusao = true;
					this.clearPergunta();

				}

				this.filtrarPerguntasDisponiveis();

			} else {
				this.errorMessageEmpty = false;
			}

		},

		modalConfirmacao(conteudo) {

			let modal = {};

			modal.title = `<p class="title-modal-confirm">${conteudo.title}</p>`;
			modal.html = '';

			modal.html += `<p class="message-modal-confirm">${conteudo.mensagem}</p>`;
			modal.html += `<p class="message-modal-confirm">`;
			modal.html += `    <b>${conteudo.textoConfirmacao}</b>`;
			modal.html += `</p>`;

			modal.confirmButtonText = `<i class="mdi mdi-delete"></i> ${conteudo.textoConfirmar}`;
			modal.cancelButtonText = `<i class="fa fa-close"></i> ${conteudo.textoCancelar}`,

			modal.showCancelButton = true;
			modal.confirmButtonColor = '#F56C6C';
			modal.cancelButtonColor = '#FFF';
			modal.showCloseButton = true;
			modal.focusConfirm = false;
			modal.reverseButtons = true;

			this.$fire(modal)
				.then((result) => {

					if(result.value) {
						conteudo.acao();
					}

				});

		},

		filtrarPerguntasDisponiveis() {

			let that = this;

			this.perguntasDisponiveis = this.perguntasCadastradas.filter(item => {

				let exists = false;

				that.atividadeDispensavel.perguntas.forEach(element => {
					exists = element.id === item.id ? true : exists;
				});

				return !exists;

			});

		}

	},

	computed: {

	},

	props: {

		atividadeDispensavel: {
			type: [Object]
		},
		erro: {
			type: [Object]
		}

	},

	created(){
	},

	mounted() {

		perguntaService.listarPerguntasAtivas()
			.then(resposta => {
				this.perguntasCadastradas = resposta.data;

				this.perguntasCadastradas.forEach((pergunta) => {

					pergunta.respostasEsperadas = "";
					let primeiro = true;

					pergunta.respostas.forEach((resposta , index) => {

						if(resposta.permiteLicenciamento){

							if(!primeiro){
								pergunta.respostasEsperadas += " / ";
							}

							pergunta.respostasEsperadas += resposta.texto;
							primeiro = false;

						}

					});

				});

				this.filtrarPerguntasDisponiveis();
			});

	}

};

</script>

<style lang="less">

.v-input--is-disabled{
	pointer-events: auto !important;

	.v-input__slot{
		cursor: not-allowed !important;
	}
}

.v-autocomplete:not(.v-input--is-focused).v-select--chips input {
	max-height: 100% !important;
}

.theme--light.v-list-item .v-list-item__mask{
	color:white;
	background: #65afef;
}
</style>