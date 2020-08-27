<template lang="pug">

#tela-cadastro-requisito-tecnico
	v-container
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Requisito
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="requisito")
						v-container.pa-0
							v-row
								v-col(cols="12", md="3")
									v-label Código
									v-text-field#QA-input-requisito-tecnico-codigo(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="requisitoTecnico.codigo",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( requisitoTecnico.codigo, false )",
										required,
										dense
									)
								v-col(cols="12", md="9")
									v-label Descrição
									v-text-field#QA-input-requisito-tecnico-descricao(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="requisitoTecnico.descricao",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( requisitoTecnico.descricao, false )",
										required,
										dense
									)

			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Requisitos Técnicos para Solicitação de Licenciamento
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="requisitolist")
						v-container.pa-0
							v-row
								v-col(cols="9", md="6")
									v-label Documento
									v-autocomplete#QA-select-licenca-finalidade(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="grupoRequisito.documento",
										:items="documentos",
										item-text="nome",
										:error-messages="errorMessage( grupoRequisito.documento, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true
									)

								v-col(cols="9", md="3")
									v-label Tipo Licença
									v-autocomplete#QA-select-licenca-finalidade(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="grupoRequisito.licencas",
										:items="licencas",
										item-text="sigla",
										:error-messages="errorMessage( grupoRequisito.licencas, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true,
										multiple=true
										chips=true,
										deletable-chips=true
										:disabled="!isInclusao"
									)
								
								v-col(cols="9", md="2")
									v-label Tipo do requisito
									br
									input.mt-3(type="radio" value="true" v-model="grupoRequisito.obrigatorio")
									| &nbsp;Básico
									
									input.mt-3.ml-3(type="radio" value="false" v-model="grupoRequisito.obrigatorio")
									| &nbsp;Complementar
							
							v-row
								v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
									a#QA-limpar-dados-requisito.d-flex.flex-row.align-center.justify-end(@click="clear")
										v-icon mdi-delete
										span Limpar dados
								
									v-btn#QA-btn-cadastrar-requisito.btn-cadastrar(@click="incluirDados", large)
										v-icon(color="white") mdi-plus
										span Incluir

		GridListagemInclusao.px-7(
			:tituloListagem="tituloListagem",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:editarItem="editarItem",
			:excluirItem="excluirItem"
		)

		v-row.px-7
			v-col.align-center(cols="12", md="12")
				v-btn#QA-btn-cadastrar-requisito(@click="cancelar", outlined, large, color="red")
					v-icon mdi-close
					span Cancelar

				v-btn#QA-btn-cadastrar-requisito.btn-cadastrar.float-right(@click="submit", large)
					v-icon(color="white") {{iconBotaoCadastrarEditar}}
					span {{labelBotaoCadastrarEditar}}

</template>

<script>

import DocumentoService from '@/services/documento.service';
import LicencaService from '@/services/licenca.service';
import RequisitoTecnicoService from '../services/requisitoTecnico.service';
import GridListagemInclusao from '@/components/GridListagemInclusao';
import { HEADER } from '@/utils/dadosHeader/ListagemRequisitoTecnicoInclusao';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { SET_SNACKBAR } from '@/store/actions.type';

