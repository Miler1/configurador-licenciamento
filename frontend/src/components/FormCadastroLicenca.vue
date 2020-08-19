<template lang="pug">

	v-form(ref="licenca")
		v-container.pa-0
			v-row
				v-col(cols="12", md="3")
					v-label Tipo
					v-text-field#QA-input-licenca-tipo(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						v-model="licenca.sigla",
						:error-messages="errorMessage(licenca.sigla)",
						@click.native="resetErrorMessage",
						@input="v => {licenca.sigla = v.toUpperCase()}",
						required,
					)
				v-col(cols="12", md="9")
					v-label Nomenclatura
					v-text-field#QA-input-licenca-nomenclatura(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						v-model="licenca.nome",
						:error-messages="errorMessage(licenca.nome)",
						@click.native="resetErrorMessage",
						required,
					)
			v-row.flex-row.align-center.justify-start
				v-col(cols="12", md="3")
					label Finalidade
						v-select#QA-select-licenca-finalidade(
							outlined,
							dense,
							color="#E0E0E0",
							:placeholder="placeholderSelect",
							item-color="grey darken-3",
							v-model="licenca.finalidade",
							:items="finalidades",
							item-text="text",
							item-value="value",
							:error-messages="errorMessage(licenca.finalidade)",
							@click.native="resetErrorMessage",
							@change="upValidade",
							required,
						)
				v-col.d-flex.flex-column.justify-start.pl-0(cols="12", md="9")
					v-col.pb-0(cols="12", md="8")
						label Prazo de validade
					v-col.d-flex.flex-row.justify-start.pt-0(cols="12", md="12")
						div.d-flex.flex-row
							v-text-field#QA-input-licenca-validade(
								outlined,
								dense,
								color="#E0E0E0",
								type="number",
								min="0",
								step="1",
								v-model="licenca.validadeEmAnos",
								:error-messages="validadeErrorMessage(licenca.validadeEmAnos)",
								:disabled="validadeDisabled()",
								@click.native="resetErrorMessage",
							)
							div#div-meses
								span Anos
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-licenca.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados

					v-btn#QA-btn-cadastrar-licenca(@click="submit")
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span {{labelBotaoCadastrarEditar}}

</template>

<script>

import FinalidadeEnum from '../utils/enums/finalidadeEnum';

export default {

	name: "FormCadastroLicenca",

	data: () => {
		return {
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			finalidades: FinalidadeEnum,
		};
	},

	props: {
		licenca: {
			type: [Object]
		},
		submit: {
			type: [Function]
		},
		clear: {
			type: [Function]
		},
		resetErrorMessage: {
			type: [Function]
		},
		errorMessage: {
			type: [Function]
		},
		validadeErrorMessage: {
			type: [Function]
		},
		labelBotaoCadastrarEditar: {
			type: [String]
		},
		iconBotaoCadastrarEditar: {
			type: [String]
		}
	},

	methods: {

		validadeDisabled() {
			return !this.licenca.finalidade || this.licenca.finalidade === 'CADASTRO';
		},

		upValidade() {
			if (this.licenca.finalidade === 'CADASTRO') {
				this.licenca.validadeEmAnos = null;
			}
		},

	},

};

</script>

<style lang="less">

@import "../assets/css/variaveis.less";

#div-meses {
	background-color: @bg-header;
	border: none;
	border-radius: 0 4px 4px 0;
	color: @icon-color;
	cursor: default;
	display: flex;
	height: 40px;

	span {
		align-self: center;
		padding: 1px 8px;
	}
}

#QA-input-licenca-validade:disabled {

	background-color: @bg-header;

}

</style>