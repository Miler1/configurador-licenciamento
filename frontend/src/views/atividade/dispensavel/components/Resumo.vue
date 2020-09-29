<template lang="pug">

#step-cnaes-atividade-dispensavel-Resumo.pt-7

	GridListagemInclusao.px-7(
		:tituloListagem="tituloListagemCnae",
		:headers="headerCnae",
		:dadosListagem="atividadeDispensavel.cnaesTipologia",
		:inputPesquisa="inputPesquisa"
	)

	GridListagemInclusao.px-7.pt-7(
		:tituloListagem="tituloListagemPergunta",
		:headers="headerPergunta",
		:dadosListagem="atividadeDispensavel.perguntas",
		:inputPesquisa="inputPesquisa"
	)

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import { HEADERCNAE, HEADERPERGUNTA } from '@/utils/dadosHeader/ListagemResumoAtividadeDispensavel';

export default {

	name:'Resumo',

	components: {
		GridListagemInclusao
	},

	props: {

		atividadeDispensavel: {
			type: [Object]
		},

	},
	
	data: () => {

		return {

			inputPesquisa: false,
			headerCnae: HEADERCNAE,
			headerPergunta: HEADERPERGUNTA,
			tituloListagemCnae: "Listagem de relações CNAEs / tipologias adicionadas",
			tituloListagemPergunta: "Listagem de perguntas adicionadas",

		};

	},

	methods: {

		//SOME METHODS HERE

	},

	created(){
	},

	mounted() {

	}

};

</script>

<style lang="less">


</style>