<template lang="pug">

#tela-cadastro-requisito-tecnico
	v-container
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Grupo de requisito técnico
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
						span.align-baseline Requisito técnico para solicitação de licenciamento
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="requisitolist")
						v-container.pa-0
							v-row
								v-col(cols="12", md="9")
									v-label Documento
									v-autocomplete#QA-select-requisito-tecnico-documento(
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
								v-col(cols="12", md="3")
									v-label Tipos de licenças
									v-autocomplete#QA-select-requisito-tecnico-licenca(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelectLicenca",
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
							v-row
								v-col.d-flex.flex-column(cols="12", md="5")
									v-col.pa-0
										v-label Tipo do requisito
									v-col.pa-0.mb-1
										v-btn-toggle#QA-btn-toggle-requisito-tecnico(
												v-model="grupoRequisito.obrigatorio",
												color="green lighten-4", 
											)
											v-btn#QA-btn-requisito-tecnico-basico(
												color="white",
												value="true",
												width="140px",
											) 
												span Básico
											v-btn#QA-btn-requisito-tecnico-complementar(
												color="white",
												value="false",
												width="140px",
											) 
												span Complementar
									v-col.d-flex.pa-0
										span.v-messages.theme--light.error--text.v-messages__message.pl-3.mb-3 
											| {{ errorMessage(grupoRequisito.obrigatorio, true) }}
							v-row
								v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
									a#QA-limpar-dados-requisito-tecnico.d-flex.flex-row.align-center.justify-end(@click="clearRequisito")
										v-icon mdi-delete
										span Limpar dados
								
									v-btn#QA-btn-adicionar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
										v-icon mdi-plus
										span Adicionar
								
									v-btn#QA-btn-editar-requisito-tecnico.btn-cadastrar(@click="incluirDados", large, v-if="!isInclusao")
										v-icon(color="white") mdi-pencil
										span Editar

		GridListagemInclusao.px-7(
			:tituloListagem="tituloListagem",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:editarItem="editarItem",
			:excluirItem="excluirItem"
		)

		v-row.pt-6.px-7
			v-col#form-actions.d-flex.justify-space-between(cols="12", md="12")
				v-btn#QA-btn-cancelar-requisito-tecnico(@click="cancelar", outlined, large, color="#84A98C")
					v-icon mdi-close
					span Cancelar

				v-btn#QA-btn-cadastrar-requisito-tecnico.btn-cadastrar(@click="submit", large)
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
			placeholderSelectLicenca: "Selecione um ou mais",
			documentos: [],
			licencas: [],
			errorMessageEmpty: true,
			errorMessageEmptyInclusao: true,
			tituloListagem: "Listagem de documentos adicionados para este grupo",
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

				if (!this.isInclusao && Array.isArray(value)) {
					return 'Este campo não permite ser editado';
				}

				else if(!this.errorMessageEmptyInclusao && !value) {
					return 'Obrigatório';
				}

				return this.errorMessageEmpty || value || (this.dadosListagem.length > 0) ? '' : 'Obrigatório';
			}

			return this.errorMessageEmpty || value ? '' : 'Obrigatório';

		},

		clear() {

			this.requisitoTecnico.codigo = null;
			this.requisitoTecnico.descricao = null;
			this.requisitoTecnico.ativo = true;

			this.clearRequisito();

		},

		clearRequisito() {

			this.grupoRequisito.licencas = null;
			this.grupoRequisito.documento = null;
			this.grupoRequisito.obrigatorio = null;
			this.isInclusao = true;
			this.resetErrorMessage();
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
			}

		},

		cadastrar() {

			RequisitoTecnicoService.cadastrar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess();
				})
				.catch(error => {
					this.handleError(error);
				});

		},

		editar() {

			RequisitoTecnicoService.editar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess(true);
				})
				.catch(error => {
					this.handleError(error, true);
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

			this.clear();
			this.redirectListagem();

		},

		redirectListagem() {
			this.$router.push({name: 'RequisitosTecnicos'});
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

			this.$fire({

				title: '<p class="title-modal-confirm">Confirmar cancelamento </p>',

				html: this.isCadastro ?
					`<p class="message-modal-confirm">Ao cancelar o cadastro, todas as informações serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar o cadastro? Esta opção não poderá ser desfeita e todas as informações serão perdidas.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao cancelar a edição, todas as informações alteradas serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar o cadastro? Esta opção não poderá ser desfeita e todas as informações serão perdidas.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor: '#67C23A',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: '<i class="fa fa-check-circle"></i> Confirmar',
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {

				if (result.value) {
					this.$router.push({name: 'RequisitosTecnicos'});
				}
				
			}).catch((error) => {
				console.error(error);
			});

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

			this.$fire({
				title:'<p class="title-modal-confirm">Remover documento - ' + item.documento.nome + '</p>',

				html:`<p class="message-modal-confirm">Ao remover o documento, ele não estará mais vinculado nesse grupo.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover o documento? Esta opção pode ser desfeita a qualquer momento ao adicioná-lo novamente.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor:'#EB2D30',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: '<i class="fa fa-minus-circle"></i> Remover' ,
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {

				if(result.value) {	

					var list = [];

					this.dadosListagem = this.dadosListagem.filter(
						dado => dado.documento.nome != item.documento.nome || dado.licenca.sigla != item.licenca.sigla
					);
				}

			});		
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
		text-transform: none !important;
	}

	.v-icon {
		font-size: 20px !important;
	}

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
		font-size: 16px;
		text-transform: none !important;
	}

	.v-icon {
		font-size: 20px !important;
	}

	.btn-cadastrar {
		background-color: @green-primary !important;
		color: @bg-text-field !important;
	}

}

.theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn.v-btn--active {
	border-color: @green-primary !important;
	border-left-width: 1px !important;

	span {
		color: @green-primary !important;
	}
}

.v-input--is-disabled{
	pointer-events: auto !important;

	.v-input__slot{
		cursor: not-allowed !important;
	}
}

.v-autocomplete:not(.v-input--is-focused).v-select--chips input {
	max-height: 100% !important;
}

</style>