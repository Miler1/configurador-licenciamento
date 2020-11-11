<template lang="pug">

#tela-cadastro-atividade-licenciavel.px-7.pb-7

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
							span.noWrap {{passo.titulo}}

						v-divider(v-if="index !== passos.length - 1", :key="Math.random()")

		v-col(cols="12")

			.tituloAcao {{isCadastro || this.$route.name === 'ContinuarCadastroAtividadeLicenciavel' ? 'Cadastro de atividade licenciável' : 'Editar atividade licenciável'}}

		v-col.py-0(cols="12")

			PassoAtividades(
				ref="telaAtividades"
				v-if="passo == 1",
				:cnaesAtividade="atividadeLicenciavel.cnaesAtividade",
				:dados="atividadeLicenciavel.dados",
				:erro="erros[0]",
				:erroCnae="errosCnae[0]",
				:erroRascunho="errosRascunho[0]"
			)

			PassoParametros(
				v-if="passo == 2",
				:parametros="atividadeLicenciavel.parametros",
				:dados="atividadeLicenciavel.dados",
				:erro="erros[1]",
				:erroRascunho="errosRascunho[1]"
			)

			Resumo(
				v-if="passo == 3",
				:atividadeLicenciavel="atividadeLicenciavel"
			)

	v-row
		v-col.d-flex.justify-center.alignt-center.step-indicator

			span Etapa {{passo}} de {{passos.length}}

	v-row
		v-col#form-actions.d-flex.justify-space-between(cols="12", md="12", flex=1)

			v-btn#QA-btn-cancelar-atividade-licenciavel(@click="cancelar", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
				v-icon mdi-close
				span Cancelar

			div
				v-btn#QA-btn-voltar-atividade-licenciavel.ml-2(v-show="passo != 1", :disabled="passo === 1", @click="previousStep", outlined, :min-width="buttonMinWidth", large, color="#84A98C")
					v-icon mdi-arrow-left
					span Voltar

				v-btn#QA-btn-rascunho-atividade-licenciavel.ml-2(v-show="isCadastro || this.$route.name === 'ContinuarCadastroAtividadeLicenciavel'", @click="salvarRascunho", :min-width="buttonMinWidth", outlined, large, color="#84A98C")
					v-icon mdi-floppy
					span Salvar

				v-btn#QA-btn-proximo-cadastrar-atividade-licenciavel.ml-2.btn-cadastrar(@click="nextOrSubmit", :min-width="buttonMinWidth", large)
					v-icon(color="white") {{nextButtonDecider().icon}}
					span {{nextButtonDecider().text}}

</template>

<script>

