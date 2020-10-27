<template lang="pug">

	div

		GridListagem.pa-7(
			:tituloAba="tituloAba",
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:continuarRascunho="continuarRascunho",
			:removerItem="removerItem",
			:parametrosFiltro="parametrosFiltro",
			:buttonCadastrar="buttonCadastrar",
			:abrirTelaCadastro="abrirTelaCadastro"
		)

</template>

<script>

import GridListagem from '@/components/GridListagem';
import RelatorioService from '@/services/relatorio.service';
import AtividadeService from '@/services/atividade.service';
import snackbar from '@/services/snack.service';
import { HEADER } from '@/utils/dadosHeader/ListagemAtividadeLicenciavelHeader';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "AtividadeLicenciavel",

	components: {
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "atividade licenciável",
			tituloListagem: "Listagem de atividades licenciáveis cadastradas",
			placeholderPesquisa: "Pesquise pelo código ou nome da atividade licenciável",
			headerListagem: HEADER,
			dadosListagem: {},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'nome,asc',
				stringPesquisa: ''
			},
			buttonCadastrar: true
		};
	},

	methods: {

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/atividadeLicenciavel/relatorio");
		},

		updatePagination(parametrosFiltro) {

			AtividadeService.listarAtividadeLicenciavel(parametrosFiltro)

				.then((response) => {

					this.dadosListagem = response.data;

					this.prepararDadosParaListagem();

					this.dadosListagem.nomeItem = 'atividades licenciáveis';

				})
				.catch(erro => {

					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.atividadeLicenciavel.listagem + erro.message);

				});

		},

		prepararDadosParaListagem(){

			var parametros = '';
			var tiposLicencas = '';

			this.dadosListagem.content.forEach(dado => {

				dado.parametros.forEach(parametro => {
					parametros += parametro.parametro.codigo + ' - ';
				});
				parametros = parametros.substring(0, parametros.length - 2);
				dado.parametros = parametros;
				parametros = '';

				dado.tiposLicencas.forEach(tipoLicenca => {
					tiposLicencas += tipoLicenca.sigla + ' - ';
				});
				tiposLicencas = tiposLicencas.substring(0, tiposLicencas.length - 2);
				dado.tiposLicencas = tiposLicencas;
				tiposLicencas = '';


			});

		},

		resetaDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'nome,asc';
			this.parametrosFiltro.stringPesquisa = '';
		},

		abrirTelaCadastro() {
			this.$router.push({name: 'CadastrarAtividadeLicenciavel'});
		},

		editarItem(item) {
			this.$router.push({name: 'EditarAtividadeLicenciavel', params: { idAtividadeLicenciavel: item.id }});
		},

	},

};

</script>