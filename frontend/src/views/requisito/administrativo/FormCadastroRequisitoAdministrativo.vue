<template lang="pug">

	v-form(ref="requisitoAdministrativo")
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
					:filter="filtroSelect",
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
				ToggleOptions(
					ref="toggleOptionsPessoa",
					labelOption="Pessoa",
					idToggle="QA-btn-toggle-pessoa",
					:errorMessage="errorMessage",
					:options="optionsPessoa",
					@changeOption="requisitoAdministrativo.tipoPessoa = $event"
				)

			v-col.d-flex.flex-column(cols="12", md="4")
				ToggleOptions(
					ref="toggleOptionsRequisitoAdm",
					labelOption="Tipo do requisito",
					idToggle="QA-btn-toggle-requisito-administrativo",
					:errorMessage="errorMessage",
					:options="optionsRequisitoAdm",
					@changeOption="requisitoAdministrativo.obrigatorio = $event"
				)
		v-row
			v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
				a#QA-limpar-dados-requisito-administrativo.d-flex.flex-row.align-center.justify-end(@click="clear")
					v-icon.pr-1 fa-eraser
					span Limpar dados
				v-btn#QA-btn-cadastrar-requisito-administrativo(@click="submit", large, color="#84A98C")
					v-icon(color="white") {{iconBotaoCadastrarEditar}}
					span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}

</template>

<script>

import DocumentoService from '@/services/documento.service';
import LicencaService from '@/services/licenca.service';
import ToggleOptions from "@/components/ToggleOptions";

export default {

	name: "FormCadastroRequisitoAdministrativo",

	components: {
		ToggleOptions
	},

	data: () => {
		return {
			placeholderSelect: "Selecione",
			placeholderSelectLicenca: "Selecione um ou mais",
			documentos: [],
			licencas: [],
			optionsPessoa:[
				{
					idOption: "QA-btn-requisito-administrativo-pessoa-fisica",
					value: "PF",
					label: "Física",
					width: "140px"
				},
				{
					idOption: "QA-btn-requisito-administrativo-pessoa-juridica",
					value: "PJ",
					label: "Jurídica",
					width: "140px"
				}
			],
			optionsRequisitoAdm:[
				{
					idOption: "QA-btn-requisito-administrativo-basico",
					value: "true",
					label: "Básico",
					width: "140px"
				},
				{
					idOption: "QA-btn-requisito-administrativo-complementar",
					value: "false",
					label: "Complementar",
					width: "140px"
				}
			]
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

.theme--light.v-list-item .v-list-item__mask{
	color:white;
	background: #65afef;
}

.v-autocomplete:not(.v-input--is-focused).v-select--chips input {
	max-height: 100% !important;
}

</style>