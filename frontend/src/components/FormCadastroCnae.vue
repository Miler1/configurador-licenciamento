<template lang="pug">

	v-form(ref="atividadeCnae")
		v-container.pa-0
			v-row
				v-col(cols="12", md="3")
					v-label Código
					v-text-field(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="atividadeCnae.codigo",
						@click.native="resetErrorMessage",
						:error-messages="errorMessageEmpty || atividadeCnae.codigo ? [] : 'Obrigatório'"
						required						
					)
				v-col(cols="12", md="9")
					v-label Nome
					v-text-field(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="atividadeCnae.nome",
						@click.native="resetErrorMessage",
						:error-messages="errorMessageEmpty || atividadeCnae.nome ? [] : 'Obrigatório'"
						required
					)
			v-row
				v-col#form-actions(cols="12", md="12")
					a#QA-limpar-dados(@click="clear")
						v-icon mdi-delete
						span Limpar Dados  
				
					v-btn#QA-btn-cadastrar(@click="submit")
						v-icon(color="white") mdi-plus
						span Cadastrar

</template>

<script>
export default {
	name: "FormCadastroCnae",
	data: () => {
		return {
			placeholder: "Digite aqui..."
		}
	},
	props: {
		atividadeCnae: {
			type: [Object]
		},
		clear: {
			type: [Function]
		},
		submit: {
			type: [Function]
		},
		resetErrorMessage:{
			type: [Function]
		},
		errorMessageEmpty: {
			type: [Boolean]
		}
	}
};
</script>

<style lang="less" scope>
@import "../assets/css/variaveis.less";

	#form-actions {
		align-items: center;
		display: flex !important;
		flex-direction: row !important;
		justify-content: flex-end;
		padding: 0 12px;
		
		#QA-limpar-dados {	
			align-items: center;
			display: flex !important;
			flex-direction: row;
			justify-content: flex-end;
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
	}

	.v-label {
		color: @text-color !important;
	}

	.v-text-field {
		fieldset {
			border: 1px solid @border-components;
			border-radius: 2px;
		}
	}
</style>
