<template lang="pug">

#step-atividade-licenciavel-parametros

	.cabecalho.pl-4.mb-7.rounded
		v-row
			v-col(cols="12", md="3")
				v-label Código da atividade
				p.label-atividade 2102
			v-col(cols="12", md="9")
				v-label Atividade
				p.label-atividade Impressão e encadernação de material gráfico em geral
	
	v-expansion-panels.pb-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline Parâmetros da atividade
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content
				v-form(ref="parametroAtividade")
					v-container.pa-0
						v-row
							v-col(cols="12", md="12")
								ToggleOptions(
									ref="toggleAtividadeLicenciavelParametro",
									labelOption="Tipo de parâmetro",
									idToggle="QA-btn-toggle-atividade-licenciavel-parametro",
									:class="{disable:parametros.length > 0}"
									:errorMessage="errorMessage",
									:options="optionsTipoParametro",
									@changeOption="tipoParametro = $event"
									:change="changeOption",
									:disabled="parametros.length > 0 && isInclusao"
								)

						v-row
							v-col(cols="12", :md="isParametroSimples() ? '12': '6'", v-if="isParametroSimples() || isParametroComposto()")
								v-label Parâmetro 1
								v-autocomplete#QA-select-parametro-1(
									outlined,
									dense,
									color="#E0E0E0",
									:placeholder="placeholderSelect1",
									item-color="grey darken-3",
									v-model="parametro1.parametro",
									:items="parametrosDisponiveis",
									:filter="filtroSelect",
									item-text="textoExibicao",
									:error-messages="errorMessage(parametro1.parametro)",,
									:disabled="disableParametros(1)"
									no-data-text="Nenhum parâmetro encontrado",
									@click.native="resetErrorMessage",
									required,
									return-object=true
								)

								v-label Descrição da unidade do parâmetro 1 
									i (Opcional)
								v-tooltip(top, left, max-width=400)
									template(v-slot:activator="{ on, attrs }")
										v-icon.information.ml-1(v-bind="attrs", v-on="on") mdi-information
									span {{labelTooltipDescricao}}
								v-text-field#QA-input-descricao-unidade(
									outlined,
									color="#E0E0E0",
									:placeholder="placeholderDescricao1",
									v-model="parametro1.descricaoUnidade",
									@click.native="resetErrorMessage",
									:disabled="disableParametros(1)"
									required,
									dense
								)

								v-row(v-for='(valor, index) in parametro1.valores', :key='index')
									v-col(cols="12", md="2").align-self-center
										span Intervalo {{index + 1}}

									v-col(cols="12", md="5")
										v-label Valor mínimo
										v-text-field#QA-input-descricao-unidade(
											outlined,
											color="#E0E0E0",
											v-money="money",
											min="0",
											ref="valorminimo",
											v-model.lazy="valor.minimo",
											@click.native="resetErrorMessage",
											:placeholder="validaValoresLimites(index, 'MINIMO') ? '0': ''",
											:error-messages="errorMessage(valor.minimo, null, 1, index, 'MINIMO', false)",
											:disabled="disableParametros(1, index, 'MINIMO')"
											required,
											dense,
											hide-details="auto"
										)
										v-checkbox.mt-0.d-inline-flex(
											v-model="valor.limiteInferiorIncluso",
											label="Limite inferior incluso",
											color="#84A98C",
											@click="changeLimite(1, index, 'MINIMO')",
											:error-messages="errorMessage(valor.limiteInferiorIncluso, null, 1, index, 'MINIMO', true)",
											:disabled="disableParametros(1, index, 'MINIMO')",
											hide-details="auto"
										)


									v-col(cols="12", md="5")
										v-label Valor máximo
										v-text-field#QA-input-descricao-unidade(
											outlined,
											color="#E0E0E0",
											min="0",
											ref="valormaximo",
											v-money="index === 3 ? '' : money"
											v-model.lazy="valor.maximo",
											@click.native="resetErrorMessage",
											:placeholder="validaValoresLimites(index, 'MAXIMO') ? 'Indeterminado': ''",
											:error-messages="errorMessage(valor.maximo, null, 1, index, 'MAXIMO', false)",
											:disabled="disableParametros(1, index, 'MAXIMO')"
											required,
											dense,
											hide-details="auto"
										)
										v-checkbox.mt-0.d-inline-flex(
											v-model="valor.limiteSuperiorIncluso",
											label="Limite superior incluso",
											color="#84A98C",
											@click="changeLimite(1, index, 'MAXIMO')",
											:error-messages="errorMessage(valor.limiteSuperiorIncluso, null, 1, index, 'MAXIMO', true)",
											:disabled="disableParametros(1, index, 'MAXIMO')",
											hide-details="auto"
										)

							v-col(cols="12", md="6", v-if='isParametroComposto()')
								v-label Parâmetro 2
								v-autocomplete#QA-select-parametro-2(
									outlined,
									dense,
									color="#E0E0E0",
									:placeholder="placeholderSelect2",
									item-color="grey darken-3",
									v-model="parametro2.parametro",
									:items="parametrosDisponiveis",
									:filter="filtroSelect",
									item-text="textoExibicao",
									:error-messages="errorMessage(parametro2.parametro)",
									no-data-text="Nenhum parâmetro encontrado",
									@click.native="resetErrorMessage",
									required,
									return-object=true
									:disabled="disableParametros(2)"
								)

								v-label Descrição da unidade do parâmetro 2 
									i (Opcional)
								v-tooltip(top, left, max-width=400)
									template(v-slot:activator="{ on, attrs }")
										v-icon.information.ml-1(v-bind="attrs", v-on="on") mdi-information
									span {{labelTooltipDescricao}}
								v-text-field#QA-input-descricao-unidade(
									outlined,
									color="#E0E0E0",
									:placeholder="placeholderDescricao2",
									v-model="parametro2.descricaoUnidade",
									@click.native="resetErrorMessage",
									required,
									dense,
									:disabled="disableParametros(2)"
								)

								v-row(v-for='(valor, index) in parametro2.valores', :key='index')
									v-col(cols="12", md="2").align-self-center
										span Intervalo {{index + 1}}

									v-col(cols="12", md="5")
										v-label Valor mínimo
										v-text-field#QA-input-descricao-unidade(
											outlined,
											color="#E0E0E0",
											v-model.lazy="valor.minimo",
											type="number",
											v-money="money"
											min="0",
											ref="valorminimo",
											@click.native="resetErrorMessage",
											:placeholder="validaValoresLimites(index, 'MINIMO') ? '0': ''",
											:error-messages="errorMessage(valor.minimo, null, 2, index, 'MINIMO', false)",
											:disabled="disableParametros(2, index, 'MINIMO')",
											required,
											dense,
											hide-details="auto"
										)
										v-checkbox.mt-0.d-inline-flex(
											v-model="valor.limiteInferiorIncluso",
											label="Limite inferior incluso",
											color="#84A98C",
											@click="changeLimite(2, index, 'MINIMO')",
											:error-messages="errorMessage(valor.limiteInferiorIncluso, null, 2, index, 'MINIMO', true)",
											:disabled="disableParametros(2, index, 'MINIMO')",
											hide-details="auto"
										)


									v-col(cols="12", md="5")
										v-label Valor máximo
										v-text-field.mb-0#QA-input-descricao-unidade(
											outlined,
											color="#E0E0E0",
											v-model.lazy="valor.maximo",
											type="number",
											v-money="money"
											min="0",
											ref="valormaximo",
											@click.native="resetErrorMessage",
											:placeholder="validaValoresLimites(index, 'MAXIMO') ? 'Indeterminado': ''"
											:error-messages="errorMessage(valor.maximo, null, 2, index, 'MAXIMO', false)",
											:disabled="disableParametros(2, index, 'MAXIMO')",
											required,
											dense,
											hide-details="auto"
										)
										v-checkbox.mt-0.d-inline-flex(
											v-model="valor.limiteSuperiorIncluso",
											label="Limite superior incluso",
											color="#84A98C",
											@click="changeLimite(2, index, 'MAXIMO')",
											:error-messages="errorMessage(valor.limiteSuperiorIncluso, null, 2, index, 'MAXIMO', true)",
											:disabled="disableParametros(2, index, 'MAXIMO')",
											hide-details="auto"
										)

						v-row
							v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
								a#QA-limpar-dados-atividade-dispensavel-cnae.d-flex.flex-row.align-center.justify-end(@click="limparDados", :class="{disabled: parametros.length > 0}")
									v-icon.pr-1 fa-eraser
									span Limpar dados

								v-btn#QA-btn-adicionar-atividade-dispensavel-cnae(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao", :disabled="parametros.length > 0")
									v-icon mdi-plus
									span Adicionar

								v-btn#QA-btn-editar-atividade-dispensavel-cnae(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusao", :disable="parametros.length > 0")
									v-icon mdi-pencil
									span Editar

	GridListagemInclusao(
		:tituloListagem="tituloListagem",
		:headers="headerListagem",
		:dadosListagem="parametros",
		:labelNoData="labelNoData",
		:inputPesquisa="inputPesquisa",
		:hideFooter="hideFooter",
		:itemsPerPage="itemsPerPage",
		:dadosSelect="portesEmpreendimento",
		:errorMessage="errorMessageValido"
	)

	v-container.pa-0.mt-5(v-if="isInclusao")
		v-row
			v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
				v-btn#QA-btn-editar-atividade-dispensavel-cnae(@click="editarParametros", large, outlined, color="#84A98C", v-if="parametros.length > 0")
					v-icon mdi-pencil
					span Editar intervalos

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import ToggleOptions from "@/components/ToggleOptions";
import { HEADER } from '@/utils/dadosHeader/ListagemRelacaoParametroPorteInclusao';
import ParametroService from '@/services/parametro.service';
import PorteEmpreendimento from '@/services/porteEmpreendimento.service';
import { VMoney } from 'v-money';

