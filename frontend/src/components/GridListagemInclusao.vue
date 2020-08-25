<template lang="pug">

#grid-listagem
	b.titulo-listagem {{ tituloListagem }}

	template
		v-data-table.elevation-1(:headers="headers",
				:items='dadosListagem',
				hide-default-footer,
				@update:options="sortBy")

			template(v-slot:item.obrigatorio='{ item }')
				span {{item.obrigatorio=='true' ? 'Básico' : 'Complementar'}}

			template(v-slot:item.actions='{ item }')
				v-icon.mr-2(small @click='editarItem(item)')
					| mdi-pencil
				v-icon(small @click='excluirItem(item)')
					| mdi-close-circle

			template(v-slot:no-data)
				span Não existem registros incluídos.

</template>

<script>

export default {

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
		}

	},

	methods: {

		sortBy(value) {

			// if(value.sortBy.length > 0) {
			// 	this.parametrosFiltro.tipoOrdenacao = value.sortBy[0] + (value.sortDesc[0] ? ',desc' : ',asc');
			// } else {
			// 	this.parametrosFiltro.tipoOrdenacao = null;
			// }
			// this.updatePagination(this.parametrosFiltro);

		},

		dadosListagemIsNull() {
			return this.dadosListagem == null;
		},

	},

};

</script>

<style lang="less">

@import "../assets/css/variaveis.less";

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
