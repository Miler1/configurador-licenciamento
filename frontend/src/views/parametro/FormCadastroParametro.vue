<template lang="pug">

	v-form(ref="parametro")
		v-row
			v-col(cols="12", md="2")
				v-label Código
				v-text-field#QA-input-parametro-codigo.codigo(
					outlined,
					dense,
					color="#E0E0E0",
					:placeholder="placeholder",
					v-model="parametro.codigo",
					:error-messages="errorMessage(parametro.codigo)",
					@input="v => {parametro.codigo = v.toUpperCase()}",
					@click.native="resetErrorMessage",
					required
				)
			v-col(cols="12", md="8")
				v-label Descrição
				v-text-field#QA-input-parametro-descricao(
					outlined,
					dense,
					color="#E0E0E0",
					:placeholder="placeholderDescricao",
					v-model="parametro.nome",
					:error-messages="errorMessage(parametro.nome)",
					@click.native="resetErrorMessage",
					required

				)
			v-col(cols="12", md="2")
				v-label Casas decimais
				v-text-field#QA-input-parametro-casas_decimais(
					outlined,
					dense,
					type="number",
					color="#E0E0E0",
					:placeholder="placeholder",
					min="0",
					step="1",
					v-model="parametro.casasDecimais",
					:error-messages="codigoErrorMessage(parametro.casasDecimais)",
					@click.native="resetErrorMessage",
					required
				)
		v-row
			v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
				a#QA-limpar-dados-parametro.d-flex.flex-row.align-center.justify-end(@click="clear")
					v-icon.pr-1 fa-eraser
					span Limpar dados
				v-btn#QA-btn-cadastrar-parametro(@click="submit", large, color="#84A98C")
					v-icon(color="white") {{iconBotaoCadastrarEditar}}
					span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}

</template>

<script>

export default {

	name: "FormCadastroParametro",

	data: () => {
		return {
			placeholder: "Digite aqui...",
			placeholderDescricao: "Informe o nome e a unidade de medida do parâmetro, se necessário. Ex.: Área útil (ha)"
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
		codigoErrorMessage: {
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