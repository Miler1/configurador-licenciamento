<template lang="pug">
  
#tela-cadastro-taxa-licenciamento
	div.pb-7
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Tabela de taxas de licenciamento
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="taxaLicenciamento")
						v-container.pa-0
							v-row
								v-col(cols="12", md="3")
									v-label Código
									v-text-field#QA-input-taxa-licenciamento-codigo(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="taxaLicenciamento.codigo",
										@click.native="resetErrorMessage",
										@input="v => {taxaLicenciamento.codigo = v.toUpperCase()}",
										:error-messages="errorMessage( taxaLicenciamento.codigo, false )",
										required,
										dense
									)
								v-col(cols="12", md="9")
									v-label Descrição
									v-text-field#QA-input-taxa-licenciamento-descricao(
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="taxaLicenciamento.descricao",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( taxaLicenciamento.descricao, false )",
										required,
										dense
									)

			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline {{ isInclusao ? 'Adição de ' : 'Editar ' }} taxa de licenciamento
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="taxaLicenciamentolist")
						v-container.pa-0
							v-row
								v-col(cols="12", md="4")
									v-label Porte do empreendimento
									v-autocomplete#QA-select-taxa-licenciamento-porte-mpreendimento(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="valor.porteEmpreendimento",
										:items="portesEmpreendimento",
										:filter="searchInput"
										item-text="nome",
										:error-messages="errorMessage( valor.porteEmpreendimento, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true,
										no-data-text="Nenhum porte do empreendimento encontrado"
									)
								v-col(cols="12", md="4")
									v-label PPD
									v-autocomplete#QA-select-taxa-licenciamento-potencial-poluidor(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="valor.potencialPoluidor",
										:items="potenciaispoluidores",
										:filter="searchInput",
										item-text="nome",
										:error-messages="errorMessage( valor.potencialPoluidor, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true,
										no-data-text="Nenhum PPD encontrado"
									)
								v-col(cols="12", md="4")
									v-label Tipos de licenças
									v-autocomplete#QA-select-taxa-licenciamento-licenca(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelectLicenca",
										item-color="#84A98C",
										v-model="valor.licencas",
										:items="licencas",
										:filter="searchInput",
										item-text="sigla",
										:error-messages="errorMessage( valor.licencas, true )",
										no-data-text="Nenhum tipo de licença encontrado",
										@click.native="resetErrorMessage",
										required,
										return-object=true,
										multiple=true
										chips=true,
										deletable-chips=true
										:disabled="!isInclusao"
									)

							v-row
								v-col.d-flex.flex-column(cols="12", md="5")

									ToggleOptions(
										ref="toggleOptionsTipoTaxa",
										labelOption="Tipo de taxa",
										idToggle="QA-btn-toggle-taxa-licenciamento",
										:errorMessage="errorMessage",
										:options="optionsTipoTaxa",
										@changeOption="tipoTaxa = $event"
									)
							
							v-row.borda-campo(v-show="tipoTaxa === 'fixo'")
								v-col(cols="12", md="3")
									v-label Valor
									v-text-field#QA-input-taxa-licenciamento-valor-fixo.large-error-line(
										v-money="money"
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model.lazy="valor.valor",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( valor.valor, true )",
										required,
										dense
									)

							v-row.borda-campo(v-show="tipoTaxa === 'formula'")
								v-col(cols="12", md="4")
									v-label Equação da fórmula
										v-tooltip(top, left, max-width=400)
											template(v-slot:activator="{ on, attrs }")
												v-icon.information.ml-1.mb-1(v-bind="attrs", v-on="on") mdi-information
											span Utilize apenas ponto para separar os números monetários e decimais.
									v-text-field#QA-input-taxa-licenciamento-valor-formula(
										outlined,
										color="#E0E0E0",
										placeholder="Ex.: 1252.50 + (0.4 * AU) + 100",
										v-model.lazy="valor.formula",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( valor.formula, true, true)",
										required,
										dense,
										ref="formula"
									)
								v-col(cols="12", md="4")
									v-label Parâmetro
									v-autocomplete#QA-select-taxa-licenciamento-parametro(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										:items="parametros",
										:filter="searchInput",
										item-text="codigo",
										v-model="searchResult",
										:search-input.sync="searchInput"
										@change="adicionarParamentro",
										no-data-text="Nenhum parâmetro encontrado"
									)
								v-col(cols="12", md="4")
									v-label Operadores
									div
										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn.mr-2(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula('+')")
													v-icon mdi-plus
											span Adicionar [+]

										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn.mr-2(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula('-')")
													v-icon mdi-minus
											span Subtrair [-]

										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn.mr-2(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula('*')")
													v-icon mdi-close
											span Multiplicar [*]

										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn.mr-2(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula('/')")
													v-icon mdi-division
											span Dividir [/]

										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn.mr-2(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula('(')")
													h3 (
											span Início do grupo [(]

										v-tooltip(bottom)
											template(v-slot:activator="{ on, attrs }")
												v-btn(outlined, fab, small, v-on='on', @click="AdicionaOperadorFormula(')')")
													h3 )
											span Fim do grupo [)]
								
							v-row
								v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
									a#QA-limpar-dados-taxa-licenciamento.d-flex.flex-row.align-center.justify-end(@click="clearTaxaLicenciamento")
										v-icon.pr-1 fa-eraser
										span Limpar dados
								
									v-btn#QA-btn-adicionar-taxa-licenciamento(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
										v-icon mdi-plus
										span Adicionar
								
									v-btn#QA-btn-editar-taxa-licenciamento(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusao")
										v-icon mdi-pencil
										span Editar

		GridListagemInclusao.px-7(
			:tituloListagem="tituloListagem",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:editarItem="editarItem",
			:excluirItem="excluirItem",
			:labelNoData="labelNoData",
			:placeholderPesquisa="placeholderPesquisa",
			:tituloTooltip="tituloTooltip",
			:labelNoResultset="semResultados"
		)

		v-row.pt-5.px-7#row-justificativa(v-show="!isCadastro")
			v-col.py-0(cols="12")
				v-label Justificativa
				v-textarea#QA-input-taxa-licenciamento-justificativa(
					outlined,
					color="#E0E0E0",
					rows="3",
					auto-grow
					v-model="justificativa",
					:error-messages="errorMessage(justificativa, false, false)",
					@click.native="resetErrorMessage",
					required,
				)

		v-row.pt-6.px-7
			v-col#form-actions.d-flex.justify-space-between(cols="12", md="12")
				v-btn#QA-btn-cancelar-taxa-licenciamento(@click="cancelar", outlined, large, color="#84A98C")
					v-icon mdi-close
					span Cancelar

				v-btn#QA-btn-cadastrar-taxa-licenciamento.btn-cadastrar(@click="submit", large)
					v-icon(color="white") {{iconBotaoCadastrarEditar}}
					span {{labelBotaoCadastrarEditar}}

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import snackbar from '@/services/snack.service';
import { HEADER } from '@/utils/dadosHeader/ListagemTaxaLicenciamentoInclusao';
import { VMoney } from 'v-money';
import LicencaService from '@/services/licenca.service';
import PorteEmpreendimentoService from '@/services/porteEmpreendimento.service';
import PotencialPoluidorService from '@/services/potencialPoluidor.service';
import ParametroService from '@/services/parametro.service';
import TaxaLicenciamentoService from '@/services/taxaLicenciamento.service.js';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import ToggleOptions from "@/components/ToggleOptions";

export default {

	name:'TelaCadastroTaxaLicenciamento',

	components: {
		GridListagemInclusao,
		ToggleOptions
	},

	directives: {money: VMoney},
	
	data: () => {
		return {
			dadosPanel: {
				panel: [0, 1],
				readonly: true,
			},
			taxaLicenciamento: {
				codigo: null,
				descricao: null,
				ativo: true
			},
			valor: {
				porteEmpreendimento: null,
				potencialPoluidor: null,
				licencas: null,
				valor: null,
				formula: null
			},
			money: {
				decimal: ',',
				thousands: '.',
				prefix: 'R$ ',
				precision: 2,
				masked: false
			},
			justificativa: null,
			tituloTooltip: "taxa de licenciamento",
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			placeholderSelectLicenca: "Selecione um ou mais",
			portesEmpreendimento: [],
			potenciaispoluidores: [],
			licencas: [],
			parametros: [],
			errorMessageEmpty: true,
			errorMessageEmptyInclusao: true,
			tituloListagem: "Listagem de taxas de licenciamento adicionadas para esta tabela",
			headerListagem: HEADER,
			dadosListagem: [],
			labelBotaoCadastrarEditar: 'Cadastrar',
			iconBotaoCadastrarEditar: 'mdi-plus',
			isCadastro: true,
			isInclusao: true,
			indexItemEdicao: null,
			allowRedirect: true,
			tipoTaxa: null,
			labelNoData: 'Não existem taxas de licenciamento adicionadas.',
			semResultados: 'Nenhuma taxa de licenciamento encontrada com a pesquisa informada.',
			placeholderPesquisa: "Pesquisar pelo porte, PPD ou tipo de licença",
			searchResult: null,
			searchInput: '',
			optionsTipoTaxa:[
				{
					idOption: "QA-btn-taxa-licenciamento-fixo",
					value: "fixo",
					label: "Valor fixo",
					width: "140px"
				},
				{
					idOption: "QA-btn-taxa-licenciamento-formula",
					value: "formula",
					label: "Fórmula",
					width: "140px"
				},
				{
					idOption: "QA-btn-taxa-licenciamento-isento",
					value: "isento",
					label: "Isento",
					width: "140px"
				}
			]
		};
	},

	methods: {

		resetErrorMessage() {

			this.errorMessageEmpty = true;
			this.errorMessageEmptyInclusao = true;

		},

		errorMessage(value, isVinculacao, validaVirgula) {

			if (isVinculacao) {

				if(typeof value === 'string' && value.substring(0, 2) === "R$"){

					value = value ? parseFloat(value.replace(/R\$\s|\./g, '').replace(',', '.')) : 0.0;

					if( value < 0) {return 'Este campo permite apenas números decimais maiores ou iguais a 0,01.';}

					if (!this.errorMessageEmpty && value === 0.0) { return 'Obrigatório'; }

				}

				if (Array.isArray(value)){

					if (!this.isInclusao) {
						return 'Este campo não permite ser editado';
					} else if (!this.errorMessageEmptyInclusao && value.length === 0) {
						return 'Obrigatório';
					}
				}

				else if (!this.errorMessageEmptyInclusao && !value) {
					return 'Obrigatório';
				}

				if (validaVirgula) {
					return this.errorMessageEmptyInclusao || this.verificaVirgula(value) ? 
						'' : 'Erro! Equação da fórmula inválida. Utilize apenas ponto para separar os números monetários e decimais.';
				}

				return this.errorMessageEmpty || value || (this.dadosListagem.length > 0) ? '' : 'Obrigatório';
			}

			return this.errorMessageEmpty || value ? '' : 'Obrigatório';

		},

		clear() {

			this.taxaLicenciamento.codigo = null;
			this.taxaLicenciamento.descricao = null;
			this.taxaLicenciamento.ativo = true;

			this.clearTaxaLicenciamento();

		},

		clearTaxaLicenciamento() {

			this.valor.porteEmpreendimento = null;
			this.valor.potencialPoluidor = null;
			this.valor.licencas = null;
			this.valor.valor = null;
			this.valor.formula = null;
			this.tipoTaxa = null;
			this.$refs.toggleOptionsTipoTaxa.clearModel();
			this.isInclusao = true;
			this.resetErrorMessage();

			const valorFixo = document.getElementById('QA-input-taxa-licenciamento-valor-fixo');

			if (valorFixo) {
				valorFixo.value = null;
			}

			const valorFormula = document.getElementById('QA-input-taxa-licenciamento-valor-formula');

			if (valorFormula) {
				valorFormula.value = null;
			}

		},

		incluirDados() {

			if (this.checkFormVinculacao()) {

				let dadosInclusao = [];
				let dadosExistentes = [];

				if (this.isInclusao) {

					this.valor.licencas.forEach(licenca => {

						if (!this.validarValoresAdicionados(licenca)) {
							dadosExistentes.push(licenca.sigla);
						}

						dadosInclusao.push(this.getDadosItem(licenca));

					});

					if (dadosExistentes.length === 0) {

						this.dadosListagem.push(...dadosInclusao);
						this.clearTaxaLicenciamento();

					} else {
						this.erroIncluirValoresTaxa(dadosExistentes);
					}

				} else {

					if (!this.validarValoresAdicionados(this.valor.licencas[0])) {
						dadosExistentes.push(this.valor.licencas[0].sigla);
					}

					if (dadosExistentes.length === 0 ) {

						dadosInclusao = this.getDadosItem(this.valor.licencas[0]);

						this.dadosListagem.splice(this.indexItemEdicao, 1, dadosInclusao);

						this.indexItemEdicao = null;
						this.isInclusao = true;
						this.clearTaxaLicenciamento();

					} else {
						this.erroIncluirValoresTaxa(dadosExistentes);
					}

				}

			} else {
				this.errorMessageEmptyInclusao = false;
			}

		},

		validarValoresAdicionados(licenca) {

			let validacao = true;

			this.dadosListagem.forEach(
				(dado, index) => {
					if (dado.potencialPoluidor.codigo === this.valor.potencialPoluidor.codigo 
						&& dado.porteEmpreendimento.codigo === this.valor.porteEmpreendimento.codigo
						&& dado.licenca.sigla === licenca.sigla
						&& (this.isInclusao || this.indexItemEdicao != index)) {

						validacao = false;
					}
				}
			);

			return validacao;

		},

		getDadosItem(licenca) {

			let dadoListagem = {};
			dadoListagem.id = this.valor.id;
			dadoListagem.porteEmpreendimento = this.valor.porteEmpreendimento;
			dadoListagem.potencialPoluidor = this.valor.potencialPoluidor;
			dadoListagem.licenca = licenca;
			dadoListagem.tipoTaxa = this.tipoTaxa;

			if (this.tipoTaxa === 'fixo') {

				dadoListagem.valor = this.valor.valor.replace(/R\$\s|\./g, '').replace(',', '.');

			} else if (this.tipoTaxa === 'formula') {

				dadoListagem.valor = this.valor.formula;

			} else {

				dadoListagem.valor = '0.0';
			}

			return dadoListagem;

		},

		submit() {

			if (this.checkForm()) {

				if(this.isCadastro) {
					this.cadastrar();
				} else {
					this.editar();
				}

			} else {

				this.errorMessageEmpty = false;
				window.scrollTo(0,0);

			}

		},

		cadastrar() {

			TaxaLicenciamentoService.cadastrar(this.prepararParaSalvar())

				.then(() => {
					this.handleSuccess();
				})
				.catch(error => {
					this.handleError(error);
				});

		},

		editar() {
			
			TaxaLicenciamentoService.editar(this.prepararParaSalvar())
				.then(() => {
					this.handleSuccess(true);
				})
				.catch(error => {
					this.handleError(error, true);
				});

		},

		prepararParaSalvar() {

			let dadoListagem = {};

			this.taxaLicenciamento.justificativa = this.justificativa;

			this.taxaLicenciamento.listTaxasLicenciamento = [];

			this.dadosListagem.forEach(dado => {
				
				dadoListagem.id = dado.id;
				dadoListagem.idPorteEmpreendimento = dado.porteEmpreendimento.id;
				dadoListagem.idPotencialPoluidor = dado.potencialPoluidor.id;
				dadoListagem.idTipoLicenca = dado.licenca.id;
				dadoListagem.valor = dado.valor;

				this.taxaLicenciamento.listTaxasLicenciamento.push(dadoListagem);
				dadoListagem = {};

			});

			return this.taxaLicenciamento;

		},

		handleError(error, edicao = false) {

			let message = edicao ? ERROR_MESSAGES.taxaLicenciamento.editar : ERROR_MESSAGES.taxaLicenciamento.cadastro;
			message += error.message;

			snackbar.alert(message);

		},

		handleSuccess(edicao = false) {

			let message = edicao ? SUCCESS_MESSAGES.editar : SUCCESS_MESSAGES.cadastro;

			snackbar.alert(message, snackbar.type.SUCCESS);

			this.clear();
			this.redirectListagem();

		},

		erroIncluirValoresTaxa(licencas) {

			let licencasExistentes = '';

			licencas.forEach((licenca, index) => {

				licencasExistentes+= licenca;

				if (index !== licencas.length -1) {
					licencasExistentes+= ", ";
				} else {
					licencasExistentes+= ". ";
				}

			});

			let message = ERROR_MESSAGES.taxaLicenciamento.adicionarValores + "Já existe uma taxa com a mesma combinação " +
				"para o Porte: " + this.valor.porteEmpreendimento.nome + ", " +
				"PPD: " + this.valor.potencialPoluidor.nome + " e " +
				"Tipo(s) de licença(s): " + licencasExistentes;

			snackbar.alert(message);

		},

		redirectListagem(allowed = true) {

			this.allowRedirect = allowed;
			this.$router.push({name: 'TaxaLicenciamento'});

		},

		checkForm() {

			if (this.isCadastro) {

				return this.taxaLicenciamento.codigo
					&& this.taxaLicenciamento.codigo != ''
					&& this.taxaLicenciamento.descricao
					&& this.taxaLicenciamento.descricao != ''
					&& this.dadosListagem
					&& this.dadosListagem.length > 0;

			}

			return this.taxaLicenciamento.codigo
				&& this.taxaLicenciamento.codigo != ''
				&& this.taxaLicenciamento.descricao
				&& this.taxaLicenciamento.descricao != ''
				&& this.justificativa
				&& this.justificativa != ''
				&& this.dadosListagem
				&& this.dadosListagem.length > 0;

		},

		checkFormVinculacao() {

			if (this.tipoTaxa) {

				let tipoTaxaValido;

				if (this.tipoTaxa === 'isento') {
					tipoTaxaValido = true;
				} else if (this.tipoTaxa === 'formula') {
					tipoTaxaValido = this.valor.formula && this.valor.formula != '' && this.verificaVirgula(this.valor.formula);
				} else {

					let valor = this.valor.valor ? parseFloat(this.valor.valor.replace(/R\$\s|\./g, '').replace(',', '.')) : 0.0;
					tipoTaxaValido = valor > 0;

				}

				return this.valor.porteEmpreendimento
					&& this.valor.potencialPoluidor
					&& this.valor.licencas
					&& this.valor.licencas.length > 0
					&& tipoTaxaValido;

			}

			return false;

		},

		verificaVirgula(formula) {
			return formula ? formula.indexOf(",") === -1 : false;
		},

		cancelar() {
			this.redirectListagem(false);
		},

		confirmarCancelamento(next) {

			this.$fire({

				title: '<p class="title-modal-confirm">Confirmar cancelamento - Tabela de taxas de licenciamento</p>',

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

		editarItem(item) {

			window.scrollTo(0,0);
			this.valor.id = item.id;
			this.valor.porteEmpreendimento = item.porteEmpreendimento;
			this.valor.potencialPoluidor = item.potencialPoluidor;
			this.valor.licencas = [];
			this.valor.licencas.push(item.licenca);

			if (this.isFormula(item.valor)) {

				this.tipoTaxa = 'formula';
				this.valor.formula = item.valor;
				this.valor.valor = null;

				const valorFormula = document.getElementById('QA-input-taxa-licenciamento-valor-formula');

				if (valorFormula) {
					valorFormula.value = item.valor;
				}

			} else if (item.valor === '0.0') {

				this.tipoTaxa = 'isento';
				this.valor.valor = '0.0';

			} else {

				this.tipoTaxa = 'fixo';
				this.valor.valor = item.valor;
				this.valor.formula = null;

				const valorFixo = document.getElementById('QA-input-taxa-licenciamento-valor-fixo');

				if (valorFixo ) {
					valorFixo.value = item.valor;
				}

			}

			this.indexItemEdicao = this.dadosListagem.indexOf(item);
			this.isInclusao = false;

			var that = this;

			setTimeout(function() {

				that.$refs.toggleOptionsTipoTaxa.setModel(that.tipoTaxa);

			}, 100);

		},

		excluirItem(item) {

			let valor = item.valor;

			if (!this.isFormula(valor)) {
				//formatar para exibir no modal
				valor = parseFloat(item.valor) !== 0 ? 'R$ ' + parseFloat(item.valor).toLocaleString('pt-BR', { minimumFractionDigits: 2}) : 'Isento';
			}

			this.$fire({

				title:'<p class="title-modal-confirm">Remover taxa de licenciamento - ' + valor + '</p>',

				html:`<p class="message-modal-confirm">Ao remover a taxa de licenciamento, ela não estará mais vinculada nessa tabela.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover a taxa de licenciamento? Esta opção pode ser desfeita a qualquer momento ao adicioná-la novamente.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor:'#F56C6C',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: '<i class="mdi mdi-delete"></i> Remover' ,
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {
				
				if (result.value) {

					let indexItemExclusao = this.dadosListagem.indexOf(item);
					this.dadosListagem.splice(indexItemExclusao, 1);
					
				}

			});

		},

		isFormula(valor) {

			const regex = /[^0-9\.]/;
			return regex.test(valor);

		},

		alterarTipoTaxa() {

			this.valor.valor = null;
			this.valor.formula = null;
			this.parametroSelecionado = null;

			if (this.tipoTaxa === 'isento') {
				this.valor.valor = '0.0';
			}

		},

		adicionarParamentro(value) {

			this.valor.formula = this.valor.formula ? this.valor.formula + value : value;

			this.$nextTick(() => {

				this.searchInput = '';
				this.searchResult = null;
				this.$refs.formula.focus();

			});

		},

		AdicionaOperadorFormula(operador) {

			this.valor.formula = this.valor.formula ? this.valor.formula + operador : operador;

			this.$nextTick(() => {
				this.$refs.formula.focus();
			});

		},

		prepararDadosParaEdicao(requisito) {

			this.taxaLicenciamento.codigo = requisito.codigo;
			this.taxaLicenciamento.descricao = requisito.descricao;
			this.taxaLicenciamento.ativo = requisito.ativo;
			this.taxaLicenciamento.id = this.$route.params.idTaxaLicenciamento;
			
			this.dadosListagem = [];

			let taxas = {};

			requisito.taxasLicenciamento.forEach( taxa => {

				if (this.isFormula(taxa.valor)) {
					taxa.tipoTaxa = 'formula';
				}

				this.dadosListagem.push(taxa);

			});

		},

		filtroSelect(item, query, itemText) {

			query = this.normalizer(query);
			itemText = itemText ? this.normalizer(itemText) : itemText;

			return itemText.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) > -1;

		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		}

	},

	created() {

		PorteEmpreendimentoService.findAll()
			.then((response) => {
				this.portesEmpreendimento = response.data;
			});
		
		PotencialPoluidorService.findAll()
			.then((response) => {
				this.potenciaispoluidores = response.data;
			});

		LicencaService.findAll()
			.then((response) => {
				this.licencas = response.data;
			});

		ParametroService.findAll()
			.then((response) => {
				this.parametros = response.data;
			});

	},

	mounted() {

		if (this.$route.params.idTaxaLicenciamento) {

			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.isCadastro = false;
			this.justificativa = null;

			TaxaLicenciamentoService.findById(this.$route.params.idTaxaLicenciamento)

				.then((response) => {
					this.prepararDadosParaEdicao(response.data);
				})
				.catch((error) => {
					snackbar.alert(error.message);
				});

		}

		this.allowRedirect = false;

	},

	beforeRouteLeave(to, from, next) {

		if (!this.allowRedirect) {
			this.confirmarCancelamento(next);
		} else {
			next();
		}

	},

};

</script>

<style lang="less">

@import "../../assets/css/variaveis.less";

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

.v-label {
	color: @text-color !important;
}

#row-justificativa .v-label {
	font-size: 16px !important;
}

.v-text-field, .v-checkbox {

	fieldset {
		border: 1px solid @border-components;
		border-radius: 2px;
	}
}

.large-error-line{
	.v-messages__message{
		line-height: initial;
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

.v-input--is-disabled{
	pointer-events: auto !important;

	.v-input__slot{
		cursor: not-allowed !important;
	}
}

.v-autocomplete:not(.v-input--is-focused).v-select--chips input {
	max-height: 100% !important;
}


.div-money {
	border: 1px solid @border-components;
	height: 40px;
}

.borda-campo {
	border: 1px solid #eee;
	border-radius: 4px;
	margin: 0 0 15px 0;
}

.information {
	font-size: 16px !important;
}

</style>