export default {

	name:'PassoParametros',

	components: {
		GridListagemInclusao,
		ToggleOptions
	},

	directives: {money: VMoney},

	props: {

		parametros: {
			type: [Array]
		},
		erro: {
			type: [Object]
		},

	},
	
	data: () => {

		return {

			dadosPanel: {
				panel: [0],
				readonly: true,
			},
			isInclusao: true,
			tipoParametro: null,
			errorMessageEmpty: true,
			placeholderSelect1: 'Selecione o parâmetro 1',
			placeholderSelect2: 'Selecione o parâmetro 2',
			placeholderDescricao1: 'Ex.: Metros cúbicos/dia (m³/d)',
			placeholderDescricao2: 'Ex.: Produção em toneladas por mês',
			labelTooltipDescricao: 'Esta é a descrição do campo parâmetro que será exibida para o solicitante ao informar o valor.',

			tituloListagem: "Listagem de relações parâmetros / portes adicionadas",
			labelNoData: 'Não existem relações parâmetros / portes adicionadas.',
			inputPesquisa: false,
			hideFooter: true,
			itemsPerPage: 20,
			headerListagem: [... HEADER],

			money: {
				decimal: ',',
				thousands: '.',
				precision: 2
			},

			parametro1: {
				parametro: null,
				descricaoUnidade: null,
				valores: []
			},
			parametro2: {
				parametro: null,
				descricaoUnidade: null,
				valores: []
			},
			parametrosDisponiveis: [],
			portesEmpreendimento: [],
			optionsTipoParametro:[
				{
					idOption: "QA-btn-atividade-licenciavel-parametro-simples",
					value: "SIMPLES",
					label: "Simples",
					width: "130px"
				},
				{
					idOption: "QA-btn-atividade-licenciavel-parametro-composto",
					value: "COMPOSTO",
					label: "Composto",
					width: "130px"
				}
			]

		};

	},

	methods: {

		errorMessage(item, isInclusao, numeroParametro, index, tipo, check) {

			if(this.validaValoresLimites(index, tipo)){
				return '';
			}

			let parametro = null;

			if(numeroParametro === 1) {
				parametro = this.parametro1;
			} else {
				parametro = this.parametro2;
			}

			if(check){
				if(tipo === 'MINIMO'){
					return (this.errorMessageEmpty || (item || parametro.valores[index-1].limiteSuperiorIncluso)) ? '' : 'Obrigatório';
				}
				if(tipo === 'MAXIMO'){
					return (this.errorMessageEmpty || (item || parametro.valores[index+1].limiteInferiorIncluso)) ? '' : 'Obrigatório';
				}
			} else {

				if(!this.errorMessageEmpty && item === '0,00'){
					return 'Obrigatório';
				}
				if(tipo === 'MINIMO' && item){
					return (this.errorMessageEmpty || (item && (item === parametro.valores[index-1].maximo))) ? '' : 'O valor mínimo no intervalo atual deve ser igual ao valor máximo do intervalo anterior';
				}
				if(tipo === 'MAXIMO' && item){
					return (this.errorMessageEmpty || (item && (item > parametro.valores[index].minimo))) ? '' : 'O valor máximo deve ser maior que o valor mínimo no intervalo atual';
				}
			}

			return (this.errorMessageEmpty || item) ? '' : 'Obrigatório';
		},

		errorMessageValido(item) {
			return (!this.erro.invalido || item) ? '' : 'Obrigatório';
		},

		resetErrorMessage() {
			this.errorMessageEmpty = true;
			this.erro.invalido = false;
		},

		dadosValidos() {

			if(!this.tipoParametro) {
				this.errorMessageEmpty = false;
			}

			if(this.tipoParametro === 'SIMPLES') {
				this.errorMessageEmpty = this.validarParametro(this.parametro1);

			} else {

				this.errorMessageEmpty = this.validarParametro(this.parametro1) && this.validarParametro(this.parametro2);

			}

			return this.errorMessageEmpty;
		},

		validarParametro(parametro) {
			var valido = true;

			valido = !!parametro.parametro;

			for(var i = 0; i < 3; i++) {
				if(!parametro.valores[i].maximo
					|| !parametro.valores[i + 1].minimo
					|| parametro.valores[i].maximo !== parametro.valores[i + 1].minimo
					|| parametro.valores[i].maximo <= parametro.valores[i].minimo
					|| (!parametro.valores[i + 1].limiteInferiorIncluso && !parametro.valores[i].limiteSuperiorIncluso)) {

					valido = false;

				}

			}

			return valido;

		},

		incluirDados() {

			if(!this.dadosValidos()){
				return;
			}

			let parametrosAnterior = [... this.parametros];
			this.parametros.length = 0;

			let dadoParametro = {
				parametro1: null,
				valorMinimoParametro1: null,
				valorMaximoParametro1: null,
				limiteInferiorInclusoParametro1: null,
				limiteSuperiorInclusoParametro1: null,
				parametro2: null,
				valorMinimoParametro2: null,
				valorMaximoParametro2: null,
				limiteInferiorInclusoParametro2: null,
				limiteSuperiorInclusoParametro2: null,
				porte: null,
				licenciamentoMunicipal: null,
				repasseOutroOrgao: null,
				descricaoUnidade1: null,
				descricaoUnidade2: null
			};

			if(this.isParametroSimples()) {
				
				this.parametro1.valores.forEach((valor, index) => {

					dadoParametro.parametro1 = this.parametro1.parametro;
					dadoParametro.descricaoUnidade1 = this.parametro1.descricaoUnidade;
					dadoParametro.valorMinimoParametro1 = valor.minimo;
					dadoParametro.valorMaximoParametro1 = valor.maximo;
					dadoParametro.limiteInferiorInclusoParametro1 = valor.limiteInferiorIncluso;
					dadoParametro.limiteSuperiorInclusoParametro1 = valor.limiteSuperiorIncluso;

					if(!this.isInclusao && parametrosAnterior.length === 4) {

						dadoParametro.porte = parametrosAnterior[index].porte;
						dadoParametro.licenciamentoMunicipal = parametrosAnterior[index].licenciamentoMunicipal;
						dadoParametro.repasseOutroOrgao = parametrosAnterior[index].repasseOutroOrgao;

					}

					this.parametros.push({... dadoParametro});

				});

				this.headerListagem = [... HEADER];
				this.headerListagem.splice(3,3);

			} else {

				this.parametro1.valores.forEach((valor, index1) => {

					dadoParametro.parametro1 = this.parametro1.parametro;
					dadoParametro.descricaoUnidade1 = this.parametro1.descricaoUnidade;
					dadoParametro.valorMinimoParametro1 = valor.minimo;
					dadoParametro.valorMaximoParametro1 = valor.maximo;
					dadoParametro.limiteInferiorInclusoParametro1 = valor.limiteInferiorIncluso;
					dadoParametro.limiteSuperiorInclusoParametro1 = valor.limiteSuperiorIncluso;

					this.parametro2.valores.forEach((valor, index2) => {

						dadoParametro.parametro2 = this.parametro2.parametro;
						dadoParametro.descricaoUnidade2 = this.parametro2.descricaoUnidade;
						dadoParametro.valorMinimoParametro2 = valor.minimo;
						dadoParametro.valorMaximoParametro2 = valor.maximo;
						dadoParametro.limiteInferiorInclusoParametro2 = valor.limiteInferiorIncluso;
						dadoParametro.limiteSuperiorInclusoParametro2 = valor.limiteSuperiorIncluso;

						if(!this.isInclusao && parametrosAnterior.length === 16) {

							dadoParametro.porte = parametrosAnterior[(index1*4) + index2].porte;
							dadoParametro.licenciamentoMunicipal = parametrosAnterior[(index1*4) + index2].licenciamentoMunicipal;
							dadoParametro.repasseOutroOrgao = parametrosAnterior[(index1*4) + index2].repasseOutroOrgao;

						}

						this.parametros.push({... dadoParametro});

					});

				});

				this.headerListagem = [... HEADER];

			}

			this.isInclusao = true;
		
		},

		editarParametros() {
			this.isInclusao = false;
			window.scrollTo(0,0);
		},

		filtroSelect(item, query, itemText) {

			query = this.normalizer(query);
			itemText = itemText ? this.normalizer(itemText) : itemText;

			return itemText.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) > -1;

		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		},

		isParametroSimples() {
			return this.tipoParametro === 'SIMPLES';
		},

		isParametroComposto() {
			return this.tipoParametro === 'COMPOSTO';
		},

		validaValoresLimites(index, tipo) {
			return (tipo === 'MINIMO' && index === 0) || (tipo === 'MAXIMO' && index === 3);
		},

		disableParametros(numeroParametro, index, tipo) {

			if(this.parametros.length > 0 && this.isInclusao) {
				return true;
			}

			if(this.validaValoresLimites(index, tipo)) {
				return true;
			}

			if(this.tipoParametro) {
				return numeroParametro === 2 && this.isParametroSimples();
			}

			return true;
		},

		limparDados() {
			this.$refs.toggleAtividadeLicenciavelParametro.clearModel();
			if(this.parametros.length === 0) {
				this.clearParametro(this.parametro1);
				this.clearParametro(this.parametro2);
			}
		},

		changeOption(tipoParametro) {
			if(tipoParametro) {
				if(tipoParametro === 'SIMPLES'){
					this.clearParametro(this.parametro2);
				}
			} else {
				this.clearParametro(this.parametro1);
				this.clearParametro(this.parametro2);
			}

			this.resetErrorMessage();
		},

		clearParametro(parametro) {
			parametro.parametro = null;
			parametro.descricaoUnidade = null;
			parametro.valores.forEach((valor, index) => this.resetaDadosValores(valor, index));
		},

		resetaDadosValores(valor, i) {

			if (this.$refs.valorminimo != undefined || this.$refs.valormaximo != undefined) {
				this.$refs.valorminimo[i].$el.querySelector('input').value = 0;
				this.$refs.valormaximo[i].$el.querySelector('input').value = 0;
			}

			valor.minimo = null;
			valor.maximo = null;
			valor.limiteInferiorIncluso = null;
			valor.limiteSuperiorIncluso = null;
		},

		changeLimite(numeroParametro, index, tipo) {
			if(numeroParametro === 1) {

				if(tipo === 'MINIMO') {
					this.parametro1.valores[index-1].limiteSuperiorIncluso = false;
				} else {
					this.parametro1.valores[index+1].limiteInferiorIncluso = false;
				}

			} else {

				if(tipo === 'MINIMO') {
					this.parametro2.valores[index-1].limiteSuperiorIncluso = false;
				} else {
					this.parametro2.valores[index+1].limiteInferiorIncluso = false;
				}

			}

			this.resetErrorMessage();
		}

	},

	created(){

		ParametroService.findAtivos()
			.then((response) => {

				this.parametrosDisponiveis = response.data;
				this.parametrosDisponiveis.forEach(parametro => parametro.textoExibicao = parametro.codigo + ' - ' + parametro.nome);

			});

		PorteEmpreendimento.findAll()
			.then((response) => {

				this.portesEmpreendimento = response.data;

			});

	},

	mounted() {

		if(this.parametros.length !== 0) {

			if(this.parametros.length === 4) {

				this.$refs.toggleAtividadeLicenciavelParametro.setModel(this.optionsTipoParametro[0].value);
				this.tipoParametro = this.optionsTipoParametro[0].value;

				this.parametro1.parametro = this.parametros[0].parametro1;
				this.parametro1.descricaoUnidade = this.parametros[0].descricaoUnidade1; 

				let valor = {
					minimo: null,
					maximo: null,
					limiteInferiorIncluso: null,
					limiteSuperiorIncluso: null
				};

				this.parametros.forEach((parametro) => {

					this.parametro1.valores.push({
						minimo: parametro.valorMinimoParametro1,
						maximo: parametro.valorMaximoParametro1,
						limiteInferiorIncluso: parametro.limiteInferiorInclusoParametro1,
						limiteSuperiorIncluso: parametro.limiteSuperiorInclusoParametro1
					});

					this.parametro2.valores.push({... valor});

				});
				
				this.headerListagem = [... HEADER];
				this.headerListagem.splice(3,3);

			} else {

				this.$refs.toggleAtividadeLicenciavelParametro.setModel(this.optionsTipoParametro[1].value);
				this.tipoParametro = this.optionsTipoParametro[1].value;

				this.parametro1.parametro = this.parametros[0].parametro1;
				this.parametro1.descricaoUnidade = this.parametros[0].descricaoUnidade1;
				this.parametro2.parametro = this.parametros[0].parametro2;
				this.parametro2.descricaoUnidade = this.parametros[0].descricaoUnidade2;

				this.parametros.forEach((parametro, index) => {

					if(index % 4 === 0) {

						this.parametro1.valores.push({
							minimo: parametro.valorMinimoParametro1,
							maximo: parametro.valorMaximoParametro1,
							limiteInferiorIncluso: parametro.limiteInferiorInclusoParametro1,
							limiteSuperiorIncluso: parametro.limiteSuperiorInclusoParametro1
						});

					}

					if(index > 11) {

						this.parametro2.valores.push({
							minimo: parametro.valorMinimoParametro2,
							maximo: parametro.valorMaximoParametro2,
							limiteInferiorIncluso: parametro.limiteInferiorInclusoParametro2,
							limiteSuperiorIncluso: parametro.limiteSuperiorInclusoParametro2
						});

					}

				});

			}

		} else {

			let valor = {
				minimo: null,
				maximo: null,
				limiteInferiorIncluso: null,
				limiteSuperiorIncluso: null
			};

			for(var i = 0; i < 4; i++) {
				this.parametro1.valores.push({... valor});
				this.parametro2.valores.push({... valor});
			}

		}

	}

};

</script>

<style lang="less">

@import "../../../../assets/css/variaveis.less";

.cabecalho{
	border: 1px solid @border-components;

	.label-atividade {
		margin: 0;
	}
}

.border-bottom{
	border-bottom: 1px solid @border-components;
}

.border-right{
	border-right: 1px solid @border-components;
}

.align-self-center{
	align-self: center;
}

.information {
	font-size: 16px !important;
}

.v-input--is-disabled{
	pointer-events: auto !important;

	.v-input__slot{
		cursor: not-allowed !important;
	}

	input{
		cursor: not-allowed !important;
	}

	.v-input--selection-controls__input{
		cursor: not-allowed !important;
	}

	.v-input--selection-controls__ripple{
		cursor: not-allowed !important;
	}

	.v-label--is-disabled{
		cursor: not-allowed !important;
	}
}

.v-text-field--is-booted{
	margin: 0;
	padding: 0;
}

.disabled {
	cursor: not-allowed !important;

	i {
		color: gray !important;
	}

	span {
		color: gray !important;
	}
}
.disable{

	#QA-btn-toggle-atividade-licenciavel-parametro{
		cursor: not-allowed !important;
	}

}

</style>