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
				v-col(cols="12", md="3")
					v-label Valor
					v-col.d-flex.flex-row.justify-start.pa-0(cols="12", md="12")
						v-col.d-flex.flex-row.pa-0(width="100%")
							div.d-flex.justify-center.align-center#div-valor
								span R$
							money
								v-money#QA-input-taxa-administrativa-valor(
									outlined,
									dense,
									color="#E0E0E0",
									width="140px",
									placeholder="Informe o valor da taxa",
									v-model="taxaAdministrativa.valor",
									:money,
									:error-messages="errorMessage(taxaAdministrativa.valor)",
									@click.native="resetErrorMessage",
									required
								)
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

import {Money} from 'v-money'

export default {
	
	name: 'FormCadastroTaxaAdministrativa',

	data: () => {
		return {
			placeholder: "Selecione o ano",
			anos: getYear(),
			 money: {
				decimal: ',',
				thousands: '.',
				prefix: 'R$ ',
				suffix: ' #',
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

	methods: {

		getYear(){
			var currentYear = new Date().getFullYear();
			let years = [];
			var startYear = 1980;
			for(var i=startYear; i<= currentYear; i++){
				years.push(startYear++);
			}

			return years;
		}
	}
};
</script>

<style lang='less'>

@import "../assets/css/variaveis.less";

#div-valor {
	background-color: @bg-header;
	border: none;
	border-radius: 0 4px 4px 0;
	color: @icon-color;
	cursor: default;
	display: flex;
	height: 40px;
	width: 50px;
}

</style>