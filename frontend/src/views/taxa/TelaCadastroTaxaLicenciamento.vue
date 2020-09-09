<template lang="pug">
  
#tela-cadastro-taxa-licenciamento
	v-container
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Tabela de taxa de licenciamento
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
						span.align-baseline Valores
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
										item-text="nome",
										:error-messages="errorMessage( valor.porteEmpreendimento, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true
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
										item-text="nome",
										:error-messages="errorMessage( valor.potencialPoluidor, true )",
										@click.native="resetErrorMessage",
										required,
										return-object=true
									)
								v-col(cols="12", md="4")
									v-label Tipos de licenças
									v-autocomplete#QA-select-taxa-licenciamento-licenca(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelectLicenca",
										item-color="grey darken-3",
										v-model="valor.licencas",
										:items="licencas",
										item-text="sigla",
										:error-messages="errorMessage( valor.licencas, true )",
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
									v-col.pa-0
										v-label Tipo de taxa
									v-col.pa-0.mb-1
										v-btn-toggle#QA-btn-toggle-taxa-licenciamento(
												v-model="tipoTaxa",
												color="green lighten-4",
												@change="alterarTipoTaxa()"
											)
											v-btn#QA-btn-taxa-licenciamento-fixo(
												color="white",
												value="fixo",
												width="140px",
											) 
												span Valor fixo
											v-btn#QA-btn-taxa-licenciamento-formula(
												color="white",
												value="formula",
												width="140px",
											) 
												span Fórmula
											v-btn#QA-btn-taxa-licenciamento-isento(
												color="white",
												value="isento",
												width="140px",
											) 
												span Isento
									v-col.d-flex.pa-0
										span.v-messages.theme--light.error--text.v-messages__message.pl-3.mb-3 
											| {{ errorMessage(tipoTaxa, true) }}
							
							v-row(v-if="tipoTaxa === 'fixo'")
								v-col(cols="12", md="3")
									v-label Valor
									v-text-field#QA-input-taxa-licenciamento-valor-fixo(
										v-money="money"
										outlined,
										color="#E0E0E0",
										:placeholder="placeholder",
										v-model="valor.valor",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( valor.valor, true )",
										required,
										dense
									)

							v-row(v-if="tipoTaxa === 'formula'")
								v-col(cols="12", md="3")
									v-label Equação da fórmula
									v-text-field#QA-input-taxa-licenciamento-valor-formula(
										outlined,
										color="#E0E0E0",
										placeholder="Ex.: 1252.58 + ( 0.4 * AU )",
										v-model="valor.formula",
										@click.native="resetErrorMessage",
										:error-messages="errorMessage( valor.formula, true )",
										required,
										dense,
										ref="formula"
									)
								v-col(cols="12", md="3")
									v-label Parâmetro
									v-autocomplete#QA-select-taxa-licenciamento-licenca(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										:items="parametros",
										item-text="codigo",
										v-model="searchResult",
										:search-input.sync="searchInput"
										@change="adicionarParamentro"
									)
								v-col(cols="12", md="6")
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
										v-icon mdi-delete
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
			:placeholderPesquisa="placeholderPesquisa"
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

