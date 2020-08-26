<template lang="pug">

	v-form(ref="requisitoAdministrativo")
		v-container.pa-0
			v-row

				v-col(cols="9", md="8")
					v-label Documento
					v-autocomplete#QA-select-licenca-finalidade(
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

				v-col(cols="9", md="3")
					v-label Tipo Licença
					v-autocomplete#QA-select-licenca-finalidade(
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
				v-col(cols="9", md="5")
					v-label Pessoa
					br
					input(type="radio" value="PF" v-model="requisitoAdministrativo.tipoPessoa")
					| &nbsp;Física

					input.radio-inline(type="radio" value="PJ" v-model="requisitoAdministrativo.tipoPessoa")
					| &nbsp;Jurídica

				v-col(cols="9", md="3")
					v-label Tipo do requisito
					br
					input(type="radio" value="true" v-model="requisitoAdministrativo.obrigatorio")
					| &nbsp;Básico
					
					input.ml-3(type="radio" value="false" v-model="requisitoAdministrativo.obrigatorio")
					| &nbsp;Complementar
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-cnae.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados

					v-btn#QA-btn-cadastrar-cnae(@click="submit", large)
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
