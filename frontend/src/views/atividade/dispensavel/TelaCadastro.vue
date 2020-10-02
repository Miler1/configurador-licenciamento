<template lang="pug">

#tela-cadastro-atividade-dispensavel.px-7.pb-7

	v-row
		v-col(cols="12")#stepper-container

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

		v-col(cols="12")

			.tituloAcao {{isCadastro ? 'Cadastro de CNAEs dispensáveis' : 'Editar CNAE dispensável'}}

		v-col.py-0(cols="12")

			PassoCnaes(
				v-if="passo == 1",
				:cnaesTipologia="atividadeDispensavel.cnaesTipologia",
				:erro="erros[0]"
			)

			PassoPerguntas(
				v-if="passo == 2",
				:perguntas="atividadeDispensavel.perguntas",
				:erro="erros[1]"
			)

			Resumo(
				v-if="passo == 3",
				:atividadeDispensavel="atividadeDispensavel"
			)

	v-row
		v-col.d-flex.justify-center.alignt-center.step-indicator

			span Etapa {{passo}} de {{passos.length}}

	v-row
		v-col#form-actions.d-flex.justify-space-between(cols="12", md="12", flex=1)

			v-btn#QA-btn-cancelar-atividade-dispensavel(@click="cancelar", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
				v-icon mdi-close
				span Cancelar

			div
				v-btn#QA-btn-rascunho-atividade-dispensavel(v-show="false", @click="salvarRascunho", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
					v-icon mdi-content-save
					span Salvar

				v-btn#QA-btn-voltar-atividade-dispensavel.ml-2(v-show="passo != 1", :disabled="passo === 1", @click="previousStep", outlined, :min-width="buttonMinWidth", large, color="#84A98C")
					v-icon mdi-arrow-left
					span Voltar

				v-btn#QA-btn-proximo-cadastrar-atividade-dispensavel.ml-2.btn-cadastrar(@click="nextOrSubmit", :min-width="buttonMinWidth", large)
					v-icon(color="white") {{nextButtonDecider().icon}}
					span {{nextButtonDecider().text}}

</template>

<script>

