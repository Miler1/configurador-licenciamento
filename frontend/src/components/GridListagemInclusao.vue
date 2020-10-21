<template lang="pug">

#grid-listagem

	template

		v-col(cols='12' md='6').pa-0
			b.titulo-listagem {{ tituloListagem }}

			v-text-field#QA-input-inclusao-pesquisar(
				outlined,
				v-model='stringPesquisa'
				:placeholder='placeholderPesquisa',
				prepend-inner-icon="mdi-magnify",
				color="#E0E0E0",
				dense
				v-if="inputPesquisa"
			)

		v-data-table.elevation-1(
				:headers="headers",
				:items='dadosListagem',
				:search='stringPesquisa'
				:customFilter='customFilter'
				:items-per-page='itemsPerPage'
				:footer-props='footerProps'
				:hide-default-footer="hideFooter"
				:no-results-text='labelNoResultset'
			)

			template(v-slot:item.obrigatorio='{ item }')
				span {{item.obrigatorio == "true" ? 'Básico' : 'Complementar'}}

			template(v-slot:item.foraMunicipio='{ item }')
				span {{item.foraMunicipio == "true" ? 'Sim' : 'Não'}}

			template(v-slot:item.limiteInferiorUm='{ item }')
				span {{item.limiteInferiorUm == null ? '0' : item.limiteInferiorUm}}

			template(v-slot:item.limiteInferiorDois='{ item }')
				span {{item.limiteInferiorDois == null ? '0' : item.limiteInferiorDois}}

			template(v-slot:item.limiteSuperiorUm='{ item }')
				span {{item.limiteSuperiorUm == null ? 'Indeterminado' : item.limiteSuperiorUm}}

			template(v-slot:item.limiteSuperiorDois='{ item }')
				span {{item.limiteSuperiorDois == null ? 'Indeterminado' : item.limiteSuperiorDois}}

			template(v-slot:item.valor='{ item }')
				span(v-if="item.tipoTaxa != 'formula'")
					| {{parseFloat(item.valor) !== 0 ? parseFloat(item.valor).toLocaleString('pt-BR', { minimumFractionDigits: 2}) : 'Isento'}}
				span(v-else)
					| {{item.valor}}

			template(v-slot:item.porte='props' v-if='!resumo')
				v-select(:items="dadosSelect", item-text="nome", return-object, v-model="props.item.porte", hide-details="auto", :error-messages="errorMessage(props.item.porte)")

			template(v-slot:item.licenciamentoMunicipal='{ item }')
				v-simple-checkbox(v-model="item.licenciamentoMunicipal", color="#84A98C", :disabled='resumo')

			template(v-slot:item.repasseOutroOrgao='{ item }')
				v-simple-checkbox(v-model="item.repasseOutroOrgao", color="#84A98C", :disabled='resumo')

			template(v-slot:item.actions='{ item }')
				v-tooltip(bottom)
					template(v-slot:activator="{ on, attrs }")
						v-icon.mr-2(small @click='editarItem(item)', v-on='on', color='#9EBAA4')
							| mdi-pencil
					span Editar {{tituloTooltip}}
				v-tooltip(bottom, v-if="exibirIconeRemover")
					template(v-slot:activator="{ on, attrs }")
						v-icon(small @click='excluirItem(item)', v-on='on', color='#F56C6C')
							|  mdi-delete
					span Remover {{tituloTooltip}}

			template(#footer.page-text="props")
				span Exibindo {{props.pageStart}}-{{props.pageStop}} de {{props.itemsLength}} registros

			template(v-slot:no-data)
				span {{labelNoData}}

</template>

<script>

export default {

	data: () => ({

		stringPesquisa: null,
		footerProps: {

			itemsPerPageText: 'Resultados por página',
			itemsPerPageOptions: [10, 20, 30, 50, 100]
		},

	}),

	methods: {

		customFilter(value, search, item) {

			value = value ? this.normalizer(value) : value;
			search = this.normalizer(search);

			return value != null
				&& search != null
				&& value !== 'true'
				&& value !== 'false'
				&& value.toString().indexOf(search) !== -1; //Faz o matching da pesquisa dentro do valor
		},

		normalizer(string) {
			return string.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
		}

	},

	name:'GridListagemInclusao',

	props: {

		tituloListagem: {
			type: [String]
		},
		headers: {
			type: [Array]
		},
		dadosListagem: {
			type: [Array]
		},
		editarItem: {
			type: [Function]
		},
		excluirItem: {
			type: [Function]
		},
		labelNoData: {
			type: [String]
		},
		labelNoResultset: {
			type: [String]
		},
		placeholderPesquisa: {
			type: [String]
		},
		tituloTooltip: {
			type: [String]
		},
		inputPesquisa: {
			type: [Boolean],
			default: true
		},
		exibirIconeRemover: {
			type: [Boolean],
			default: true
		},
		hideFooter: {
			type: [Boolean],
			default: false
		},
		itemsPerPage: {
			type: [Number],
			default: 10
		},
		dadosSelect: {
			type: [Array]
		},
		errorMessage: {
			type: [Function]
		},
		resumo: {
			type: [Boolean],
			default: false
		}

	}

};

</script>

<style lang="less">

@import "../assets/css/variaveis.less";

thead tr th {
	font-size: 14px !important;
}

tbody tr:nth-of-type(odd) {
	background-color: rgba(0, 0, 0, .05);
}

#text-align-center {
	text-align: center;
}

.titulo-listagem{
	font-size: 18px;
}

.exibicao-paginas{
	color: @text-color;
	font-size: 14px;
}

.v-pagination__item {
	font-size: 13px !important;
}

.v-pagination__item--active{
	cursor: default !important;
}

.v-list-item__title {
		font-size: 13px;
	}

.w-80{
	width: 80px;

	.v-input__slot {
		font-size: 13px;
	}
}

.v-btn {
	text-transform: none !important;
}

</style>
