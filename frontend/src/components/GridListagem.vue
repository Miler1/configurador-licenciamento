<template lang="pug">

#grid-listagem
	b.titulo-listagem {{ tituloListagem }}
	v-row
		v-col(cols='12' md='8')
			v-text-field(outlined,
			:placeholder="placeholderPesquisa",
			prepend-inner-icon="mdi-magnify",
			color="#E0E0E0",
			dense,
			@input='inputPesquisa')

		v-col(cols='12' md='4')
			v-btn#QA-btn-gerar-relatorio.float-right(@click="gerarRelatorio", large, outlined, color="#84A98C")
				v-icon mdi-download
				span Gerar Relatório

	template
		v-data-table.elevation-1(:headers="headers",
				:items='dadosListagem.content',
				hide-default-footer,
				:items-per-page="numberOfPages",
				@update:options="sortBy")

			template(v-slot:item.ativo='{ item }')
				span {{item.ativo ? 'Ativo' : 'Inativo'}}

			template(v-slot:item.actions='{ item }')
				v-icon.mr-2(small @click='')
					| mdi-pencil
				v-icon(small @click='')
					| {{item.ativo ? 'mdi-minus-circle' : 'mdi-check-circle'}}

			template(v-slot:no-data)
				span Não existem registros a serem exibidos.

			template(v-slot:footer, v-if="dadosListagem == null" )
				v-row
					v-col(cols='12' md='4')
						v-pagination.float-left(v-model="page"
								:length="dadosListagem.totalPages"
								:page="page"
								:total-visible="totalVisible",
								@input="input",
								color="#84A98C")

					v-col.flex-row.mt-3(cols='12' md='8')
						v-select.float-right.d-inline-flex.mx-4.w-90(:items="numbersOfPages", 
								solo, 
								dense, 
								@input="changeValue", 
								v-model="numberOfPages")
						span.float-right.exibicao-paginas.mt-2.ml-1
							| Resultados por página
						span.float-right.exibicao-paginas.mt-2
							| Exibindo {{dadosListagem.numberOfElements}} de {{dadosListagem.totalElements}} registros /

</template>

<script>

export default {
	name:'GridListagem',
	props: {

		tituloListagem: {
			type: [String]
		},
		placeholderPesquisa: {
			type: [String]
		},
		gerarRelatorio: {
			type: [Function]
		},
		headers: {
			type: [Array]
		},
		dadosListagem: {
			type: [Object]
		},
		updatePagination: {
			type: [Function]
		}

	},

	data: () => ({

		page: 0,
		totalVisible: 7,
		numberOfPages: 10,
		numbersOfPages: [10, 15, 20, 30, 40, 50, 100],
		parametrosFiltro: {
			pagina: 0,
			itemsPorPagina: 10,
			tipoOrdenacao: 'dataCadastro,asc',
			stringPesquisa: ''
		}

	}),

	updated() {

		if(this.dadosListagem && this.dadosListagem.pageable) {
			this.page = this.dadosListagem.pageable.pageNumber + 1;
		}

	},

	methods: {

		changeValue(numberOfPages) {

			this.parametrosFiltro.itemsPorPagina = numberOfPages;
			this.parametrosFiltro.pagina = 0;
			this.updatePagination(this.parametrosFiltro);

		},

		input(page) {

			this.parametrosFiltro.pagina = page-1;
			this.updatePagination(this.parametrosFiltro);

		},

		inputPesquisa(text) {

			this.parametrosFiltro.stringPesquisa = text;
			this.updatePagination(this.parametrosFiltro);

		},

		sortBy(value) {

			if(value.sortBy.length > 0) {
				this.parametrosFiltro.tipoOrdenacao = value.sortBy[0] + (value.sortDesc[0] ? ',desc' : ',asc');
			} else {
				this.parametrosFiltro.tipoOrdenacao = null;
			}
			this.updatePagination(this.parametrosFiltro);

		},

		dadosListagemIsNull() {
			return this.dadosListagem == null;
		}

	},

}

</script>

<style lang="less">
@import "../assets/css/variaveis.less";

tbody tr:nth-of-type(odd) {
	background-color: rgba(0, 0, 0, .05);
}
.titulo-listagem{
	font-size: 18px;
}
.exibicao-paginas{
	color: @text-color;
}
.w-90{
	width: 90px;
}

</style>
