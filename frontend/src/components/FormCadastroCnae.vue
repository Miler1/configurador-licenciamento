<template lang="pug">

	v-form(ref="atividadeCnae")
		v-container.pa-0
			v-row
				v-col(cols="12", md="3")
					v-label Código
					v-text-field#QA-input-cnae-codigo(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="atividadeCnae.codigo",
						@click.native="resetErrorMessage",
						:error-messages="errorMessageEmpty || atividadeCnae.codigo ? [] : 'Obrigatório'"
						required,
						dense
					)
				v-col(cols="12", md="9")
					v-label Nome
					v-text-field#QA-input-cnae-nome(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="atividadeCnae.nome",
						@click.native="resetErrorMessage",
						:error-messages="errorMessageEmpty || atividadeCnae.nome ? [] : 'Obrigatório'"
						required,
						dense
					)
			v-row
				v-col#form-actions(cols="12", md="12")
					a#QA-limpar-dados-cnae(@click="clear")
						v-icon mdi-delete
						span Limpar dados  
				
					v-btn#QA-btn-cadastrar-cnae(@click="submit", large)
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span {{labelBotaoCadastrarEditar}}

</template>

<script>

export default {

	name: "FormCadastroCnae",

	data: () => {
		return {
			placeholder: "Digite aqui..."
		};
	},

	props: {
		atividadeCnae: {
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
		errorMessageEmpty: {
			type: [Boolean]
		},
		labelBotaoCadastrarEditar: {
			type: [String]
		},
		iconBotaoCadastrarEditar: {
			type: [String]
		}
	}

};

</script>

<style lang="less" scope>

</style>
