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
					v-form(ref="atividadeCnae")
						v-container.pa-0
							v-row
								v-col(cols="12", md="3")
									v-label Código
									v-text-field#QA-input-requisito-tecnico-codigo(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="atividadeCnae",
										@click.native="resetErrorMessage",
										required,
										dense
									)
								v-col(cols="12", md="9")
									v-label Descrição
									v-text-field#QA-input-requisito-tecnico-descricao(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="atividadeCnae",
										@click.native="resetErrorMessage",
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
					v-form(ref="atividadeCnae")
						v-container.pa-0
							v-row
								v-col(cols="12", md="3")
									v-label Código
									v-text-field#QA-input-requisito-tecnico-codigo(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="atividadeCnae",
										@click.native="resetErrorMessage",
										required,
										dense
									)
								v-col(cols="12", md="9")
									v-label Descrição
									v-text-field#QA-input-requisito-tecnico-descricao(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="atividadeCnae",
										@click.native="resetErrorMessage",
										required,
										dense
									)

</template>

<script>
export default {

	name:'TelaCadastroRequisitoTecnico',
	
	data: () => {
		return {
			dadosPanel: {
				panel: [0, 1],
				readonly: true,
			},
			placeholder: "Digite aqui...",
		};
	},

	methods: {

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
			background-color: @green-primary !important;
			color: @bg-text-field !important;
			font-size: 16px;
			padding: 20px !important;
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