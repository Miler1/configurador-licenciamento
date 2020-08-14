<template lang="pug">

	v-form(ref="tipologia")
		v-container.pa-0
			v-row
				v-col(cols="12", md="3")
					v-label Código
					v-text-field#QA-input-tipologia-codigo(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="tipologia.codigo",
						@click.native="resetErrorMessage",
						dense
					)
				v-col(cols="12", md="9")
					v-label Nome
					v-text-field#QA-input-tipologia-nome(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="tipologia.nome",
						@click.native="resetErrorMessage",
						:error-messages="errorMessageEmpty || tipologia.nome ? [] : msgmObrigatorio"
						required,
						dense
					)
			v-row
				v-col#form-actions(cols="12", md="12")
					a#QA-limpar-dados-cnae(@click="clear")
						v-icon mdi-delete
						span Limpar dados  
				
					v-btn#QA-btn-cadastrar-cnae(@click="submit")
						v-icon(color="white") mdi-plus
						span Cadastrar

</template>

<script>

export default {

	name: "FormCadastroTipologia",

	data: () => {
		return {
			placeholder: "Digite aqui...",
			msgmObrigatorio: "Obrigatório"
		};
	},

	props: {
		tipologia: {
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
		}
	}

};

</script>

<style lang="less" scope>

</style>
