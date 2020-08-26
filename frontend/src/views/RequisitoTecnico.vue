<template lang="pug">

	v-container

		GridListagem.pa-7(
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:editarItem="editarItem",
			:ativarDesativarItem="ativarDesativarItem",
			:parametrosFiltro="parametrosFiltro",
			:buttonCadastrar="buttonCadastrar",
			:abrirTelaCadastro="abrirTelaCadastro"
		)

</template>

<script>

import GridListagem from '@/components/GridListagem';
import RelatorioService from '../services/relatorio.service';
import RequisitoTecnicoService from '../services/requisitoTecnico.service';
import { HEADER } from '@/utils/dadosHeader/ListagemRequisitoTecnicoHeader';

export default {

	name: "RequisitoTecnico",

	components: {
		GridListagem
	},

	data: () => {
		return {
			tituloListagem: "Listagem de requisitos técnicos cadastrados",
			placeholderPesquisa: "Pesquisar por código ou descrição do requisito",
			headerListagem: HEADER,
			dadosListagem: {},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
			buttonCadastrar: true
		};
	},

	methods: {

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/requisitoTecnico/relatorio");
		},

		updatePagination(parametrosFiltro) {

			RequisitoTecnicoService.listar(parametrosFiltro)

				.then((response) => {
					this.dadosListagem = response.data;
				})
				.catch(erro => {

					console.error(erro);
					
					this.$store.dispatch(SET_SNACKBAR,
						{color: 'error', text: ERROR_MESSAGES.requisitoTecnico.listagem + ': ' + erro.message, timeout: '6000'}
					);
					
				});

		},

		abrirTelaCadastro() {

			this.$router.push({name: 'CadastrarRequisitosTecnicos'});

		},

		editarItem(item) {

			this.$router.push({name: 'EditarRequisitosTecnicos', params: { idRequisito: item.id }});

		},

		ativarDesativarItem(item) {

		}
	},
};

</script>