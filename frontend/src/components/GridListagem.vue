<template lang="pug">

#grid-listagem
	b.titulo-listagem {{ tituloListagem }}
	v-row
		v-col(cols='12' md='6')
			v-text-field#QA-input-pesquisar(outlined,
			v-model="parametrosFiltro.stringPesquisa"
			:placeholder="placeholderPesquisa",
			prepend-inner-icon="mdi-magnify",
			color="#E0E0E0",
			dense,
			@input='inputPesquisa')

		v-col(cols='12' md='6')
			v-btn#QA-btn-abrir-cadastro.float-right.ml-4( @click="abrirTelaCadastro", large, dark, color="#84A98C", v-if="buttonCadastrar")
				v-icon.font-cadastrar mdi-plus
				span.font-cadastrar Cadastrar
			v-btn#QA-btn-gerar-relatorio.float-right(@click="gerarRelatorio", large, outlined, color="#84A98C")
				v-icon mdi-download
				span Gerar Relatório
			

	template
		v-data-table.elevation-1(:headers="headers",
				:items='dadosListagem.content',
				hide-default-footer,
				:items-per-page="itensPerPage",
				@update:options="sortBy")

			template#text-align-center(v-slot:item.validadeEmAnos='{ item }')
				span {{item.validadeEmAnos ? item.validadeEmAnos : ' ‒'}}

			template(v-slot:item.finalidade='{ item }')
				span {{item.finalidade}}

			template(v-slot:item.obrigatorio='{ item }')
				span {{item.obrigatorio ? 'Básico' : 'Complementar'}}

			template(v-slot:item.ativo='{ item }')
				span {{item.ativo ? 'Ativo' : 'Inativo'}}

			template(v-slot:item.actions='{ item }')
				v-icon.mr-2(small @click='editarItem(item)')
					| mdi-pencil
				v-icon(small @click='ativarDesativarItem(item)')
					| {{item.ativo ? 'mdi-minus-circle' : 'mdi-check-circle'}}

			template(v-slot:no-data)
				span Não existem registros a serem exibidos.

			template(v-slot:footer, v-if="dadosListagem.numberOfElements > 0")
				v-row
					v-col(cols='12' md='8')
						v-pagination.float-left(v-model="page"
								:length="dadosListagem.totalPages"
								:page="page"
								:total-visible="totalVisible",
								@input="input",
								color="#84A98C")
						span.float-left.exibicao-paginas.mt-4
							| Exibindo {{dadosListagem.numberOfElements}} de {{dadosListagem.totalElements}} registros

					v-col.flex-row.mt-3(cols='12' md='4')
						v-select.float-right.d-inline-flex.mx-4.w-80(:items="itensPerPages", 
								solo, 
								dense, 
								@input="changeValue", 
								v-model="itensPerPage")
						span.float-right.exibicao-paginas.mt-2.ml-1
							| Resultados por página

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
		},
		buttonCadastrar: {
			type: [Boolean]
		},
		abrirTelaCadastro: {
			type: [Function]
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

			if(this.parametrosFiltro.itemsPorPagina !== itensPerPage) {

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

.font-cadastrar{
	font-size:16px;
}

</style>