export default {

	name:'TelaCadastroTaxaLicenciamento',

	components: {
		GridListagemInclusao
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
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			placeholderSelectLicenca: "Selecione um ou mais",
			portesEmpreendimento: [],
			potenciaispoluidores: [],
			licencas: [],
			parametros: [],
			errorMessageEmpty: true,
			errorMessageEmptyInclusao: true,
			tituloListagem: "Listagem de taxas adicionados para esta tabela",
			headerListagem: HEADER,
			dadosListagem: [],
			labelBotaoCadastrarEditar: 'Cadastrar',
			iconBotaoCadastrarEditar: 'mdi-plus',
			isCadastro: true,
			isInclusao: true,
			indexItemEdicao: null,
			allowRedirect: true,
			tipoTaxa: null,
			labelNoData: 'Não existem taxas adicionados.',
			placeholderPesquisa: "Pesquisar pelo PPD, porte ou tipo de licença",
			searchResult: null,
			searchInput: ''
		};
	},

	methods: {

		resetErrorMessage() {
			this.errorMessageEmpty = true;
			this.errorMessageEmptyInclusao = true;
		},

		errorMessage(value, isVinculacao) {

			if(isVinculacao) {

				if((!this.errorMessageEmpty || !this.errorMessageEmptyInclusao) && value === 'R$ 0,00') {
					return 'Obrigatório';
				}

				if (!this.isInclusao && Array.isArray(value)) {
					return 'Este campo não permite ser editado';
				}

				else if(!this.errorMessageEmptyInclusao && !value) {
					return 'Obrigatório';
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
			this.tipoTaxa = null;
			this.isInclusao = true;
			this.resetErrorMessage();
		},

		incluirDados() {

			if(this.checkFormVinculacao()){

				var dadoListagem = {};

				if(this.isInclusao) {

					this.valor.licencas.forEach(licenca => {

						dadoListagem = this.getDadosItem(licenca);

						this.dadosListagem.push(dadoListagem);
						dadoListagem = {};
				
					});

				} else {

					dadoListagem = this.getDadosItem(this.valor.licencas[0]);

					this.dadosListagem.splice(this.indexItemEdicao, 1, dadoListagem);
					dadoListagem = {};
					this.indexItemEdicao = null;
					this.isInclusao = true;

				}

				this.clearTaxaLicenciamento();

			} else {
				this.errorMessageEmptyInclusao = false;
			}

		},

		getDadosItem(licenca) {

			var dadoListagem = {};

			dadoListagem.porteEmpreendimento = this.valor.porteEmpreendimento;
			dadoListagem.potencialPoluidor = this.valor.potencialPoluidor;
			dadoListagem.licenca = licenca;

			if(this.tipoTaxa === 'fixo') {

				dadoListagem.valor = this.valor.valor.replace(/R\$\s|\./g, '').replace(',', '.');

			} else if(this.tipoTaxa === 'formula') {

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
			}

		},

		cadastrar() {

			TaxaLicenciamentoService.cadastrar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess();
				})
				.catch(error => {
					this.handleError(error);
				});

		},

		editar() {

			TaxaLicenciamentoService.editar(this.preparaPraSalvar())

				.then(() => {
					this.handleSuccess(true);
				})
				.catch(error => {
					this.handleError(error, true);
				});

		},

		preparaPraSalvar() {
			
			this.taxaLicenciamento.listTaxasLicenciamento = [];
			var dadoListagem = {};

			this.dadosListagem.forEach(dado => {

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

		redirectListagem(allowed = true) {

			this.allowRedirect = allowed;
			this.$router.push({name: 'TaxaLicenciamento'});

		},

		checkForm() {

			return this.taxaLicenciamento.codigo
				&& this.taxaLicenciamento.codigo != ''
				&& this.taxaLicenciamento.descricao
				&& this.taxaLicenciamento.descricao != ''
				&& this.dadosListagem
				&& this.dadosListagem.length > 0;

		},

		checkFormVinculacao() {

			if(this.tipoTaxa) {

				var tipoTaxaValido = this.tipoTaxa === 'formula' ? (this.valor.formula && this.valor.formula != '') : (this.valor.valor && this.valor.valor != 'R$ 0,00');

				return this.valor.porteEmpreendimento
					&& this.valor.potencialPoluidor
					&& this.valor.licencas
					&& this.valor.licencas.length > 0
					&& tipoTaxaValido;

			}

			return false;
		},

		cancelar(){
			this.redirectListagem(false);
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

		editarItem(item) {

			window.scrollTo(0,0);
			this.valor.porteEmpreendimento = item.porteEmpreendimento;
			this.valor.potencialPoluidor = item.potencialPoluidor;
			this.valor.licencas = [];
			this.valor.licencas.push(item.licenca);

			const regex = /[^0-9\.]/;

			if( regex.test(item.valor)) {

				this.tipoTaxa = 'formula';
				this.valor.formula = item.valor;

			} else if (item.valor === '0.0') {

				this.tipoTaxa = 'isento';
				this.valor.valor = '0.0';

			} else {

				this.tipoTaxa = 'fixo';
				this.valor.valor = item.valor;
			}

			this.indexItemEdicao = this.dadosListagem.indexOf(item);
			this.isInclusao = false;

		},

		excluirItem(item) {

			this.$fire({
				title:'<p class="title-modal-confirm">Remover documento - ' + item.documento.nome + '</p>',

				html:`<p class="message-modal-confirm">Ao remover o documento, ele não estará mais vinculado nesse grupo.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover o documento? Esta opção pode ser desfeita a qualquer momento ao adicioná-lo novamente.</b>
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

				if(result.value) {	

					var list = [];

					this.dadosListagem = this.dadosListagem.filter(
						dado => dado.documento.nome != item.documento.nome || dado.licenca.sigla != item.licenca.sigla
					);
				}

			});		
		},

		alterarTipoTaxa() {
			this.valor.valor = null;
			this.valor.formula = null;
			this.parametroSelecionado = null;

			if(this.tipoTaxa === 'isento') {
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

		AdicionaOperadorFormula(operador){

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
			this.dadosListagem = requisito.taxasLicenciamento;

		},

	},

	created(){

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

		if(this.$route.params.idTaxaLicenciamento) {

			this.labelBotaoCadastrarEditar = "Editar";
			this.iconBotaoCadastrarEditar = "mdi-pencil";
			this.isCadastro = false;

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

		if(!this.allowRedirect){
			this.confirmarCancelamento(next);
		} else {
			next();
		}

	}

};

</script>

<style lang="less">

@import "../../assets/css/variaveis.less";

.v-expansion-panel-header {
	background-color: @bg-header;
	color: @text-color;
	cursor: default;
	font-size: 21px;
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

.v-text-field, .v-checkbox {

	fieldset {
		border: 1px solid @border-components;
		border-radius: 2px;
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

.theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn.v-btn--active {
	border-color: @green-primary !important;
	border-left-width: 1px !important;

	span {
		color: @green-primary !important;
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

</style>