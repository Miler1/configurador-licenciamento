<template lang="pug">

#step-cnaes-atividade-dispensavel-cnae
	div.pb-7
		v-expansion-panels.pa-7(multiple, v-model="dadosPanel.panel", :readonly="dadosPanel.readonly")
			v-expansion-panel
				v-expansion-panel-header
					div.d-flex.flex-row.align-center.justify-start
						span.align-baseline Relação CNAE / Tipologia
					template(v-slot:actions)
						v-icon
				v-expansion-panel-content
					v-form(ref="atividadeDispensavelCnaeTipologia")
						v-container.pa-0
							v-row
								v-col(cols="12", md="4")
									v-label CNAE
									v-autocomplete#QA-select-atividade-dispensavel-cnae(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelectCnae",
										item-color="#84A98C",
										v-model="relacaoCnaeTipologia.cnaes",
										:items="cnaes",
										item-text="textoExibicao",
										item-value="codigo",
										:error-messages="errorMessage(relacaoCnaeTipologia.cnaes)",
										no-data-text="Nenhum tipo de CNAE encontrado",
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
												| {{ data.item.codigo }}
								v-col(cols="12", md="4")
									v-label Tipologia
									v-autocomplete#QA-select-atividade-dispensavel-tipologia(
										outlined,
										dense,
										color="#E0E0E0",
										:placeholder="placeholderSelect",
										item-color="grey darken-3",
										v-model="relacaoCnaeTipologia.tipologia",
										:items="tipologias",
										item-text="nome",
										:error-messages="errorMessage(relacaoCnaeTipologia.tipologia)",
										no-data-text="Nenhum documento encontrado",
										@click.native="resetErrorMessage",
										required,
										return-object=true
									)
								v-col(cols="12", md="4")
									ToggleOptions(
										ref="toggleOptionsForaMunicipio",
										labelOption="Atividade fora do município",
										idToggle="QA-btn-toggle-fora-municipio",
										:errorMessage="errorMessage",
										:options="optionsForaMunicipio",
										@changeOption="relacaoCnaeTipologia.foraMunicipio = $event"
									)

							v-row
								v-col#form-actions.d-flex.flex-row.align-center.justify-end(cols="12", md="12")
									a#QA-limpar-dados-atividade-dispensavel-cnae.d-flex.flex-row.align-center.justify-end(@click="clearForm")
										v-icon mdi-delete
										span Limpar dados
								
									v-btn#QA-btn-adicionar-atividade-dispensavel-cnae(@click="incluirDados", large, outlined, color="#84A98C", v-if="isInclusao")
										v-icon mdi-plus
										span Adicionar
								
									v-btn#QA-btn-editar-atividade-dispensavel-cnae(@click="incluirDados", large, outlined, color="#84A98C", v-if="!isInclusao")
										v-icon mdi-pencil
										span Editar
		
		GridListagemInclusao.px-7(
			:tituloListagem="tituloListagem",
			:headers="headerListagem",
			:dadosListagem="cnaesTipologia",
			:editarItem="editarItem",
			:excluirItem="excluirItem",
			:labelNoData="labelNoData",
			:placeholderPesquisa="placeholderPesquisa",
			:tituloTooltip="tituloTooltip",
			:labelNoResultset="semResultados"
		)

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import atividadeCnaeService from '@/services/atividadeCnae.service';
import tipologiaService from '@/services/tipologia.service';
import snackbar from '@/services/snack.service';
import ToggleOptions from "@/components/ToggleOptions";
import { HEADER } from '@/utils/dadosHeader/ListagemCnaeTipologiaInclusao';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name:'PassoCnaes',

	components: {
		GridListagemInclusao,
		ToggleOptions
	},

	props: {

		cnaesTipologia: {
			type: [Array]
		},
		erro: {
			type: [Object]
		}

	},
	
	data: () => {

		return {
			dadosPanel: {
				panel: [0],
				readonly: true,
			},
			placeholder: "Digite aqui...",
			placeholderSelect: "Selecione",
			placeholderSelectCnae: "Selecione um ou mais",
			placeholderPesquisa:  "Pesquisar pelo CNAE ou tipologia",
			tituloTooltip: "CNAE e tipologia",
			semResultados: 'Nenhum CNAE e tipologia encontrado com a pesquisa informada.',
			cnaes: [],
			tipologias: [],
			isInclusao: true,
			tituloListagem: "Listagem de CNAEs e tipologias selecionadas",
			labelNoData: 'Não existem CNAEs e tipologias adicionados.',
			headerListagem: HEADER,
			errorMessageEmptyInclusao: true,
			relacaoCnaeTipologia: {
				cnaes: [],
				tipologia: null,
				foraMunicipio: null
			},
			optionsForaMunicipio: [
				{
					idOption: "QA-btn-atividade-dispensavel-fora-sim",
					value: "true",
					label: "Sim",
				},
				{
					idOption: "QA-btn-atividade-dispensavel-fora-nao",
					value: "false",
					label: "Não",
				}
			],

		};

	},

	methods: {

		errorMessage(value) {

			if (Array.isArray(value)){

				if (!this.isInclusao) {
					return 'Este campo não permite ser editado';
				} else if ((!this.errorMessageEmptyInclusao || this.erro.invalido) && value.length === 0) {
					return 'Obrigatório';
				}
			}

			return (this.errorMessageEmptyInclusao && !this.erro.invalido) || value ? '' : 'Obrigatório';

		},

		clearForm() {

			this.relacaoCnaeTipologia.cnaes = [];
			this.relacaoCnaeTipologia.tipologia = null;
			this.relacaoCnaeTipologia.foraMunicipio = null;
			this.$refs.toggleOptionsForaMunicipio.clearModel();
			this.resetErrorMessage();
		},

		resetErrorMessage() {

			this.errorEtapaCnaes = true;
			this.errorMessageEmptyInclusao = true;
			this.erro.invalido = false;

		},

		incluirDados() {

			if (this.checkFormVinculacao()) {

				let dadosInclusao = [];
				let dadosExistentes = [];

				if (this.isInclusao) {

					this.relacaoCnaeTipologia.cnaes.forEach(cnae => {

						if (!this.validarValoresAdicionados(cnae)) {
							dadosExistentes.push(cnae.codigo);
						}

						dadosInclusao.push(this.getDadosItem(cnae));

					});

					if (dadosExistentes.length === 0) {

						this.cnaesTipologia.push(...dadosInclusao);
						this.clearForm();

					} else {
						this.erroIncluirCnaeTipologia(dadosExistentes);
					}

				} else {

					if (!this.validarValoresAdicionados(this.relacaoCnaeTipologia.cnaes[0])) {
						dadosExistentes.push(this.valor.licencas[0].sigla);
					}

					if (dadosExistentes.length === 0 ) {

						dadosInclusao = this.getDadosItem(this.relacaoCnaeTipologia.cnaes[0]);

						this.cnaesTipologia.splice(this.indexItemEdicao, 1, dadosInclusao);

						this.indexItemEdicao = null;
						this.isInclusao = true;
						this.clearForm();

					} else {
						this.erroIncluirCnaeTipologia(dadosExistentes);
					}

				}

			} else {
				this.errorMessageEmptyInclusao = false;
			}

		},

		getDadosItem(cnae) {

			let dadoListagem = {};

			dadoListagem.tipologia = this.relacaoCnaeTipologia.tipologia;
			dadoListagem.cnae = cnae;
			dadoListagem.foraMunicipio = this.relacaoCnaeTipologia.foraMunicipio;

			return dadoListagem;

		},

		validarValoresAdicionados(cnae) {

			let validacao = true;

			this.cnaesTipologia.forEach(
				(dado, index) => {
					if (dado.tipologia.id === this.relacaoCnaeTipologia.tipologia.id
						&& dado.cnae.id === cnae.id
						&& (this.isInclusao || this.indexItemEdicao != index)) {

						validacao = false;
					}
				}
			);

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

			let message = ERROR_MESSAGES.atividadeDispensavel.cnaes.adicionarValores + "Já existe uma relação com a mesma combinação " +
				"para a tipologia: " + this.relacaoCnaeTipologia.tipologia.nome + " e " +
				"CNAE(s): " + dadosExistentes;

			snackbar.alert(message);

		},

		checkFormVinculacao() {

			return this.relacaoCnaeTipologia.cnaes
				&& this.relacaoCnaeTipologia.cnaes.length > 0
				&& this.relacaoCnaeTipologia.tipologia
				&& this.relacaoCnaeTipologia.foraMunicipio
				&& this.relacaoCnaeTipologia.foraMunicipio != '';

		},

		editarItem(item) {
			window.scrollTo(0,0);
			this.relacaoCnaeTipologia.tipologia = item.tipologia;
			this.relacaoCnaeTipologia.cnaes = [];
			this.relacaoCnaeTipologia.cnaes.push(item.cnae);
			this.relacaoCnaeTipologia.foraMunicipio = item.foraMunicipio;

			this.indexItemEdicao = this.cnaesTipologia.indexOf(item);
			this.isInclusao = false;

			var that = this;

			setTimeout(function() {

				that.$refs.toggleOptionsForaMunicipio.setModel(item.foraMunicipio);

			}, 100);
		},

		excluirItem(item) {

			this.$fire({

				title:'<p class="title-modal-confirm">Remover CNAE e tipologia</p>',

				html:`<p class="message-modal-confirm">Ao remover o CNAE e a tipologia, eles não estarão mais relacionados.</p>
						<p class="message-modal-confirm">
						<b>Tem certeza que deseja remover o CNAE e a tipologia? Esta opção pode ser desfeita a qualquer momento ao relacioná-los novamente.</b>
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

					let indexItemExclusao = this.cnaesTipologia.indexOf(item);
					this.cnaesTipologia.splice(indexItemExclusao, 1);

				}

			});
		},
		selecionarCnae() {

			console.log('here');
			
			this.relacaoCnaeTipologia.cnaes.forEach(cnae => {
				cnae.textoExibicao = cnae.codigo;
			});
		}

	},

	created(){

		atividadeCnaeService.buscarCnaesAtivos()
			.then((response) => {
				this.cnaes = response.data;

				this.cnaes.forEach(cnae => {
					cnae.textoExibicao = cnae.codigo + ' - ' + cnae.nome;
				});
			});

		tipologiaService.buscarTipologiasAtivas()
			.then((response) => {
				this.tipologias = response.data;
			});
	},

	mounted() {

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