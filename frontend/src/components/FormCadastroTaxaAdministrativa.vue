<template lang='pug'>

	v-form(ref="taxaAdministrativa")
		v-container.pa-0
			v-row
				v-col(cols="12", md="2")
					v-label Ano
					v-select#QA-select-taxa-administrativa-ano(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						item-color="grey darken-3",
						v-model="taxaAdministrativa.ano",
						:items="anos",
						:error-messages="errorMessage(taxaAdministrativa.ano)",
						@click.native="resetErrorMessage",
						required,
					)
				v-col.mr-9(cols="12", md="2")
					v-label Valor
					v-text-field#QA-input-taxa-licenciamento-valor(
						v-money="money"
						outlined,
						color="#E0E0E0",
						v-model="taxaAdministrativa.valor",
						@click.native="resetErrorMessage",
						:error-messages="errorMessage( taxaAdministrativa.valor, false )",
						required,
						dense
					)
				v-col(cols="12", md="7")
					v-label Opções para cobrança
						i &nbsp (opcional)
					v-col.d-flex.flex-row.pa-0
						v-checkbox(v-model="taxaAdministrativa.atividadeDispensavel", label="Atividades dispensáveis", color="#84A98C")
						v-checkbox.ml-5(v-model="taxaAdministrativa.atividadeLicenciavel", label="Atividades licenciáveis", color="#84A98C")

			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-taxa-administrativa.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados
					v-btn#QA-btn-cadastrar-taxa-administrativa(@click="submit", large, color="#84A98C")
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}
  
</template>

<script>

import { VMoney } from 'v-money';

export default {
	
	name: 'FormCadastroTaxaAdministrativa',

	directives: {money: VMoney},

	data: () => {
		return {
			placeholder: "Selecione o ano",
			anos: [],
			money: {
				decimal: ',',
				thousands: '.',
				prefix: 'R$ ',
				precision: 2,
				masked: false
			}
		};
	},

	props: {
		taxaAdministrativa: {
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
		labelBotaoCadastrarEditar: {
			type: [String]
		},
		iconBotaoCadastrarEditar: {
			type: [String]
		}
	},

	created() {

		let anoAtual = new Date().getFullYear();
		let anos = [];
		for (let i = anoAtual; i<= 2060; i++) {
			anos.push(anoAtual++);
		}

		this.anos = anos;

	}

};
</script>

<style lang='less'>

@import "../assets/css/variaveis.less";

.div-valor {
	background-color: @bg-header;
	border: none;
	border-radius: 0 4px 4px 0;
	color: @icon-color;
	cursor: default;
	display: flex;
	height: 40px;
	width: 50px;
}

.div-money {
	border: 1px solid @border-components;
	height: 40px;
}

.v-input--selection-controls {
	margin-top: 4px !important;
}

</style>