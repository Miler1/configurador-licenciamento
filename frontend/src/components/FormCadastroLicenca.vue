<template lang="pug">

	v-form(ref="licenca")
		v-container.pa-0
			v-row
				v-col(cols="12", md="3")
					v-label Tipo
					v-text-field#QA-input-licenca-tipo(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="licenca.tipo",
						:error-messages="checkErrorMessage(licenca.tipo)"
						@click.native="resetErrorMessage",
						required,					
					)
				v-col(cols="12", md="9")
					v-label Nomenclatura
					v-text-field#QA-input-licenca-nomenclatura(
						outlined,
						dense,
						color="#E0E0E0",
						:placeholder="placeholder"
						v-model="licenca.nomenclatura",
						:error-messages="checkErrorMessage(licenca.nomenclatura)"
						@click.native="resetErrorMessage",
						required,
					)
			v-row
				v-col#col-validade.pr-0(cols="12", md="2")
					label Prazo de Validade
					div#div-validade
						v-text-field#QA-input-licenca-validade.my-input(
							outlined,
							dense,
							color="#E0E0E0",
							:placeholder="placeholder"
							v-model="licenca.validade",
							:error-messages="checkErrorMessage(licenca.validade)"
							@click.native="resetErrorMessage",
							required,
						)
						div#div-meses
							span Anos
				v-col#col-checkboxes.pl-7(cols="12", md="8")
					v-checkbox(
						v-model="licenca.podeRenovar",
						label="Passível de renovação",
						color="#84A98C",
					)
					v-checkbox.pl-7(
						v-model="licenca.naoEspira",
						label="Não expira",
						color="#84A98C",
					)
			v-row
				v-col#form-actions(cols="12", md="12")
					a#QA-limpar-dados-cnae(@click="clear")
						v-icon mdi-delete
						span Limpar Dados  
				
					v-btn#QA-btn-cadastrar-licenca(@click="submit")
						v-icon(color="white") mdi-plus
						span Cadastrar
  
</template>

<script>

export default {

	name: "FormCadastroLicenca",

	data: () => {
		return {
			placeholder: "Digite aqui..."
		}
	},

	props: {
		licenca: {
			type: [Object]
		},
		submit: {
			type: [Function]
		},
		clear: {
			type: [Function]
		},
		resetErrorMessage: {
			type: [Function]
		},
		errorMessageEmpty: {
			type: [Boolean]
		},
		checkErrorMessage: {
			type: [Function]
		}
	}
}

</script>

<style lang="less">

@import "../assets/css/variaveis.less";

	#col-validade {
		display:flex;
		flex-direction: column;
		justify-content: flex-start;

		#div-validade {
			display: flex;
			flex-direction: row;

			#div-meses {
				border: none;
				border-radius: 0 4px 4px 0;
				background-color: @bg-header;
				color: @icon-color;
				display: flex;
				height: 40px;

				span {
					align-self: center;
					padding: 1px 8px 0px 8px;
				}
			}
		}
	}

	#col-checkboxes {
		align-items: center;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
	}

</style>