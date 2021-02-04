<template lang="pug">

	v-expansion-panels(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel(v-for="(item, i) in dadosPanel.items", :key="i")
			v-expansion-panel-header.expansion-header
				div.d-flex.flex-row.align-center.justify-start
					v-icon.pr-3(color="#84A98C") {{dadosPanel.iconName}}
					span.align-baseline {{dadosPanel.title}}
				
				template(v-slot:actions)
					v-btn#QA-btn-expand_cadastrar(@click="abrirPanel", v-if="dadosPanel.panel.length === 0", large, color="#84A98C")
						v-icon(color="white") mdi-plus
						span Cadastrar	
					v-tooltip(bottom, v-if="dadosPanel.panel.length > 0")
						template(v-slot:activator="{ on, attrs }")
							v-btn#QA-btn-fechar_cadastro(
								icon,
								v-on="on",
								@click="fecharPanel"
							)
								v-icon.pa-3(color="white") mdi-close
						span Cancelar {{dadosPanel.tipo}}
			v-expansion-panel-content
				slot

</template>

<script>

export default {

	name: "PanelCadastro",

	methods: {

		abrirPanel() {
			this.dadosPanel.panel = [0];
		},

		fecharPanel() {
			this.dadosPanel.panel = [];
			this.clear();
		}
	},

	props: {
		clear:  {
			type: [Function]
		},
		dadosPanel: {
			type: [Object]
		},
	}
};
</script>

<style lang="less">

@import "../assets/css/variaveis.less";

.expansion-header {
	background-color: @bg-header;
	color: @text-color;
	cursor: default;
	font-size: 18px !important;
	height: 70px;
	padding: 16px 24px 16px 24px;

	.v-btn {
		background-color: @green-primary;
		color: white;
		font-size: 16px;
		text-transform: none;
	}

	.v-icon {
		font-size: 20px;
	}
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

	.v-label {
		color: @text-color;
	}

	.v-btn {
		font-size: 16px;
		text-transform: none !important;
	}

	.v-icon {
		font-size: 20px !important;
	}

	.label-btn-cadastrar {
		color: white !important;
	}
}

</style>