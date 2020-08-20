<template lang="pug">

	v-form(ref="documento")
		v-container.pa-0
			v-row
				v-col(cols="12", md="8")
					v-label Nome
					v-text-field#QA-input-documento-nome(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="documento.nome",
						@click.native="resetErrorMessage",
						:error-messages="errorMessage(documento.nome)"
						required,
						dense
					)
				v-col(cols="12", md="4")
					v-label Nome para exibição
					v-tooltip(top, max-width=400)
						template(v-slot:activator="{ on, attrs }")
							v-icon.information.mb-1.mr-1.float-right(v-bind="attrs", v-on="on") mdi-information
						p.text-center Este é o nome que será exibido para o usuário e técnico quando for realizado o upload deste documento.
					v-text-field#QA-input-documento-nome-exibicao(
						outlined,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="documento.prefixoNomeArquivo",
						@click.native="resetErrorMessage",
						:error-messages="errorMessage(documento.prefixoNomeArquivo)"
						required,
						dense
					)
			v-row
				v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
					a#QA-limpar-dados-documento.d-flex.flex-row.align-center.justify-end(@click="clear")
						v-icon mdi-delete
						span Limpar dados
				
					v-btn#QA-btn-cadastrar-documento(@click="submit", large)
						v-icon(color="white") {{iconBotaoCadastrarEditar}}
						span {{labelBotaoCadastrarEditar}}

</template>

<script>

export default {

	name: "FormCadastroDocumento",

	data: () => {
		return {
			placeholder: "Digite aqui..."
		};
	},

	props: {
		documento: {
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

<style lang="less" scope>

	.information {
		font-size: 16px !important;
	}

</style>