import PassoCnaes from '@/views/atividade/dispensavel/components/Cnaes.vue';
import PassoPerguntas from '@/views/atividade/dispensavel/components/Perguntas.vue';
import Resumo from '@/views/atividade/dispensavel/components/Resumo.vue';
import TipoCaracterizacaoAtividadeService from '@/services/tipoCaracterizacaoAtividade.service';
import snackbar from '@/services/snack.service';
import { ERROR_MESSAGES, SUCCESS_MESSAGES } from '@/utils/helpers/messages-utils';

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
					completo: false,
					validar: null
				},
				{
					titulo: "Perguntas",
					completo: false,
					validar: null
				},
				{
					titulo: "Resumo",
					completo: false,
					validar: null
				}
			],
			erros: [
				{ invalido: false },
				{ invalido: false },
				{ invalido: false }
			],
			atividadeDispensavel: {
				cnaesTipologia: [],
				perguntas: []
			},
			allowRedirect: false,
			isCadastro: true,
			buttonMinWidth: "9em"
		};

	},

	methods: {

		cadastrar(){

			this.prepararDados();

			if (this.validar()) {

				TipoCaracterizacaoAtividadeService.cadastrarAtividadeDispensavel(this.atividadeDispensavel)
					.then(() => {
						this.handleSuccess();
					})
					.catch(error => {
						this.handleError(error);
					});

			}

		},

		editar() {

			this.prepararDados();

			if (this.validar()) {

				TipoCaracterizacaoAtividadeService.editarAtividadeDispensavel(this.atividadeDispensavel)
					.then( () => {
						this.handleSuccess(true);
					})
					.catch(error => {
						this.handleError(error, true);
					});

			}

		},

		prepararDados() {

			this.atividadeDispensavel.cnaesTipologia.forEach(cnaeTipologia => {

				cnaeTipologia.foraMunicipio = cnaeTipologia.foraMunicipio === 'true' ? true : false;
				delete cnaeTipologia.cnae.textoExibicao;

			});

		},

		salvarRascunho() {
			alert("Só vou conseguir salvar pra você quando for implementado :(");
		},

		lastStep() {
			return this.passo === this.passos.length;
		},

		nextStep() {

			if (this.validar()) {

				this.passo += 1;
				window.scrollTo(0,0);

			}

		},

		validar() {

			let possivel = true;

			for (let i = 0; i < this.passo; i++) {

				if (!this.passos[i].validar()) {

					possivel = false;
					this.erros[i].invalido = true;

				}

			}

			return possivel;

		},

		previousStep() {

			if (this.passo > 1) {

				this.passo -= 1;
				window.scrollTo(0,0);

			}

		},

		nextOrSubmit() {

			if (this.lastStep()) {

				if (this.isCadastro) {
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
				text: lastStep ? (this.isCadastro ? "Cadastrar" : "Editar") : "Próxima",
				icon: lastStep ? (this.isCadastro ? "mdi-plus" : "mdi-pencil") : "mdi-arrow-right"
			};

		},

		validarCnaesTipologias() {

			let cnaesTipologias = this.atividadeDispensavel.cnaesTipologia;
			let valido = this.passos[0].completo = cnaesTipologias && cnaesTipologias.length > 0;

			if (!valido) {
				snackbar.alert(ERROR_MESSAGES.atividadeDispensavel.cnaes.avancarEtapa, snackbar.type.WARN);
			}

			return valido;

		},

		validarPerguntas() {

			let perguntas = this.atividadeDispensavel.perguntas;
			let valido = this.passos[1].completo = perguntas && perguntas.length > 0;

			if (!valido) {
				snackbar.alert(ERROR_MESSAGES.atividadeDispensavel.perguntas.avancarEtapa, snackbar.type.WARN);
			}

			return valido;

		},

		confirmarCancelamento(next) {

			this.$fire({

				title: '<p class="title-modal-confirm">Confirmar cancelamento - CNAEs dispensáveis </p>',

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

		handleError(error, edicao = false) {

			console.log(error.message);

			let message = edicao ? ERROR_MESSAGES.requisitoAdministrativo.editar : ERROR_MESSAGES.requisitoAdministrativo.cadastro;

			snackbar.alert(message);

		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			snackbar.alert(message, snackbar.type.SUCCESS);

			// this.clear();
			this.redirectListagem();

		},

		redirectListagem(allowed = true) {

			this.allowRedirect = allowed;
			this.$router.push({name: 'cnaesDispensaveis'});

		},

		prepararDadosParaEdicao(atividadeDispensavel) {

			this.atividadeDispensavel = atividadeDispensavel;

			this.atividadeDispensavel.cnaesTipologia.forEach(cnaeTipologia => {
				cnaeTipologia.foraMunicipio = cnaeTipologia.foraMunicipio ? 'false' : 'true';
			});

		}

	},

	created() {

		if (this.$route.params.idAtividadeDispensavel) {

			this.isCadastro = false;

			TipoCaracterizacaoAtividadeService.findById(this.$route.params.idAtividadeDispensavel)
				.then((response) => {
					this.prepararDadosParaEdicao(response.data);
				})
				.catch(error => {
					console.log(error.message);
				});

		}

	},

	mounted() {

		this.passos[0].validar = this.validarCnaesTipologias;
		this.passos[1].validar = this.validarPerguntas;
		this.passos[2].validar = () => true;

	},

	beforeRouteLeave(to, from, next) {

		if (!this.allowRedirect) {
			this.confirmarCancelamento(next);
		} else {
			next();
		}

	}

};

</script>

<style lang="less">

@import "../../../assets/css/variaveis.less";


#tela-cadastro-atividade-dispensavel {

	.tituloAcao {
		font-size: 22px;
	}

	.step-indicator {
		color: @text-color;
		font-size: 14px;
		text-rendering: optimizeLegibility;
		font-weight: bold;
	}

	#stepper-container {
		padding-left: 20%;
		padding-right: 20%;
		text-align: center;

		.v-stepper {
			box-shadow: unset;
		}

		.v-stepper__header {
			box-shadow: unset;
		}

		.v-stepper__step {

			.v-stepper__step__step {
				width: 32px;
				height: 32px;
			}
		}

		.v-divider {
			margin: 40px -66px 0 !important;
		}

		.v-stepper__step--complete {

			.v-stepper__step__step {
				background-color: white !important;
				border-color: rgb(132, 169, 140);
				border: 1px solid;

				.v-icon {
					color: rgb(132, 169, 140) !important;
				}
			}
		}

		.v-stepper__step--active {

			.v-stepper__step__step {
				background-color: rgb(132, 169, 140) !important;

				.v-icon {
					color:white !important;
				}
			}
		}

		.v-stepper__step--active {

			.v-stepper__label {
				text-shadow: none !important;
				color: @text-color !important;
				font-weight: bold;
			}
		}

	}

	#form-actions {

		padding: 0 12px;

		a {
			margin-right: 20px;

			.v-icon, span {
				color: @red;
			}
		}

		.v-btn {
			font-size: 16px;
			text-transform: none !important;
		}

		.v-icon {
			font-size: 20px !important;
		}

		.btn-cadastrar {
			background-color: @green-primary !important;
			color: @bg-text-field !important;
		}
	}
}

</style>