<template lang="pug">

#tela-cadastro-atividade-dispensavel

		div.stepper-container
			v-stepper(v-model="passo", alt-labels=true)
				v-stepper-header(flat)
					template(v-for="(passo, index) in passos")
						v-stepper-step(
							:key="`passo${index}-${passo.titulo}`",
							:complete="passo.completo",
							:step="index + 1",
							:editable="false"
							color="#84A98C"
						)
							span {{passo.titulo}}

						v-divider(v-if="index !== passos.length - 1", :key="Math.random()")
		
		PassoCnaes(v-if="passo == 1")

		PassoPerguntas(v-if="passo == 2")

		Resumo(v-if="passo == 3")

		v-row.pt-6.px-7
			v-col#form-actions.d-flex.justify-space-between(cols="12", md="12", flex=1)
				v-btn#QA-btn-cancelar-atividade-dispensavel.align-self-start(@click="cancelar", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
					v-icon mdi-close
					span Cancelar

				div.right-buttons
					v-btn#QA-btn-rascunho-atividade-dispensavel(@click="salvarRascunho", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
						v-icon mdi-content-save
						span Salvar

					v-btn#QA-btn-voltar-atividade-dispensavel.ml-2(:disabled="passo === 1", @click="previousStep", outlined, :min-width="buttonMinWidth", large, color="#84A98C")
						v-icon mdi-arrow-left
						span Voltar

					v-btn#QA-btn--proximo-cadastrar-atividade-dispensavel.ml-2.btn-cadastrar(@click="nextOrSubmit", :min-width="buttonMinWidth", large)
						v-icon(color="white") {{nextButtonDecider().icon}}
						span {{nextButtonDecider().text}}


</template>

<script>

import PassoCnaes from '@/views/atividade/dispensavel/components/Cnaes.vue';
import PassoPerguntas from '@/views/atividade/dispensavel/components/Perguntas.vue';
import Resumo from '@/views/atividade/dispensavel/components/Resumo.vue';

export default {

	name:'TelaCadastroAtividadeDispensavel',

	components: {
		PassoCnaes,
		PassoPerguntas,
		Resumo
	},
	
	data: () => {

		return {

			passo: 1,
			passos: [
				{
					titulo: "CNAEs",
					completo: false
				},
				{
					titulo: "Perguntas",
					completo: false
				},
				{
					titulo: "Resumo",
					completo: false
				}
			],
			allowRedirect: false,
			isCadastro: true,
			buttonMinWidth: "9em"
		};

	},

	methods: {

		cadastrar(){
			alert("Cadastrando processamento intergaláctico :)");
		},

		editar() {
			alert("Editando esse rolê que já tava salvo :D");
		},

		salvarRascunho() {
			alert("Só vou conseguir salvar pra você quando for implementado :(");
		},

		lastStep() {
			return this.passo === this.passos.length;
		},

		nextStep() {
			this.passo += 1;
		},

		previousStep() {
			if(this.passo > 1){
				this.passo -= 1;
			}
		},

		nextOrSubmit() {

			if(this.lastStep()){

				if(this.isCadastro){
					this.cadastrar();
				} else {
					this.editar();
				}

			} else {
				this.nextStep();
			}

		},

		cancelar() {
			this.$router.go(-1);
		},

		nextButtonDecider() {

			let lastStep = this.lastStep();

			return {
				text: lastStep ? (this.isCadastro ? "Cadastrar" : "Editar") : "Próximo",
				icon: lastStep ? (this.isCadastro ? "mdi-plus" : "mdi-pencil") : "mdi-arrow-right"
			};
		},

		confirmarCancelamento(next) {

			this.$fire({

				title: '<p class="title-modal-confirm">Confirmar cancelamento </p>',

				html: this.isCadastro ?
					`<p class="message-modal-confirm">Ao cancelar o cadastro, todas as informações serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar o cadastro? Esta opção não poderá ser desfeita e todas as informações serão perdidas.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao cancelar a edição, todas as informações alteradas serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar a edição? Esta opção não poderá ser desfeita e todas as informações serão perdidas.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor: '#67C23A',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: '<i class="fa fa-check-circle"></i> Confirmar',
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {

				if (result.value) {
					next();
				}
				
			}).catch((error) => {
				console.error(error);
			});

		},

	},

	created(){
	},

	mounted() {

	},

	beforeRouteLeave(to, from, next) {

		if(!this.allowRedirect){
			this.confirmarCancelamento(next);
		} else {
			next();
		}

	}

};

</script>

<style lang="less">

@import "../../../assets/css/variaveis.less";

.btn-cadastrar {
	background-color: @green-primary !important;
	color: @bg-text-field !important;
}

.stepper-container {

	padding-left: 20%;
	padding-right: 20%;

	.v-stepper {
		box-shadow: unset;
	}

	.v-stepper__header {
		box-shadow: unset;
	}

}

</style>