<template lang="pug">

	v-expansion-panels(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel(v-for="(item, i) in dadosPanel.items", :key="i")
			v-expansion-panel-header
				| {{title}}
				template(v-slot:actions)
					v-btn#QA-btn-expand_cadastrar(@click="abrirPanel", v-if="dadosPanel.panel.length === 0")
						v-icon(color="white") mdi-plus
						span Cadastrar
					v-btn#QA-btn-fechar_cadastro(
						icon,
						@click="fecharPanel",
						v-if="dadosPanel.panel.length > 0"
					)
						v-icon.pa-3(color="white") mdi-close
			v-expansion-panel-content
				slot

</template>

<script>

export default {
	name: "PanelCadastro",
	data() {
		return {
			
		};
	},
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
		title: {
			type: [String]
		},
		clear:  {
			type: [Function]
		},
		dadosPanel: {
			type: [Object]
		}
	}
};
</script>

<style lang="less" scope>
@import "../assets/css/variaveis.less";

	.v-expansion-panel-header {
		background-color: @bg-header;
		color: @text-color;
		cursor: default;
		font-size: 18px;
		height: 70px;

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
</style>