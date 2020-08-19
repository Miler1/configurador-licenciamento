<template lang="pug">

	v-form(ref="parametro")
		v-container.pa-0
			v-row
				v-col(cols="12", md="2")
					v-label Código
					v-text-field#QA-input-parametro-codigo(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						v-model="parametro.codigo",
						:error-messages="errorMessage(parametro.codigo)"
						@click.native="resetErrorMessage",
						required						
					)
				v-col(cols="12", md="7")
					v-label Descrição
					v-text-field#QA-input-parametro-descricao(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						v-model="parametro.descricao",
						:error-messages="errorMessage(parametro.descricao)"
						@click.native="resetErrorMessage",
						required

					)
				v-col(cols="12", md="3")
					v-label Unidade
					v-select#QA-select-parametro-unidade(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder",
						v-model="parametro.unidade",
						:error-messages="errorMessage(parametro.unidade)"
						@click.native="resetErrorMessage",
						required
					)
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-parametro.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados

					v-btn#QA-btn-cadastrar-parametro(@click="submit")
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span {{labelBotaoCadastrarEditar}}
  
</template>

<script>

export default {

	name: "FormCadastroParametro",

	data: () => {
		return {
			placeholder: "Digite aqui..."
		};
	},

	props: {
		parametro: {
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
	}

};

</script>

<style lang="less">

</style>