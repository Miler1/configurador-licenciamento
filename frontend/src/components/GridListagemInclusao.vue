<template lang="pug">

#grid-listagem
	b.titulo-listagem {{ tituloListagem }}

	template
		v-data-table.elevation-1(
				:headers="headers",
				:items='dadosListagem',
				hide-default-footer,
				@update:options="sortBy"
				:items-per-page="itemsPerPage"
			)

			template(v-slot:item.obrigatorio='{ item }')
				span {{item.obrigatorio == "true" ? 'Básico' : 'Complementar'}}

			template(v-slot:item.actions='{ item }')
				v-tooltip(bottom)
					template(v-slot:activator="{ on, attrs }")
						v-icon.mr-2(small @click='editarItem(item)', v-on='on', color='#9EBAA4')
							| mdi-pencil
					span Editar	documento	
				v-tooltip(bottom)
					template(v-slot:activator="{ on, attrs }")
						v-icon(small @click='excluirItem(item)', v-on='on', color='#F56C6C')
							|  mdi-delete
					span Remover documento	

			template(v-slot:no-data)
				span Não existem documentos adicionados.

</template>

<script>

export default {

	data: () => ({

		itemsPerPage: 200

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
