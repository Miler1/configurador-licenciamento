<template lang="pug">

#step-atividade-licenciavel-atividades

	v-expansion-panels.pb-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline  Dados básicos da atividade
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content
				v-form(ref="atividadeLicenciavel")
					v-row
						v-col(cols="12", md="3")
							v-label Código
							v-text-field#QA-input-atividade-codigo(
								outlined,
								color="#E0E0E0",
								:placeholder="placeholder",
								@click.native="resetErrorMessage",
								v-model="dados.codigoAtividade",
								:error-messages="errorMessageCodigoAtividade(dados.codigoAtividade)",
								@input="v => {dados.codigoAtividade = v.toUpperCase()}",
								required,
								dense
							)
						v-col(cols="12", md="9")
							v-label Nome
							v-text-field#QA-input-atividade-nome(
								outlined,
								color="#E0E0E0",
								:placeholder="placeholder",
								@click.native="resetErrorMessage",
								v-model="dados.nomeAtividade",
								:error-messages="errorMessageNomeAtividade(dados.nomeAtividade)",
								required,
								dense
							)
					v-row
						v-col(cols="12", md="6")
							v-label Tipologia
							v-autocomplete#QA-select-atividade-licenciavel-tipologia(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="dados.tipologia",
								:items="tipologias",
								:filter="filtroSelect",
								item-text="nome",
								:error-messages="errorMessageTipologia(dados.tipologia)",
								no-data-text="Nenhuma tipologia encontrada",
								@click.native="resetErrorMessage",
								required,
								return-object=true
							)
						v-col(cols="12", md="2")
							v-label Tipos de licenças
							v-autocomplete#QA-select-atividade-licenciavel-licenca(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelectMultiplo",
								item-color="#84A98C",
								v-model="dados.licencas",
								:items="licencas",
								:filter="filtroSelect",
								item-text="sigla",
								:error-messages="errorMessageLicenca(dados.licencas)",
								no-data-text="Nenhum tipo de licença encontrado",
								@click.native="resetErrorMessage",
								required,
								multiple=true,
								return-object=true,
								chips=false,
								deletable-chips=true,
							)
								template(v-slot:selection="data")
									v-chip(
										:key="JSON.stringify(data.item)"
										v-bind="data.attrs"
										:input-value="data.selected"
										:disabled="data.disabled"
										close=true
										@click:close="data.parent.selectItem(data.item)"
									)
										| {{ data.item.sigla }}
						v-col(cols="12", md="2")
							v-label Gerência / Setor
							v-autocomplete#QA-select-taxa-licenciamento-gerencia-setor(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="dados.setor",
								:items="setores",
								:filter="filtroSelect"
								item-text="sigla",
								:error-messages="errorMessageSetor( dados.setor, true )",
								@click.native="resetErrorMessage",
								required,
								return-object=true,
								no-data-text="Nenhuma Gerência / Setor encontrado"
							)
						v-col(cols="12", md="2")
							v-label PPD
							v-autocomplete#QA-select-taxa-licenciamento-potencial-poluidor(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="dados.potencialPoluidor",
								:items="potenciaispoluidores",
								:filter="filtroSelect"
								item-text="nome",
								:error-messages="errorMessagePotencialPoluidor( dados.potencialPoluidor, true )",
								@click.native="resetErrorMessage",
								required,
								return-object=true,
								no-data-text="Nenhum PPD encontrado"
							)
					v-row
						v-col(cols="12", md="4")
							div
								v-label Localização
							div
								v-checkbox.mt-0.mr-8.d-inline-flex(
									v-model="dados.tiposAtividade",
									label="Zona urbana",
									value="URBANA",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessageLocalizacao(dados.tiposAtividade)"
								)
								v-checkbox.mt-0.d-inline-flex(
									v-model="dados.tiposAtividade",
									label="Zona rural",
									value="RURAL",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessageLocalizacao(dados.tiposAtividade)",
									:hide-details="setDetails(dados.tiposAtividade, 0)"
								)
						v-col(cols="12", md="4")
							ToggleOptions(
								ref="toggleOptionsForaEmpreendimento",
								labelOption="Atividade fora do empreendimento",
								idToggle="QA-btn-toggle-fora-empreendimento",
								:errorMessage="errorMessageForaEmpreendimento",
								:options="optionsForaEmpreendimento",
								@changeOption="dados.foraEmpreendimento = $event",
							)
						v-col(cols="12", md="4")
							div
								v-label Geometria
							div
								v-checkbox.mt-0.mr-8.d-inline-flex(
									v-model="dados.geoPonto",
									label="Ponto",
									value="true",
									color="#84A98C",
									@click="resetErrorMessage",
									v-bind:false-value="false",
	  								v-bind:true-value="true",
									:error-messages="errorMessageGeometria()",
								)
								v-checkbox.mt-0.mr-8.d-inline-flex(
									v-model="dados.geoLinha",
									label="Linha",
									value="true",
									color="#84A98C",
									@click="resetErrorMessage",
									v-bind:false-value="false",
	  								v-bind:true-value="true",
									:error-messages="errorMessageGeometria()",
									:hide-details="setDetails(dados.geoLinha, 0)"
								)
								v-checkbox.mt-0.d-inline-flex(
									v-model="dados.geoPoligono",
									label="Polígono",
									value="true",
									color="#84A98C",
									@click="resetErrorMessage",
									v-bind:false-value="false",
	  								v-bind:true-value="true",
									:error-messages="errorMessageGeometria()",
									:hide-details="setDetails(dados.geoPoligono, 0)"
								)
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline Adição de CNAE
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content
				v-form(ref="atividadeLicenciavelCnae")
					v-row
						v-col(cols="12", md="12")
							v-label CNAE
							v-autocomplete#QA-select-atividade-licenciavel-cnae(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelectMultiplo",
								item-color="#84A98C",
								v-model="dados.cnaes",
								:items="cnaes",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessageCnae(cnaesAtividade)",
								no-data-text="Nenhum CNAE encontrado",
								@click.native="resetErrorMessageCnae",
								required,
								multiple=true,
								return-object=true,
								chips=false,
								deletable-chips=true,
							)
								template(v-slot:selection="data")
									v-chip(
										:key="JSON.stringify(data.item)"
										v-bind="data.attrs"
										:input-value="data.selected"
										:disabled="data.disabled"
										close=true
										@click:close="data.parent.selectItem(data.item)"
									)
										| {{ data.item.codigo }}
					v-row
						v-col(cols="12", md="12")
							v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
								a#QA-limpar-dados-requisito-tecnico.d-flex.flex-row.align-center.justify-end(@click="clearCnae")
									v-icon.pr-1 fa-eraser
									span Limpar dados
								v-btn#QA-btn-adicionar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
									v-icon mdi-plus
									span Adicionar

	GridListagemInclusao.pb-4(
		:tituloListagem="tituloListagem",
		:headers="headerListagem",
		:dadosListagem="cnaesAtividade",
		:excluirItem="excluirItem",
		:labelNoData="labelNoData",
		:placeholderPesquisa="placeholderPesquisa",
		:tituloTooltip="tituloTooltip",
		:labelNoResultset="semResultados",
		:exibirIconeEditar="exibirIconeEditar"
	)

	v-expansion-panels(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline Vinculações
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content
				v-form(ref="atividadeLicenciavelRequisitoTecnicoTaxaLicenciamento")
					v-row
						v-col(cols="12", md="6")
							v-label Grupo de requisito técnico
							v-autocomplete#QA-select-atividade-licenciavel-requisitos-tecnicos(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="dados.requisitoTecnico",
								:items="requisitosTecnicos",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessageRequisito(dados.requisitoTecnico)",
								@input="v => {dados.requisitoTecnico = dados.requisitoTecnico}",
								no-data-text="Nenhuma requisito técnico",
								@click.native="resetErrorMessage",
								required,
								return-object=true
							)
						v-col(cols="12", md="6")
							v-label Tabela de taxas de licenciamento
							v-autocomplete#QA-select-atividade-licenciavel-taxas-licenciamento(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="dados.taxaLicenciamento",
								:items="taxasLicenciamento",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessageTaxaLicenciamento(dados.taxaLicenciamento)",
								@input="v => {dados.taxaLicenciamento = dados.taxaLicenciamento}",
								no-data-text="Nenhuma taxa de licenciamento encontrada",
								@click.native="resetErrorMessage",
								required,
								return-object=true
							)