import PassoAtividades from '@/views/atividade/licenciavel/components/Atividades.vue';
import PassoParametros from '@/views/atividade/licenciavel/components/Parametros.vue';
import AtividadeService from '@/services/atividade/licenciavel.service';
import Resumo from '@/views/atividade/licenciavel/components/Resumo.vue';
import snackbar from '@/services/snack.service';
import { ERROR_MESSAGES, SUCCESS_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name:'TelaCadastroAtividadeLicenciavel',

	components: {
		PassoAtividades,
		PassoParametros,
		Resumo
	},
	data: () => {

		return {
			passo: 1,
			passos: [
				{
					titulo: "Atividade",
					completo: false,
					validar: null,
					validarRascunho: null,
				},
				{
					titulo: "Parâmetros / Portes",
					completo: false,
					validar: null,
					validarRascunho: null
				},
				{
					titulo: "Resumo",
					completo: false,
					validar: null,
				},
			],
			erros: [
				{ invalido: false },
				{ invalido: false },
				{ invalido: false }
			],
			errosCnae: [
				{ invalido: false }
			],
			errosRascunho: [
				{ invalido: false },
				{ invalido: false }
			],
			atividadeLicenciavel: {
				cnaesAtividade: [],
				dados: {
					codigoAtividade: null,
					nomeAtividade: null,
					tipologia: null,
					licencas: [],
					potencialPoluidor: null,
					setor: null,
					foraEmpreendimento: null,
					cnaes: [],
					requisitoTecnico: null,
					taxaLicenciamento: null,
					tiposAtividade: [],
					geoPonto: false,
					geoLinha: false,
					geoPoligono: false,
				},
				parametros: [],
				justificativa: null,
			},
			primeiroRascunho: false,
			atividadeLicenciavelBkp: {},
			allowRedirect: false,
			isCadastro: true,
			isRascunho:false,
			buttonMinWidth: "9em"
		};

	},

	methods: {

		cadastrar(){

			this.prepararDados();

			if (this.validar()) {

				window.scrollTo(0, 0);

				AtividadeService.cadastrarAtividadeLicenciavel(this.atividadeLicenciavel)
					.then(() => {
						this.handleSuccess();
					}).catch(error => {

						console.error(error);
						this.atividadeLicenciavel = this.atividadeLicenciavelBkp;
						this.handleError(error);

					});

			}

		},

		editar() {

			let acao = {};

			acao.confirmar = (result) => {

				if (result.value) {

					let justificativa = result.value;

					this.prepararDados();

					if (justificativa && this.validar()) {

						this.atividadeLicenciavel.justificativa = justificativa;

						AtividadeService.editarAtividadeLicenciavel(this.atividadeLicenciavel)
							.then( (response) => {
								this.handleSuccess(true);
							})
							.catch(error => {

								console.error(error);
								this.handleError(error, true);

							});

					}

				}

			};

			this.modalConfirmacao(acao);

		},

		modalConfirmacao(acao) {

			if (this.isRascunho) {

				const result = {value: true};

				acao.confirmar(result);

			} else {

				this.$fire({

					title: `<div><p class="title-modal-confirm">Confirmar edição - Atividade licenciável</p><div>`,
					html:
						`
						<div class="row" id="row-justificativa-atividade-licenciavel" style="padding-top:15px">
							<div class="col col-12" style="display:flex; flex-direction: column;">
								<label id="label-justificativa" aria-hidden="true" class="v-label theme--light" style="text-align: left; padding-bottom:4px">Justificativa</label>
								<div class="v-input v-textarea v-textarea--auto-grow v-textarea--no-resize theme--light v-text-field v-text-field--is-booted v-text-field--enclosed v-text-field--outlined" id="div-input">
									<div class="v-input__control">
										<div class="v-input__slot style="padding-right: 0">
											<fieldset aria-hidden="true">
												<legend style="width: 0px;">
													<span>​</span>
												</legend>
											</fieldset>
											<div class="v-text-field__slot">
												<textarea id="QA-input-atividade-licenciavel-justificativa" rows="4" placeholder="Digite aqui..." required="required"></textarea>
											</div>
										</div>
										<div class="v-text-field__details">
											<div class="v-messages theme--light">
												<div class="v-messages__wrapper" id="texto-mensagem"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						`,
					width: '580px',
					showCancelButton: true,
					confirmButtonColor:'#67C23A',
					cancelButtonColor: '#FFF',
					showCloseButton: true,
					focusConfirm: false,
					confirmButtonText: '<i class="fa fa-check-circle" style="left:0px"></i> Confirmar',
					cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
					reverseButtons: true,
					preConfirm: () => {

						const campoJustificativa = document.getElementById("QA-input-atividade-licenciavel-justificativa");

						let justificativa = campoJustificativa.value.trim();

						if (justificativa) {
							return justificativa;
						} else {

							const input = document.getElementById("div-input");
							const mensagem = document.getElementById("texto-mensagem");

							const classes = ["v-input--has-state", "v-text-field--placeholder", "error--text"];
							input.classList.add(...classes);
							input.style.color = "#ff5252";

							mensagem.innerHTML = "Obrigatório";
							mensagem.style.color = "#ff5252";
							mensagem.style.caretColor = "#ff5252";

							campoJustificativa.setAttribute("margin-top", "10px");
							campoJustificativa.setAttribute("widith", "100%");
							campoJustificativa.setAttribute("resize", "none");
							campoJustificativa.setAttribute("padding-right", "12px");

							return false;

						}

					}

				}).then((result) => {
					acao.confirmar(result);
				}).catch((error) => {
					console.log("error", error);
				});

			}

		},

		salvarRascunho() {

			if (this.validarRascunho()) {

				this.prepararDados();

				window.scrollTo(0, 0);

				AtividadeService.salvarRascunhoAtividadeLicenciavel(this.atividadeLicenciavel)
					.then((response) => {

						this.atividadeLicenciavel =  JSON.parse(JSON.stringify(this.atividadeLicenciavelBkp) );

						this.atividadeLicenciavel.dados.id = response.data.id;

						this.atividadeLicenciavel.dados.rascunho = response.data.rascunho;

						this.handleSuccess(false, true);

					})
					.catch(error => {

						this.atividadeLicenciavel = this.atividadeLicenciavelBkp;

						this.handleError(error, false, true);

					});

			}

		},

		prepararDados() {

			this.atividadeLicenciavelBkp =  JSON.parse(JSON.stringify(this.atividadeLicenciavel) );

			this.atividadeLicenciavel.cnaesAtividade.forEach(atividade => {
				delete atividade.textoExibicao;
			});

			if (this.atividadeLicenciavel.dados.setor && this.atividadeLicenciavel.dados.setor.sigla) {
				this.atividadeLicenciavel.dados.setor = this.atividadeLicenciavel.dados.setor.sigla;
			}

			this.atividadeLicenciavel.parametros.forEach(parametro => {

				if (parametro.limiteInferiorUm && typeof parametro.limiteInferiorUm === 'string') {
					parametro.limiteInferiorUm = parseFloat(parametro.limiteInferiorUm.replace(/R\$\s|\./g, '').replace(',', '.'));
				}

				if (parametro.limiteSuperiorUm && typeof parametro.limiteSuperiorUm === 'string') {
					parametro.limiteSuperiorUm = parseFloat(parametro.limiteSuperiorUm.replace(/R\$\s|\./g, '').replace(',', '.'));
				}

				if (parametro.limiteInferiorDois && typeof parametro.limiteInferiorDois === 'string') {
					parametro.limiteInferiorDois = parseFloat(parametro.limiteInferiorDois.replace(/R\$\s|\./g, '').replace(',', '.'));
				}

				if (parametro.limiteSuperiorDois && typeof parametro.limiteSuperiorDois === 'string') {
					parametro.limiteSuperiorDois = parseFloat(parametro.limiteSuperiorDois.replace(/R\$\s|\./g, '').replace(',', '.'));
				}
				//tratar valores nulos
				parametro.limiteInferiorUmIncluso = parametro.limiteInferiorUmIncluso ? parametro.limiteInferiorUmIncluso : false;
				parametro.limiteSuperiorUmIncluso = parametro.limiteSuperiorUmIncluso ? parametro.limiteSuperiorUmIncluso : false;
				parametro.limiteInferiorDoisIncluso = parametro.limiteInferiorDoisIncluso ? parametro.limiteInferiorDoisIncluso : false;
				parametro.limiteSuperiorDoisIncluso = parametro.limiteSuperiorDoisIncluso ? parametro.limiteSuperiorDoisIncluso : false;

			});

		},

		validar() {

			let possivel = true;

			for (let i = 0; i < this.passo; i++) {

				if (!this.passos[i].validar()) {

					possivel = false;
					this.erros[i].invalido = true;
					this.errosCnae[0].invalido = true;
					this.errosRascunho[i].invalido = false;

				}

			}

			return possivel;

		},

		validarCnaesAtividades() {

			let cnaesAtividades = this.atividadeLicenciavel.cnaesAtividade;
			let dados = this.atividadeLicenciavel.dados;

			let valido = this.passos[0].completo =
				cnaesAtividades && cnaesAtividades.length > 0;

			if (!valido) {

				window.scrollTo(0, 0);
				snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.atividades.avancarEtapaCnae, snackbar.type.WARN);

				return valido;

			}

			valido = this.passos[0].completo = dados.codigoAtividade != null && dados.codigoAtividade != '' &&
				dados.nomeAtividade != null && dados.nomeAtividade != '' &&
				dados.licencas && dados.licencas.length > 0 &&
				dados.potencialPoluidor != null &&
				dados.setor &&
				dados.tiposAtividade && dados.tiposAtividade.length > 0 &&
				dados.foraEmpreendimento != null &&
				(dados.geoPonto || dados.geoLinha || dados.geoPoligono) &&
				dados.requisitoTecnico != null &&
				dados.taxaLicenciamento != null;

			if (!valido) {
				window.scrollTo(0, 0);
				// snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.atividades.avancarEtapa, snackbar.type.WARN);
			}

			return valido;

		},

		validarParametros() {

			let parametros = this.atividadeLicenciavel.parametros;
			let valido = this.passos[1].completo = parametros && parametros.length > 0;

			if (!valido) {

				snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.parametros.avancarEtapa, snackbar.type.WARN);

				return valido;

			}

			parametros.forEach(parametro => {

				if (!parametro.porte) {
					valido = false;
				}

			});

			if (!valido) {
				snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.parametros.avancarEtapaPorte, snackbar.type.WARN);
			}

			return valido;

		},

		validarRascunho() {

			let possivel = true;

			for (let i = 0; i < this.passo; i++) {

				if (!this.passos[i].validarRascunho()) {

					possivel = false;
					this.erros[i].invalido = false;
					this.errosCnae[0].invalido = false;
					this.errosRascunho[i].invalido = true;

				}

			}

			return possivel;

		},

		validarRascunhoAtividades() {

			let dados = this.atividadeLicenciavel.dados;

			let valido = this.passos[0].completo = dados.codigoAtividade != null && dados.codigoAtividade != '' &&
				dados.nomeAtividade != null && dados.nomeAtividade != '';

			if (!valido) {

				window.scrollTo(0, 0);
				// snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.rascunho.salvar, snackbar.type.WARN);
				return valido;

			}

			return valido;

		},

		validarRascunhoParametros() {

			let parametros = this.atividadeLicenciavel.parametros;
			let valido = this.passos[1].completo = parametros && parametros.length > 0;

			if (!valido) {

				window.scrollTo(0, 0);
				snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.parametros.rascunho.salvar, snackbar.type.WARN);

			}

			return valido;

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

			this.allowRedirect = false;
			this.$router.push({name: 'atividadesLicenciaveis'});

		},

		nextButtonDecider() {

			let lastStep = this.lastStep();

			return {
				text: lastStep ? (this.isCadastro ? "Cadastrar" : (this.$route.name === 'ContinuarCadastroAtividadeLicenciavel' ? "Cadastrar" : "Editar")) : "Próxima",
				icon: lastStep ? (this.isCadastro ? "mdi-plus" : (this.$route.name === 'ContinuarCadastroAtividadeLicenciavel' ? "mdi-plus" : "mdi-pencil")) : "mdi-arrow-right"
			};

		},

		confirmarCancelamento(next) {

			this.$fire({

				title: '<p class="title-modal-confirm">Confirmar cancelamento - Atividade licenciável</p>',

				html: this.isCadastro || this.isRascunho ?
					`<p class="message-modal-confirm">Ao cancelar o cadastro, todas as informações que não foram salvas serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar o cadastro? Esta opção não poderá ser desfeita e todas as informações que não foram salvas serão perdidas.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao cancelar a edição, todas as informações alteradas e que não foram salvas serão perdidas.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja cancelar a edição? Esta opção não poderá ser desfeita e todas as informações alteradas e que não foram salvas serão perdidas.</b>
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

		handleError(error, edicao = false, isRascunho =  false) {

			console.error(error.message);

			let message = "";

			if (isRascunho) {
				message += ERROR_MESSAGES.atividadeLicenciavel.salvarRascunho;
			} else {
				message += edicao ? ERROR_MESSAGES.atividadeLicenciavel.editar : ERROR_MESSAGES.atividadeLicenciavel.cadastro;
			}

			message += error.message;

			snackbar.alert(message);

		},

		handleSuccess(edicao = false, rascunho = false) {

			let message = edicao ?
				(this.$route.name === 'ContinuarCadastroAtividadeLicenciavel' ?
					SUCCESS_MESSAGES.cadastro :
					SUCCESS_MESSAGES.editar) :
				(rascunho ?
					SUCCESS_MESSAGES.salvarRascunho :
					SUCCESS_MESSAGES.cadastro);


			snackbar.alert(message, snackbar.type.SUCCESS);

			if (!rascunho) {
				this.redirectListagem();
			}

		},

		redirectListagem(allowed = true) {

			this.allowRedirect = allowed;
			this.$router.push({name: 'atividadesLicenciaveis'});

		},

		prepararDadosParaEdicao(atividadeLicenciavel) {

			this.atividadeLicenciavel = atividadeLicenciavel;

			let dados = this.atividadeLicenciavel.dados;

			if (dados.requisitoTecnico != null) {
				dados.requisitoTecnico.textoExibicao = dados.requisitoTecnico.codigo + ' - ' + dados.requisitoTecnico.descricao;
			}

			if (dados.taxaLicenciamento != null) {
				dados.taxaLicenciamento.textoExibicao = dados.taxaLicenciamento.codigo + ' - ' + dados.taxaLicenciamento.descricao;
			}

		}

	},

	created() {

		if (this.$route.params.idAtividadeLicenciavel) {

			this.isCadastro = false;

			AtividadeService.findByIdAtividadeLicenciavel(this.$route.params.idAtividadeLicenciavel)
				.then((response) => {

					this.prepararDadosParaEdicao(response.data);

					if (this.atividadeLicenciavel.dados.foraEmpreendimento !== null) {
						this.$refs.telaAtividades.$refs.toggleOptionsForaEmpreendimento.setModel(this.atividadeLicenciavel.dados.foraEmpreendimento.toString());
					}

					if (this.atividadeLicenciavel.dados.rascunho) {
						this.isRascunho = true;
					}

				})
				.catch(error => {
					console.log(error.message);
				});

		}

	},

	mounted() {

		this.passos[0].validarRascunho = this.validarRascunhoAtividades;
		this.passos[1].validarRascunho = this.validarRascunhoParametros;
		this.passos[2].validarRascunho = () => true;

		this.passos[0].validar = this.validarCnaesAtividades;
		this.passos[1].validar = this.validarParametros;
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

#tela-cadastro-atividade-licenciavel {

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

		.noWrap{
			white-space: nowrap;
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

.v-expansion-panel-header {
	background-color: @bg-header;
	color: @text-color;
	cursor: default;
	font-size: 18px !important;
	height: 70px;
	padding: 0 20px;

	.v-btn {
		font-size: 16px;
		text-transform: none !important;
	}

	.v-icon {
		font-size: 20px !important;
	}

}

</style>