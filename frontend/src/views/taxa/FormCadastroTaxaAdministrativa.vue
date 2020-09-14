<template lang='pug'>

	v-form(ref="taxaAdministrativa")
		v-container.pa-0
			v-row
				v-col(cols="12", xl="2", lg="2", md="6")
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

				v-col.min-width-215(cols="12", xl="2", lg="2", md="6")

					ToggleOptions(
						ref="toggleOptionsIsento",
						labelOption="Isento",
						idToggle="QA-btn-toggle-taxa-licenciamento-isento",
						:errorMessage="errorMessage",
						:options="optionsIsento",
						@changeOption="taxaAdministrativa.isento = $event"
					)

				v-col(cols="12", xl="3", lg="3", md="4", v-if="taxaAdministrativa.isento == 'false'")
					v-label Valor
					v-text-field#QA-input-taxa-licenciamento-valor(
						v-money="money"
						outlined,
						color="#E0E0E0",
						v-model.lazy="taxaAdministrativa.valor",
						@click.native="resetErrorMessage",
						:error-messages="errorMessage( taxaAdministrativa.valor, false )",
						required,
						dense
					)
				
				v-col.min-width-215(cols="12", xl="2", lg="2", md="4", v-if="taxaAdministrativa.isento == 'false'")
					ToggleOptions(
						ref="toggleAtividadeDispensavel",
						labelOption="Atividades dispensáveis",
						idToggle="QA-btn-toggle-atividade-dispensavel",
						:errorMessage="errorMessage",
						:options="optionsAtividadeDispensavel",
						@changeOption="taxaAdministrativa.atividadeDispensavel = $event"
					)
				
				v-col.min-width-215(cols="12", xl="2", lg="2", md="4", v-if="taxaAdministrativa.isento == 'false'")
					ToggleOptions(
						ref="toggleAtividadeLicenciavel",
						labelOption="Atividades licenciáveis",
						idToggle="QA-btn-toggle-atividade-licenciavel",
						:errorMessage="errorMessage",
						:options="optionsAtividadeLicenciavel",
						@changeOption="taxaAdministrativa.atividadeLicenciavel = $event"
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

import { VMoney } from 'v-money';
import ToggleOptions from "@/components/ToggleOptions";

export default {
	
	name: 'FormCadastroTaxaAdministrativa',

	components: {
		ToggleOptions
	},

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
			},
			optionsIsento:[
				{
					idOption: "QA-btn-isento-taxa-adm-sim",
					value: "true",
					label: "Sim"
				},
				{
					idOption: "QA-btn-isento-taxa-adm-nao",
					value: "false",
					label: "Não"
				}
			],
			optionsAtividadeDispensavel:[
				{
					idOption: "QA-btn-atividade-dispensavel-sim",
					value: "true",
					label: "Sim"
				},
				{
					idOption: "QA-btn-atividade-dispensavel-nao",
					value: "false",
					label: "Não"
				}
			],
			optionsAtividadeLicenciavel:[
				{
					idOption: "QA-btn-atividade-licenciavel-sim",
					value: "true",
					label: "Sim"
				},
				{
					idOption: "QA-btn-atividade-licenciavel-nao",
					value: "false",
					label: "Não"
				}
			],
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

.v-input--selection-controls {
	margin-top: 4px !important;
}

.min-width-215 {
	min-width: 215px;
}

</style>