export default {

	name:'TelaCadastroRequisitoTecnico',

	components: {
		GridListagemInclusao
	},
	
	data: () => {
		return {
			dadosPanel: {
				panel: [0, 1],
				readonly: true,
			},
			requisitoTecnico: {
				codigo: null,
				descricao: null,
				ativo: true
			},
			grupoRequisito: {
				documento: null,
				licencas: null,
				obrigatorio: null
			},
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			documentos: [],
			licencas: [],
			errorMessageEmpty: true,
			errorMessageEmptyInclusao: true,
			tituloListagem: "Listagem de dados vinculados",
			headerListagem: HEADER,
			dadosListagem: [],
			labelBotaoCadastrarEditar: 'Cadastrar',
			iconBotaoCadastrarEditar: 'mdi-plus',
			isCadastro: true,
			isInclusao: true,
			indexItemEdicao: null
		};
	},

	methods: {

		resetErrorMessage() {
			this.errorMessageEmpty = true;
			this.errorMessageEmptyInclusao = true;
		},

		errorMessage(value, isVinculacao) {
			if(isVinculacao) {
				return this.errorMessageEmptyInclusao || value || (this.dadosListagem.length > 0) ? [] : 'Obrigatório';
			}
			return this.errorMessageEmpty || value ? [] : 'Obrigatório';
		},

		clearRequisito() {

			this.requisitoTecnico.codigo = null;
			this.requisitoTecnico.descricao = null;
			this.requisitoTecnico.ativo = true;

			this.clear();
		},

		clear() {

			if(this.isInclusao){
				this.grupoRequisito.licencas = null;
			}

			this.grupoRequisito.documento = null;
			this.grupoRequisito.obrigatorio = null;

		},

		incluirDados() {

			if(this.checkFormVinculacao()){

				

				var dadoListagem = {};

				if(this.isInclusao) {

					this.grupoRequisito.licencas.forEach(licenca => {

						dadoListagem.documento = this.grupoRequisito.documento;
						dadoListagem.licenca = licenca;
						dadoListagem.obrigatorio = this.grupoRequisito.obrigatorio;

						this.dadosListagem.push(dadoListagem);
						dadoListagem = {};
				
					});

				} else {

					dadoListagem.documento = this.grupoRequisito.documento;
					dadoListagem.licenca = this.grupoRequisito.licencas[0];
					dadoListagem.obrigatorio = this.grupoRequisito.obrigatorio;

					this.dadosListagem.splice(this.indexItemEdicao, 1, dadoListagem);
					dadoListagem = {};
					this.indexItemEdicao = null;
					this.isInclusao = true;

				}

				this.clear();

			} else {
				this.errorMessageEmptyInclusao = false;
			}

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
				this.errorMessageEmptyInclusao = false;
			}

		},

		cadastrar() {

			RequisitoTecnicoService.cadastrar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess();
				})
				.catch(erro => {
					this.handleError(erro);
				});

		},

		editar() {

			RequisitoTecnicoService.editar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess(true);
				})
				.catch(erro => {
					this.handleError(erro, true);
				});

		},

		preparaPraSalvar() {
			
			this.requisitoTecnico.listRequisitos = [];
			var dadoListagem = {};

			this.dadosListagem.forEach(dado => {

				dadoListagem.idTipoDocumento = dado.documento.id;
				dadoListagem.idTipoLicenca = dado.licenca.id;
				dadoListagem.obrigatorio = dado.obrigatorio == 'true' ? true : false;

				this.requisitoTecnico.listRequisitos.push(dadoListagem);
				dadoListagem = {};

			});

			return this.requisitoTecnico;
		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.requisitoTecnico.editar : ERROR_MESSAGES.requisitoTecnico.cadastro;
			message += error.message;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'error', text: message, timeout: '9000'}
			);
		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			this.$store.dispatch(SET_SNACKBAR,
				{color: 'success', text: message, timeout: '6000'}
			);

			this.clearRequisito();
			this.cancelar();

		},

		checkForm() {

			return this.requisitoTecnico.codigo
				&& this.requisitoTecnico.codigo != ''
				&& this.requisitoTecnico.descricao
				&& this.requisitoTecnico.descricao != ''
				&& this.dadosListagem
				&& this.dadosListagem.length > 0;

		},

		checkFormVinculacao() {
			return this.grupoRequisito.documento
				&& this.grupoRequisito.documento != ''
				&& this.grupoRequisito.licencas
				&& this.grupoRequisito.licencas.length > 0
				&& this.grupoRequisito.obrigatorio != null;
		},

		cancelar() {
			this.$router.push({name: 'RequisitosTecnicos'});
		},

		editarItem(item) {

			window.scrollTo(0,0);
			this.grupoRequisito.documento = item.documento;
			this.grupoRequisito.licencas = [];
			this.grupoRequisito.licencas.push(item.licenca);
			this.grupoRequisito.obrigatorio = item.obrigatorio;

			this.indexItemEdicao = this.dadosListagem.indexOf(item);
			this.isInclusao = false;

		},

		excluirItem(item) {
			var list = [];
			this.dadosListagem = this.dadosListagem.filter(
				dado => dado.documento.nome != item.documento.nome || dado.licenca.sigla != item.licenca.sigla
			);
		},

		preparaDadosParaEdicao(requisito) {

			this.requisitoTecnico.codigo = requisito.codigo;
			this.requisitoTecnico.descricao = requisito.descricao;
			this.requisitoTecnico.ativo = requisito.ativo;
			this.requisitoTecnico.id = this.$route.params.idRequisito;
			
			this.dadosListagem = [];
			this.dadosListagem = requisito.tipoLicencaGrupoDocumentoList;

		}
	},

	created(){

		DocumentoService.findAll()
			.then((response) => {
				this.documentos = response.data;
			});

		LicencaService.findAll()
			.then((response) => {
				this.licencas = response.data;
			});

	},

	mounted() {

		if(this.$route.params.idRequisito) {
			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.isCadastro = false;

			RequisitoTecnicoService.findById(this.$route.params.idRequisito)
				.then((response) => {
					this.preparaDadosParaEdicao(response.data);
				})
				.catch((error) => {
					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: error.message, timeout: '9000'}
					);
				});
		}
	}

};
</script>

<style lang="less">

@import "../assets/css/variaveis.less";

	.v-expansion-panel-header {
		background-color: @bg-header;
		color: @text-color;
		cursor: default;
		font-size: 21px;
		height: 70px;
		padding: 0 20px;

		.v-btn {
			font-size: 16px;
			padding: 20px !important;
			text-transform: none !important;
		}

		.v-icon {
			font-size: 20px !important;
		}
	}

	.btn-cadastrar {
		background-color: @green-primary !important;
		color: @bg-text-field !important;
	}

	.v-label {
		color: @text-color !important;
	}

	.v-text-field, .v-checkbox {

		fieldset {
			border: 1px solid @border-components;
			border-radius: 2px;
		}
	}

	#form-actions {
		padding: 0 12px;

		a {
			margin-right: 20px;

			.v-icon, span {
				color: @red;
			}
		}

		.v-btn {
			background-color: @green-primary !important;
			color: @bg-text-field !important;
			font-size: 16px;
			height: auto;
			padding: 12px 20px !important;
			text-transform: none !important;
		}

		.v-icon {
			font-size: 20px !important;
		}
	}

</style>