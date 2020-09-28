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
						:error-messages="errorMessage(atividadeCnae.codigo)"
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
						:error-messages="errorMessage(atividadeCnae.nome)"
						required,
						dense
					)
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-cnae.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon fa-eraser
						span Limpar dados

					v-btn#QA-btn-cadastrar-cnae(@click="submit",color="#84A98C", large)
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}

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
