<template lang="pug">

	v-form(ref="pergunta")
		v-container.pa-0
			v-row
				v-col.pb-0(cols="12", md="12")
					v-label Pergunta
					v-text-field#QA-input-pergunta(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="pergunta.texto",
						@click.native="resetErrorMessage",
						:error-messages="errorMessage(pergunta.texto)"
						required,
						dense
					)
			v-row
				v-col(cols="12", md="12")
					div.mb-5
						v-label Opções de respostas:
					div(v-for="item in pergunta.respostas" :key="item.texto")
						b.mr-3 {{item.texto}}
						v-checkbox.mt-0.d-inline-flex(v-model="item.permiteLicenciamento", label="Esta opção permite prosseguir", color="#84A98C")
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-cnae.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados
				
					v-btn#QA-btn-cadastrar-cnae(@click="submit",color="#84A98C", large)
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span.label-btn-cadastrar {{labelBotaoCadastrarEditar}}

</template>

<script>

export default {

	name: "FormCadastroPergunta",

	data: () => {
		return {
			placeholder: "Digite aqui..."
		};
	},

	props: {
		pergunta: {
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
