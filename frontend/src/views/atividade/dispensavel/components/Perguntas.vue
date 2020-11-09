<template lang="pug">

#step-cnaes-atividade-dispensavel-pergunta

	v-expansion-panels.pb-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline {{ isInclusao ? 'Adição de ' : 'Editar ' }}  pergunta
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content
				v-form(ref="atividadeDispensavelPergunta")
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
									:filter="filtroSelect",
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
									v-icon.pr-1 fa-eraser
									span Limpar dados

								v-btn#QA-btn-adicionar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
									v-icon mdi-plus
									span Adicionar

								v-btn#QA-btn-editar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusao")
									v-icon mdi-pencil
									span Editar

	GridListagemInclusao(
		:tituloListagem="tituloListagem",
		:headers="headerListagem",
		:dadosListagem="perguntas",
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

	props: {

		perguntas: {
			type: [Array]
		},
		erro: {
			type: [Object]
		}

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
			tituloListagem: "Listagem de perguntas adicionadas",
			placeholderPesquisa: "Pesquisar pela pergunta ou respostas esperadas",
			labelNoData: 'Não existem perguntas adicionadas.',
			semResultados: 'Nenhuma pergunta encontrada com a pesquisa informada.',
			isInclusao: true,
			indexItemEdicao: null,
			allowRedirect: false
		};

	},

	methods: {

		incluirDados() {

			if (this.pergunta) {

				if (this.isInclusao) {

					this.perguntas.push(this.pergunta);
					this.clearPergunta();

				} else {

					this.perguntas.splice(this.indexItemEdicao, 1, this.pergunta);

					this.indexItemEdicao = null;
					this.clearPergunta();

				}

			} else {
				this.errorMessageEmpty = false;
			}

		},

		editarItem(item) {

			window.scrollTo(0,0);

			this.perguntasDisponiveis.push(item);

			this.pergunta = item;

			this.indexItemEdicao = this.perguntas.indexOf(item);

			this.isInclusao = false;

		},

		excluirItem(item) {

			let modal = {};

			modal.title = `Remover pergunta - ${item.texto}`;
			modal.mensagem = `Ao remover a pergunta, ela não estará mais vinculada nesse(s) CNAE(s) dispensável(eis).`;
			modal.textoConfirmacao = `Tem certeza que deseja remover a pergunta? Esta opção pode ser desfeita a qualquer momento ao adicioná-la novamente.`;

			modal.textoConfirmar = `Remover`;
			modal.textoCancelar = `Cancelar`;

			modal.acao = () => {

				let indexItemExclusao = this.perguntas.indexOf(item);
				this.perguntas.splice(indexItemExclusao, 1);
				this.filtrarPerguntasDisponiveis();

			};

			this.modalConfirmacao(modal);

		},

		errorMessage(item) {

			if (this.erro.invalido && this.pergunta) {
				return 'Adicione a pergunta primeiro';
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

					if (result.value) {
						conteudo.acao();
					}

				});

		},

		filtrarPerguntasDisponiveis() {

			let that = this;

			this.perguntasDisponiveis = this.perguntasCadastradas.filter(item => {

				let exists = false;

				that.perguntas.forEach(element => {
					exists = element.id === item.id ? true : exists;
				});

				return !exists;

			});

		},

		prepararLabelRespostasEsperadas(arrayDePerguntas) {

			arrayDePerguntas.forEach((pergunta) => {

				let primeiro = true;

				pergunta.respostasEsperadas = "";

				pergunta.respostas.forEach((resposta , index) => {

					if (resposta.permiteLicenciamento){

						if (!primeiro) {
							pergunta.respostasEsperadas += " / ";
						}

						pergunta.respostasEsperadas += resposta.texto;
						primeiro = false;

					}

				});

			});
		},

		filtroSelect(item, query, itemText) {

			query = this.normalizer(query);
			itemText = itemText ? this.normalizer(itemText) : itemText;

			return itemText.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) > -1;

		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		}

	},

	created() {

		perguntaService.listarPerguntasAtivas()
			.then(resposta => {

				this.perguntasCadastradas = resposta.data;

				this.prepararLabelRespostasEsperadas(this.perguntasCadastradas);
				this.filtrarPerguntasDisponiveis();

			});

	},

	mounted() {

		if (this.$route.params.idAtividadeDispensavel) {

			this.prepararLabelRespostasEsperadas(this.perguntas);
			this.isCadastro = false;

		}

	},

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