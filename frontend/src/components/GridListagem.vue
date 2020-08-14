<template lang="pug">

#grid-listagem
	b.titulo-listagem {{ tituloListagem }}
	v-row
		v-col(cols='12' md='8')
			v-text-field#QA-input-pesquisar(outlined,
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
				:items-per-page="itensPerPage",
				@update:options="sortBy")

			template#teste(v-slot:item.validadeEmAnos='{ item }')
				span {{item.validadeEmAnos ? item.validadeEmAnos : '̶'}}

			template(v-slot:item.ativo='{ item }')
				span {{item.ativo ? 'Ativo' : 'Inativo'}}

			template(v-slot:item.actions='{ item }')
				v-icon.mr-2(small @click='editarItem(item)')
					| mdi-pencil
				v-icon(small @click='ativarDesativarItem(item)')
					| {{item.ativo ? 'mdi-minus-circle' : 'mdi-check-circle'}}

			template(v-slot:no-data)
				span Não existem registros a serem exibidos.

			template(v-slot:footer)
				v-row
					v-col(cols='12' md='4')
						v-pagination.float-left(v-model="page"
								:length="dadosListagem.totalPages"
								:page="page"
								:total-visible="totalVisible",
								@input="input",
								color="#84A98C")

					v-col.flex-row.mt-3(cols='12' md='8')
						v-select.float-right.d-inline-flex.mx-4.w-80(:items="itensPerPages", 
								solo, 
								dense, 
								@input="changeValue", 
								v-model="itensPerPage")
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
		},
		editarItem: {
			type: [Function]
		},
		ativarDesativarItem: {
			type: [Function]
		},
		parametrosFiltro: {
			type: [Object]
		}

	},

	data: () => ({

		page: 0,
		totalVisible: 7,
		itensPerPage: 10,
		itensPerPages: [10, 15, 20, 30, 40, 50, 100],

	}),

	updated() {

		if(this.dadosListagem && this.dadosListagem.pageable) {
			this.page = this.dadosListagem.pageable.pageNumber + 1;
		}

	},

	methods: {

		changeValue(itensPerPage) {

			if(parametrosFiltro.itemsPorPagina !== itensPerPage) {

				this.parametrosFiltro.itemsPorPagina = itensPerPage;
				this.parametrosFiltro.pagina = 0;
				this.updatePagination(this.parametrosFiltro);

			}

		},

		input(page) {

			if(this.parametrosFiltro.pagina !== page-1) {

				this.parametrosFiltro.pagina = page-1;
				this.updatePagination(this.parametrosFiltro);

			}

		},

		inputPesquisa(text) {

			this.parametrosFiltro.pagina = 0;
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

};

</script>

<style lang="less">

@import "../assets/css/variaveis.less";

tbody tr:nth-of-type(odd) {
	background-color: rgba(0, 0, 0, .05);
}

#teste {
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

</style>
