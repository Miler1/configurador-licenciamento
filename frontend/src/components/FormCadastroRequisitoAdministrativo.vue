<template lang="pug">

	v-form(ref="requisitoAdministrativo")
		v-container.pa-0
			v-row
				v-col(cols="12", md="9")
					v-label Documento
					v-autocomplete#QA-select-requisito-administrativo-documento(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholderSelect",
						item-color="grey darken-3",
						v-model="requisitoAdministrativo.documento",
						:items="documentos",
						item-text="nome",
						:error-messages="errorMessage( requisitoAdministrativo.documento )",
						no-data-text="Nenhum documento encontrado",
						@click.native="resetErrorMessage",
						required,
						return-object=true
					)
				v-col(cols="12", md="3")
					v-label Tipo Licença
					v-autocomplete#QA-select-requisito-administrativo-licenca(
						outlined,
						:disabled="!cadastro",
						dense,
						color="#E0E0E0",
						:placeholder="placeholderSelect",
						item-color="grey darken-3",
						v-model="requisitoAdministrativo.licencas",
						:items="licencas",
						item-text="sigla",
						:error-messages="errorMessage(requisitoAdministrativo.licencas)",
						no-data-text="Nenhum tipo de licença encontrado",
						@click.native="resetErrorMessage",
						required,
						return-object=true,
						multiple=true
						chips=true,
						deletable-chips=true
					)
			v-row
				v-col.d-flex.flex-column.mr-5(cols="12", md="4")
					v-col.pa-0
						v-label Pessoa
					v-col.pa-0
						v-btn-toggle#QA-btn-toggle-pessoa(
								v-model="requisitoAdministrativo.tipoPessoa",
								tile, 
								color="green lighten-4",
							)
							v-btn#QA-btn-requisito-administrativo-pessoa-fisica(
								value="PF",
								width="140px",
							) 
								span Física
							v-btn#QA-btn-requisito-administrativo-pessoa-juridica(
								value="PJ",
								width="140px",
							) 
								span Jurídica

					v-col.pa-0
						span.v-messages.theme--light.error--text.v-messages__message {{ !this.requisitoAdministrativo.tipoPessoa && this.errorMessage().length ? this.errorMessage() : "" }}

				v-col.d-flex.flex-column(cols="12", md="4")
					v-col.pa-0
						v-label Tipo do requisito
					v-col.pa-0
						v-btn-toggle#QA-btn-toggle-requisito(
								v-model="requisitoAdministrativo.obrigatorio",
								tile,
								color="green lighten-4", 
							)
							v-btn#QA-btn-requisito-administrativo-basico(
								value=true,
								width="140px",
							) 
								span Básico
							v-btn#QA-btn-requisito-administrativo-complementar(
								value=false,
								width="140px",
							) 
								span Complementar

					v-col.pa-0
						span.v-messages.theme--light.error--text.v-messages__message {{ this.requisitoAdministrativo.obrigatorio === null && this.errorMessage().length ? this.errorMessage() : "" }}

			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-requisito-administrativo.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados
					v-btn#QA-btn-cadastrar-requisito-administrativo(@click="submit", large)
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span {{labelBotaoCadastrarEditar}}
</template>

<script>

import DocumentoService from '@/services/documento.service';
import LicencaService from '@/services/licenca.service';

export default {

	name: "FormCadastroRequisitoAdministrativo",

	data: () => {
		return {
			placeholderSelect: "Selecione",
			documentos: [],
			licencas: []
		};
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

	props: {
		requisitoAdministrativo: {
			type: [Object]
		},
		submit: {
			type: [Function]
		},
		clear: {
			type: [Function]
		},
		resetErrorMessage:{
			type: [Function]
		},		
		errorMessage: {
			type: [Function]
		},
		errorMessageEmpty: {
			type: [Boolean]
		},
		labelBotaoCadastrarEditar: {
			type: [String]
		},
		iconBotaoCadastrarEditar: {
			type: [String]
		},
		cadastro: {
			type: [Boolean]
		}
	}

};

</script>

<style lang="less" scoped>

@import "../assets/css/variaveis.less";

.theme--light.v-btn-toggle:not(.v-btn-toggle--group) {

	.v-btn--active {
		border-color: @green-primary !important;

		span {
			color: @green-primary !important;
		}
	}
}

.v-btn-toggle > .v-btn.v-btn:not(:first-child) {
	
	border-left-width: 1px !important;
}

.theme--light.v-list-item .v-list-item__mask{

	color:white;
	background: #65afef;;
}

</style>