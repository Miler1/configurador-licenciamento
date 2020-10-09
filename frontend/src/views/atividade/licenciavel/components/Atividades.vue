<template lang="pug">

#step-atividade-licenciavel-atividades
	
	v-expansion-panels.pb-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
		v-expansion-panel
			v-expansion-panel-header(v-show="indexItemEdicao != null || isCadastro")
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline  Dados básicos
				template(v-slot:actions)
					v-icon
			v-expansion-panel-content(v-show="indexItemEdicao != null || isCadastro")
				v-form(ref="atividadeLicenciavel")
					v-row
						v-col(cols="12", md="3")
							v-label Código da atividade
							v-text-field#QA-input-atividade-codigo(
								outlined,
								color="#E0E0E0",
								:placeholder="placeholder",
								@click.native="resetErrorMessage",
								v-model="relacaoAtividadeTipologiaCnae.codigoAtividade",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.codigoAtividade)",
								@input="v => {dados.codigoAtividade = relacaoAtividadeTipologiaCnae.codigoAtividade}",
								required,
								dense
							)
						v-col(cols="12", md="9")
							v-label Atividade
							v-text-field#QA-input-atividade-nome(
								outlined,
								color="#E0E0E0",
								:placeholder="placeholder",
								@click.native="resetErrorMessage",
								v-model="relacaoAtividadeTipologiaCnae.nomeAtividade",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.nomeAtividade)",
								@input="v => {dados.nomeAtividade = relacaoAtividadeTipologiaCnae.nomeAtividade}",
								required,
								dense
							)
					v-row
						v-col(cols="12", md="6")
							v-label Topologia
							v-autocomplete#QA-select-atividade-licenciavel-tipologia(
								outlined,
								dense,
								color="#E0E0E0",
								:placeholder="placeholderSelect",
								item-color="grey darken-3",
								v-model="relacaoAtividadeTipologiaCnae.tipologia",
								:items="tipologias",
								:filter="filtroSelect",
								item-text="nome",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.tipologia)",
								@input="v => {dados.tipologia = relacaoAtividadeTipologiaCnae.tipologia}",
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
								:placeholder="placeholderSelectLicenca",
								item-color="#84A98C",
								v-model="relacaoAtividadeTipologiaCnae.licencas",
								:items="licencas",
								:filter="filtroSelect",
								item-text="sigla",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.licencas)",
								@input="v => {dados.licencas = relacaoAtividadeTipologiaCnae.licencas}",
								no-data-text="Nenhuma licença encontrada",
								@click.native="resetErrorMessage",
								required,
								multiple=true,
								return-object=true
								chips=true,
								deletable-chips=true,
								:disabled="!isInclusao",
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
								:filter="filtroSelect"
								item-text="nome",
								:error-messages="errorMessage( relacaoAtividadeTipologiaCnae.setor, true )",
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
								v-model="relacaoAtividadeTipologiaCnae.potencialPoluidor",
								:items="potenciaispoluidores",
								:filter="filtroSelect"
								item-text="nome",
								:error-messages="errorMessage( relacaoAtividadeTipologiaCnae.potencialPoluidor, true )",
								@input="v => {dados.potencialPoluidor = relacaoAtividadeTipologiaCnae.potencialPoluidor}",
								@click.native="resetErrorMessage",
								required,
								return-object=true,
								no-data-text="Nenhum PPD encontrado"
							)
					v-row
						v-col(cols="12", md="4")
							div.mb-2
								v-label Localização da atividade
							div
								v-checkbox.mt-0.mr-8.d-inline-flex(
									label="Zona Urbana",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.localizacaoAtividade)",
									@input="v => {dados.localizacaoAtividade = relacaoAtividadeTipologiaCnae.localizacaoAtividade}",
								)
								v-checkbox.mt-0.d-inline-flex(
									label="Zona Rural",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.localizacaoAtividade)",
									@input="v => {dados.localizacaoAtividade = relacaoAtividadeTipologiaCnae.localizacaoAtividade}",
								)
						v-col(cols="12", md="4")
							ToggleOptions(
								ref="toggleOptionsForaMunicipio",
								labelOption="Atividade fora do empreendimento",
								idToggle="QA-btn-toggle-fora-municipio",
								:errorMessage="errorMessage",
								:options="optionsForaMunicipio",
								@changeOption="relacaoAtividadeTipologiaCnae.foraMunicipio = $event"
								@input="v => {dados.foraMunicipio = relacaoAtividadeTipologiaCnae.foraMunicipio}",
							)
						v-col(cols="12", md="4")
							div.mb-2
								v-label Geometria da atividade
							div
								v-checkbox.mt-0.mr-8.d-inline-flex(
									label="Ponto",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.geometria)",
									@input="v => {dados.geometria = relacaoAtividadeTipologiaCnae.geometria}",
								)
								v-checkbox.mt-0.mr-8.d-inline-flex(
									label="Linha",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.geometria)",
									@input="v => {dados.geometria = relacaoAtividadeTipologiaCnae.geometria}",
								)
								v-checkbox.mt-0.d-inline-flex(
									label="Polígono",
									color="#84A98C",
									@click="resetErrorMessage",
									:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.geometria)"
									@input="v => {dados.geometria = relacaoAtividadeTipologiaCnae.geometria}",
								)
		v-expansion-panel
			v-expansion-panel-header
				div.d-flex.flex-row.align-center.justify-start
					span.align-baseline {{ isInclusao ? 'Adição de ' : 'Editar ' }}  relação Atividade/Cnae
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
								:placeholder="placeholderSelectCnae",
								item-color="#84A98C",
								v-model="relacaoAtividadeTipologiaCnae.cnaes",
								:items="cnaes",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessageCnae(relacaoAtividadeTipologiaCnae.cnaes)",
								no-data-text="Nenhum CNAE encontrado",
								@click.native="resetErrorMessage",
								required,
								multiple=true,
								return-object=true
								chips=true,
								deletable-chips=true,
								:disabled="!isInclusaoCnae"
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
								v-btn#QA-btn-adicionar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusaoCnae")
									v-icon mdi-plus
									span Adicionar
								v-btn#QA-btn-editar-requisito-tecnico(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusaoCnae")
									v-icon mdi-pencil
									span Editar

	GridListagemInclusao.pb-4(
		:tituloListagem="tituloListagem",
		:headers="headerListagem",
		:dadosListagem="cnaesAtividade",
		:editarItem="editarItem",
		:excluirItem="excluirItem",
		:labelNoData="labelNoData",
		:placeholderPesquisa="placeholderPesquisa",
		:tituloTooltip="tituloTooltip",
		:labelNoResultset="semResultados",
		:exibirIconeRemover="exibirIconeRemover"
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
								v-model="relacaoAtividadeTipologiaCnae.requisitosTecnicos",
								:items="requisitosTecnicos",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.requisitosTecnicos)",
								@input="v => {dados.requisitosTecnicos = relacaoAtividadeTipologiaCnae.requisitosTecnicos}",
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
								v-model="relacaoAtividadeTipologiaCnae.taxasLicenciamento",
								:items="taxasLicenciamento",
								:filter="filtroSelect",
								item-text="textoExibicao",
								:error-messages="errorMessage(relacaoAtividadeTipologiaCnae.taxasLicenciamento)",
								@input="v => {dados.taxasLicenciamento = relacaoAtividadeTipologiaCnae.taxasLicenciamento}",
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

	},
	
	data: () => {

		return {
			dadosPanel: {
				panel: [0, 1, 2],
				readonly: true,
			},
			exibirIconeRemover: true,
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			placeholderSelectCnae: "Selecione um ou mais",
			placeholderSelectLicenca: "Selecione um ou mais",
			placeholderPesquisa:  "Pesquisar pelo código ou nome do CNAE",
			tituloTooltip: "relação CNAE / atividade",
			semResultados: 'Nenhuma relação CNAE / atividade encontrada com a pesquisa informada.',
			cnaes: [],
			licencas: [],
			potenciaispoluidores: [],
			gerenciasetores: [],
			tipologias: [],
			requisitosTecnicos: [],
			taxasLicenciamento: [],
			isInclusao: true,
			isInclusaoCnae: true,
			tituloListagem: "Listagem de CNAEs relacionados com a atividade",
			labelNoData: 'Não existem atividades relacionadas.',
			headerListagem: HEADER,
			errorMessageEmptyInclusao: true,
			errorMessageEmptyInclusaoCnae: true,
			indexItemEdicao: null,
			relacaoAtividadeTipologiaCnae: {
				codigoAtividade: null,
				nomeAtividade: null,
				cnaes: [],
				potencialPoluidor: null,
				tipologia: null,
				licencas: [],
				setor: null,
				localizacaoAtividade: null,
				foraMunicipio: null,
				geometria: null,
				requisitosTecnicos: null,
				taxasLicenciamento: null,
			},
			optionsForaMunicipio: [
				{
					idOption: "QA-btn-atividade-licenciavel-fora-sim",
					value: "true",
					label: "Sim",
				},
				{
					idOption: "QA-btn-atividade-licenciavel-fora-nao",
					value: "false",
					label: "Não",
				}
			],
		};
	},

	methods: {

		checkFormVinculacao() {
			console.log(this.relacaoAtividadeTipologiaCnae);
			return this.relacaoAtividadeTipologiaCnae.cnaes
				&& this.relacaoAtividadeTipologiaCnae.cnaes.length > 0;
		},

		filtroSelect(item, query, itemText) {

			query = this.normalizer(query);
			itemText = itemText ? this.normalizer(itemText) : itemText;

			return itemText.toLocaleLowerCase().indexOf(query.toLocaleLowerCase()) > -1;

		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		},

		errorMessage(value) {
			
			if (Array.isArray(value)) {

				if (!this.isInclusao) {
					return 'Este campo não permite ser editado';
				} else if ((!this.errorMessageEmptyInclusao || this.erro.invalido) && value.length === 0) {
					return 'Obrigatório';
				}

			}

			return (this.errorMessageEmptyInclusao && !this.erro.invalido) || value ? '' : 'Obrigatório';

		},

		errorMessageCnae(value) {

			if (Array.isArray(value)) {

				if (!this.isInclusaoCnae) {
					return 'Este campo não permite ser editado';
				} else if ((!this.errorMessageEmptyInclusaoCnae || this.erro.invalido) && value.length === 0) {
					return 'Obrigatório';
				}

			}

			return (this.errorMessageEmptyInclusaoCnae && !this.erro.invalido) || value ? '' : 'Obrigatório';
			
		},

		clearCnae() {

			this.isInclusao = true;
			this.isInclusaoCnae = true;
			this.relacaoAtividadeTipologiaCnae.cnaes = [];
			this.resetErrorMessage();
			this.indexItemEdicao = null;

		},

		resetErrorMessage() {

			this.errorEtapaCnaes = true;
			this.errorMessageEmptyInclusaoCnae = true;
			this.errorMessageEmptyInclusao = true;
			this.erro.invalido = false;
			
		},

		incluirDados() {

			if (this.checkFormVinculacao()) {

				let dadosInclusao = [];
				let dadosExistentes = [];

				if (this.isInclusao) {
					
					this.relacaoAtividadeTipologiaCnae.cnaes.forEach(cnae => {

						if (!this.validarValoresAdicionados(cnae)) {
							dadosExistentes.push(cnae.codigo);
						}

						dadosInclusao.push(this.getDadosItem(cnae));

					});
					
					if (dadosExistentes.length === 0) {
						
						this.cnaesAtividade.push(...dadosInclusao);
						this.clearCnae();

					} else {
						this.erroIncluirCnaeTipologia(dadosExistentes);
					}

				} else {

					if (!this.validarValoresAdicionados(this.relacaoAtividadeTipologiaCnae.cnaes[0])) {
						dadosExistentes.push(this.valor.licencas[0].sigla);
					}

					if (dadosExistentes.length === 0 ) {

						dadosInclusao = this.getDadosItem(this.relacaoAtividadeTipologiaCnae.cnaes[0]);

						this.cnaesAtividade.splice(this.indexItemEdicao, 1, dadosInclusao);

						this.indexItemEdicao = null;
						this.clearCnae();

					} else {
						this.erroIncluirCnaeTipologia(dadosExistentes);
					}

				}

			} else {
				this.errorMessageEmptyInclusaoCnae = false;
			}
			
		},

		getDadosItem(cnae) {

			let dadoListagem = {};
			
			dadoListagem.tipologia = this.relacaoAtividadeTipologiaCnae.tipologia;
			dadoListagem.cnae = cnae;
			dadoListagem.foraMunicipio = this.relacaoAtividadeTipologiaCnae.foraMunicipio;
			console.log(this.relacaoAtividadeTipologiaCnae);
			return dadoListagem;

		},

		validarValoresAdicionados(cnae) {

			let validacao = true;

			this.cnaesAtividade.forEach((dado, index) => {

				if (dado.cnae.id === cnae.id
					&& (this.isInclusaoCnae || this.indexItemEdicao != index)) {

					validacao = false;
				}

			});

			return validacao;

		},

		erroIncluirCnaeTipologia(codigosCnaes) {

			let dadosExistentes = '';

			codigosCnaes.forEach((codigo, index) => {

				dadosExistentes += codigo;

				if (index !== codigosCnaes.length -1) {
					dadosExistentes+= ", ";
				} else {
					dadosExistentes+= ". ";
				}

			});

			let message = ERROR_MESSAGES.atividadeLicenciavel.atividades.adicionarValores + "Já existe uma relação " +
				"com o(s) CNAE(s): " + dadosExistentes;

			snackbar.alert(message);

		},

		editarItem(item) {
			
			window.scrollTo(0,0);
			this.relacaoAtividadeTipologiaCnae.tipologia = item.tipologia;
			this.relacaoAtividadeTipologiaCnae.cnaes = [];
			this.relacaoAtividadeTipologiaCnae.cnaes.push(item.cnae);
			this.relacaoAtividadeTipologiaCnae.foraMunicipio = item.foraMunicipio;

			if (!this.isCadastro) {

				item.cnae.textoExibicao = item.cnae.codigo + ' - ' + item.cnae.nome;
				this.cnaes.push(item.cnae);

			}

			this.indexItemEdicao = this.cnaesAtividade.indexOf(item);
			console.log(this.cnaesAtividade.indexOf(item));
			this.isInclusaoCnae = false;
			this.isInclusao = false;

			const that = this;

			setTimeout(function() {

				that.$refs.toggleOptionsForaMunicipio.setModel(item.foraMunicipio);

			}, 100);

		},

		excluirItem(item) {

			this.$fire({

				title:'<p class="title-modal-confirm">Remover relação - ' + item.cnae.codigo + ' / ' + item.cnae.nome,

				html:`<p class="message-modal-confirm">Ao remover a relação CNAE / Atividade, eles não estarão mais relacionados.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover a relação CNAE / Atividade? Esta opção pode ser desfeita a qualquer momento ao relacioná-los novamente.</b>
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

		atividadeCnaeService.buscarCnaesAtivosNaoVinculados()
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
			this.isInclusaoCnae = false;
		}

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