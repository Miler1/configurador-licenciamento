<template lang="pug">

	v-form(ref="tipologia")
		v-container.pa-0
			v-row
				//- Campo ocultado e comentários relacionados no backend de acordo com a issue
				//- https://gitlab.ti.lemaf.ufla.br/amapa/configurador-licenciamento/issues/73
				//-
				//- v-col(cols="12", md="3")
				//- 		v-label Código
				//- 		v-text-field#QA-input-tipologia-codigo(
				//- 			outlined,
				//- 			color="#E0E0E0",
				//- 			:placeholder="placeholder"
				//- 			v-model="tipologia.codigo",
				//- 			@click.native="resetErrorMessage",
				//- 			dense
				//- 		)

				v-col(cols="9", md="12")
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
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
						a#QA-limpar-dados-cnae.d-flex.flex-row.align-center.justify-end(@click="clear")
							v-icon mdi-delete
							span Limpar dados

						v-btn#QA-btn-cadastrar-cnae(@click="submit", large, color="#84A98C")
							v-icon(color="white") {{iconBotaoCadastrarEditar}}
							span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}
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