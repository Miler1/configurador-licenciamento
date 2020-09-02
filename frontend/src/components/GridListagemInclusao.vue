<template lang="pug">

#grid-listagem

	template

		v-col(cols='12' md='6')
			b.titulo-listagem {{ tituloListagem }}

			v-text-field#QA-input-inclusao-pesquisar(
				outlined,
				v-model='stringPesquisa'
				placeholder="Pesquisa",
				prepend-inner-icon="mdi-magnify",
				color="#E0E0E0",
				dense
			)

		v-data-table.elevation-1(
				:headers="headers",
				:items='dadosListagem',
				:search='stringPesquisa'
				:items-per-page="itemsPerPage"
				:footer-props='footerProps'
			)

			template(v-slot:item.obrigatorio='{ item }')
				span {{item.obrigatorio ? 'Básico' : 'Complementar'}}


			template(v-slot:item.actions='{ item }')
				v-tooltip(bottom)
					template(v-slot:activator="{ on, attrs }")
						v-icon.mr-2(small @click='editarItem(item)', v-on='on', color='#9EBAA4')
							| mdi-pencil
					span Editar	documento	
				v-tooltip(bottom)
					template(v-slot:activator="{ on, attrs }")
						v-icon(small @click='excluirItem(item)', v-on='on', color='#EB2D30')
							| mdi-close-circle
					span Remover documento	

			template(#footer.page-text="props")
				span Exibindo {{props.pageStart}}-{{props.pageStop}} de {{props.itemsLength}} registros

			template(v-slot:no-data)
				span Não existem documentos adicionados.

</template>

<script>

export default {

	data: () => ({

		stringPesquisa: null,
		itemsPerPage: 10,
		footerProps: {

			itemsPerPageText: 'Resultados por página',
			itemsPerPageOptions: [10, 20, 30, 50, 100]
		},

	}),

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

	}

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