</template>

<script>

import ToggleOptions from "@/components/ToggleOptions";
import GridListagemInclusao from '@/components/GridListagemInclusao';
import tipologiaService from '@/services/tipologia.service';
import LicencaService from '@/services/licenca.service';
import PotencialPoluidorService from '@/services/potencialPoluidor.service';
import atividadeCnaeService from '@/services/atividadeCnae.service';
import RequisitoTecnicoService from '@/services/requisitoTecnico.service';
import TaxaLicenciamentoService from '@/services/taxaLicenciamento.service';
import EntradaUnicaWSService from '@/services/entradaUnica.service';
import { HEADER } from '@/utils/dadosHeader/ListagemAtividadeCnaeInclusaoHeader';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import snackbar from '@/services/snack.service';

export default {

	name:'PassoAtividades',

	components: {
		ToggleOptions,
		GridListagemInclusao
	},

	props: {

		cnaesAtividade: {
			type: [Array]
		},
		dados: {
			type: [Object]
		},
		erro: {
			type: [Object]
		},
		erroCnae: {
			type: [Object]
		},
		erroRascunho: {
			type: [Object]
		}

	},

	data: () => {

		return {
			dadosPanel: {
				panel: [0, 1, 2],
				readonly: true,
			},
			exibirIconeEditar: false,
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			placeholderSelectMultiplo: "Selecione um ou mais",
			placeholderPesquisa:  "Pesquisar pelo código ou nome do CNAE",
			tituloTooltip: "CNAE",
			semResultados: 'Nenhum CNAE encontrado com a pesquisa informada.',
			cnaes: [],
			licencas: [],
			potenciaispoluidores: [],
			gerenciasetores: [],
			tipologias: [],
			requisitosTecnicos: [],
			taxasLicenciamento: [],
			setores: [],
			isInclusao: true,
			tituloListagem: "Listagem de CNAEs adicionados para esta atividade",
			labelNoData: 'Não existem CNAEs adicionados.',
			headerListagem: HEADER,
			errorMessageEmptyInclusao: true,
			indexItemEdicao: null,
			optionsForaEmpreendimento: [
				{
					idOption: "QA-btn-atividade-licenciavel-fora-sim",
					value: "true",
					label: "Sim",
					width: "100px"
				},
				{
					idOption: "QA-btn-atividade-licenciavel-fora-nao",
					value: "false",
					label: "Não",
					width: "100px"
				}
			],
		};
	},

	methods: {

		clearCnae() {

			this.isInclusao = true;
			this.dados.cnaes = [];
			this.resetErrorMessageCnae();
			this.indexItemEdicao = null;

		},

		resetErrorMessage() {

			this.erro.invalido = false;
			this.erroCnae.invalido = false;
			this.erroRascunho.invalido = false;

		},

		resetErrorMessageCnae() {

			this.erroCnae.invalido = false;
			this.errorMessageEmptyInclusao = true;

		},

		checkFormVinculacao() {

			return this.dados.cnaes
				&& this.dados.cnaes.length > 0;

		},

		errorMessageCodigoAtividade(value) {

			if (this.erro.invalido) {

				if (value == null || value === '') {
					return 'Obrigatório';
				}

			}

			if (this.erroRascunho.invalido) {

				if (value == null || value === '') {
					return 'Obrigatório';
				}

			}

		},

		errorMessageNomeAtividade(value) {

			if (this.erro.invalido) {

				if (value == null || value === '') {
					return 'Obrigatório';
				}

			}

			if (this.erroRascunho.invalido) {

				if (value == null || value === '') {
					return 'Obrigatório';
				}

			}

		},

		errorMessageTipologia(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessageLicenca(value) {

			if (this.erro.invalido && value.length === 0) {
				return 'Obrigatório';
			}

		},

		errorMessageSetor(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessagePotencialPoluidor(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessageLocalizacao(value) {

			if (this.erro.invalido && value.length === 0) {
				return 'Obrigatório';
			}

		},

		errorMessageRequisito(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessageTaxaLicenciamento(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessageForaEmpreendimento(value) {

			if (this.erro.invalido && value == null || value === '') {
				return 'Obrigatório';
			}

		},

		errorMessageGeometria() {

			if (this.erro.invalido && !this.dados.geoPonto && !this.dados.geoLinha && !this.dados.geoPoligono) {

				return 'Obrigatório';

			}

		},

		errorMessageCnae(value) {

			if ((!this.errorMessageEmptyInclusao || this.erroCnae.invalido) && value.length === 0) {
				return 'Obrigatório';
			} else if (!this.errorMessageEmptyInclusao) {
				return 'Obrigatório';
			}

		},

		filtroSelect(item, query, itemText) {

			query = this.normalizer(query);
			itemText = itemText ? this.normalizer(itemText) : itemText;

			return itemText.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) > -1;

		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		},

		setDetails(lista, index){
			return lista.length-1 !== index;
		},

		incluirDados() {

			if (this.checkFormVinculacao()) {

				let dadosInclusao = [];
				let dadosExistentes = [];

				if (this.isInclusao) {

					this.dados.cnaes.forEach(cnae => {

						if (!this.validarValoresAdicionados(cnae)) {
							dadosExistentes.push(cnae.codigo);
						}

						dadosInclusao.push(cnae);

					});

					if (dadosExistentes.length === 0) {

						this.cnaesAtividade.push(...dadosInclusao);
						this.clearCnae();

					} else {
						this.erroIncluirCnae(dadosExistentes);
					}

				} else {

					if (!this.validarValoresAdicionados(this.dados.cnaes[0])) {
						dadosExistentes.push(this.valor.licencas[0].sigla);
					}

					if (dadosExistentes.length === 0 ) {

						dadosInclusao = this.dados.cnaes[0];

						this.cnaesAtividade.splice(this.indexItemEdicao, 1, dadosInclusao);

						this.indexItemEdicao = null;
						this.clearCnae();

					} else {
						this.erroIncluirCnae(dadosExistentes);
					}

				}

			} else {
				this.errorMessageEmptyInclusao = false;
			}

		},

		validarValoresAdicionados(cnae) {

			let validacao = true;

			this.cnaesAtividade.forEach((dado, index) => {

				if (dado.id === cnae.id
					&& (this.isInclusao || this.indexItemEdicao != index)) {

					validacao = false;
				}

			});

			return validacao;

		},

		erroIncluirCnae(codigosCnaes) {

			let dadosExistentes = '';

			codigosCnaes.forEach((codigo, index) => {

				dadosExistentes += codigo;

				if (index !== codigosCnaes.length -1) {
					dadosExistentes+= ", ";
				} else {
					dadosExistentes+= ". ";
				}

			});

			let message = ERROR_MESSAGES.atividadeLicenciavel.atividades.adicionarValores + "Já existe um CNAE " +
				"com o mesmo código adicionado: " + dadosExistentes;

			snackbar.alert(message);

		},

		excluirItem(item) {

			this.$fire({

				title:'<p class="title-modal-confirm">Remover CNAE - ' + item.nome,

				html:`<p class="message-modal-confirm">Ao remover o CNAE, ele não estará mais vinculado nessa atividade.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover o CNAE? Esta opção pode ser desfeita a qualquer momento ao adicioná-lo novamente.</b>
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

					let indexItemExclusao = this.cnaesAtividade.indexOf(item);
					this.cnaesAtividade.splice(indexItemExclusao, 1);

				}

			});

		},

	},

	created(){

		tipologiaService.buscarTipologiasAtivas()
			.then((response) => {
				this.tipologias = response.data;
			});

		LicencaService.findAll()
			.then((response) => {
				this.licencas = response.data;
			});

		PotencialPoluidorService.findAll()
			.then((response) => {
				this.potenciaispoluidores = response.data;
			});

		atividadeCnaeService.buscarAtividadesCnaesAtivos()
			.then((response) => {

				this.cnaes = response.data;

				this.cnaes.forEach(cnae => cnae.textoExibicao = cnae.codigo + ' - ' + cnae.nome);

			});

		RequisitoTecnicoService.buscarRequisitosTecnicosAtivos()
			.then((response) => {

				this.requisitosTecnicos = response.data;

				this.requisitosTecnicos.forEach(requisitotecnico => requisitotecnico.textoExibicao = requisitotecnico.codigo + ' - ' + requisitotecnico.descricao);

			});

		TaxaLicenciamentoService.buscarCodigoTaxasLicenciamentoAtivas()
			.then((response) => {

				this.taxasLicenciamento = response.data;

				this.taxasLicenciamento.forEach(taxalicenciamento => taxalicenciamento.textoExibicao = taxalicenciamento.codigo + ' - ' + taxalicenciamento.descricao);
			});

		EntradaUnicaWSService.findSetores()
			.then((response) => {
				this.setores = response.data;
			});

		if (this.$route.params.idAtividadeLicenciavel) {

			this.exibirIconeRemover = false;
			this.isCadastro = false;

		}else{
			this.isCadastro = true;
		}

	},

	mounted() {

		if (this.$route.params.idAtividadeDispensavel) {
			this.isInclusao = false;
		}

		this.$refs.toggleOptionsForaEmpreendimento.setModel(this.dados.foraEmpreendimento);

	}

};

</script>

<style lang="less">

@import "../../../../assets/css/variaveis.less";

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

</style>