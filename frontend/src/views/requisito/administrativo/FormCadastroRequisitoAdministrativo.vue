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
						:error-messages="errorMessage(requisitoAdministrativo.documento)",
						no-data-text="Nenhum documento encontrado",
						@click.native="resetErrorMessage",
						required,
						return-object=true
					)
				v-col(cols="12", md="3")
					v-label Tipos de licenças
					v-autocomplete#QA-select-requisito-administrativo-licenca(
						outlined,
						:disabled="!cadastro",
						dense,
						color="#E0E0E0",
						:placeholder="placeholderSelectLicenca",
						item-color="#84A98C",
						v-model="requisitoAdministrativo.licencas",
						:items="licencas",
						item-text="sigla",
						:error-messages="errorMessage(validarArray(requisitoAdministrativo.licencas))",
						no-data-text="Nenhum tipo de licença encontrado",
						@click.native="resetErrorMessage",
						required,
						return-object=true,
						multiple=true,
						chips=true,
						deletable-chips=true
					)
			v-row
				v-col.d-flex.flex-column(cols="12", md="4")
					v-col.pa-0
						v-label Pessoa
					v-col.pa-0.mb-1
						v-btn-toggle#QA-btn-toggle-pessoa(
								v-model="requisitoAdministrativo.tipoPessoa",
								color="green lighten-4",
							)
							v-btn#QA-btn-requisito-administrativo-pessoa-fisica(
								color="white",
								value="PF",
								width="140px",
							)
								span Física
							v-btn#QA-btn-requisito-administrativo-pessoa-juridica(
								color="white",
								value="PJ",
								width="140px",
							)
								span Jurídica
					v-col.d-flex.pa-0
						span.v-messages.theme--light.error--text.v-messages__message.pl-3.mb-3
							| {{ errorMessage(requisitoAdministrativo.tipoPessoa) }}

				v-col.d-flex.flex-column(cols="12", md="4")
					v-col.pa-0
						v-label Tipo do requisito
					v-col.pa-0.mb-1
						v-btn-toggle#QA-btn-toggle-requisito-administrativo(
								v-model="requisitoAdministrativo.obrigatorio",
								color="green lighten-4",
							)
							v-btn#QA-btn-requisito-administrativo-basico(
								color="white",
								value="true",
								width="140px",
							)
								span Básico
							v-btn#QA-btn-requisito-administrativo-complementar(
								color="white",
								value="false",
								width="140px",
							)
								span Complementar
					v-col.d-flex.pa-0
						span.v-messages.theme--light.error--text.v-messages__message.pl-3.mb-3
							| {{ errorMessage(requisitoAdministrativo.obrigatorio) }}

			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-requisito-administrativo.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados
					v-btn#QA-btn-cadastrar-requisito-administrativo(@click="submit", large, color="#84A98C")
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}
</template>

<script>

import DocumentoService from '@/services/documento.service';
import LicencaService from '@/services/licenca.service';

export default {

	name: "FormCadastroRequisitoAdministrativo",

	data: () => {
		return {
			placeholderSelect: "Selecione",
			placeholderSelectLicenca: "Selecione um ou mais",
			documentos: [],
			licencas: []
		};
	},

	created() {

		DocumentoService.buscarDocumentosAtivos()
			.then((response) => {
				this.documentos = response.data;
			});

		LicencaService.findAll()
			.then((response) => {
				this.licencas = response.data;
			});

	},

	methods: {

		validarArray(array) {

			if(!this.cadastro)
				return array;

			return Array.isArray(array) && array.length > 0;
		}

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

<style lang="less">

@import "../../../assets/css/variaveis.less";

.theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn.v-btn--active {
	border-color: @green-primary !important;
	border-left-width: 1px !important;

	span {
		color: @green-primary !important;
	}
}

.theme--light.v-list-item .v-list-item__mask{
	color:white;
	background: #65afef;
}

.v-autocomplete:not(.v-input--is-focused).v-select--chips input {
	max-height: 100% !important;
}

</style>