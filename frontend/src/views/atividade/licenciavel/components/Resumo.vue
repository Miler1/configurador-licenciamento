<template lang="pug">

#step-atividade-licenciavel-resumo

	b.titulo-cabecalho Dados básicos da atividade
	.cabecalho.pl-4.mb-7.rounded
		v-row
			v-col(cols="12", md="4")
				v-label Código
				p.label-atividade {{atividadeLicenciavel.dados.codigoAtividade}}
			v-col(cols="12", md="8")
				v-label Nome
				p.label-atividade {{atividadeLicenciavel.dados.nomeAtividade}}
		v-row
			v-col(cols="12", md="4")
				v-label Tipologia
				p.label-atividade {{atividadeLicenciavel.dados.tipologia.nome}}
			v-col(cols="12", md="3")
				v-label Tipos de licenças
				p.label-atividade {{preparaExibicaoTipoLicenca()}}
			v-col(cols="12", md="3")
				v-label Gerência / Setor
				p.label-atividade {{atividadeLicenciavel.dados.setor.sigla || atividadeLicenciavel.dados.setor}}
			v-col(cols="12", md="2")
				v-label PPD
				p.label-atividade {{atividadeLicenciavel.dados.potencialPoluidor.nome}}
		v-row
			v-col(cols="12", md="4")
				v-label Localização
				p.label-atividade {{preparaExibicaoLocalizacao()}}
			v-col(cols="12", md="3")
				v-label Atividade fora do empreendimento
				p.label-atividade {{preparaExibicaoExecucao()}}
			v-col(cols="12", md="3")
				v-label Geometria
				p.label-atividade {{preparaExibicaoGeometria()}}

	GridListagemInclusao.mb-7(
		:tituloListagem="tituloListagemCnae",
		:headers="headerCnae",
		:dadosListagem="atividadeLicenciavel.cnaesAtividade",
		:inputPesquisa="inputPesquisa"
	)

	b.titulo-cabecalho Vinculações
	.vinculacoes.pl-4.mb-7.rounded
		v-row
			v-col(cols="12", md="6")
				v-label Grupo de requisito técnico
				p.label-atividade {{atividadeLicenciavel.dados.requisitoTecnico.codigo}} - {{atividadeLicenciavel.dados.requisitoTecnico.descricao}}
			v-col(cols="12", md="6")
				v-label Tabela de taxas de licenciamento
				p.label-atividade {{atividadeLicenciavel.dados.taxaLicenciamento.codigo}} - {{atividadeLicenciavel.dados.taxaLicenciamento.descricao}}

	b.titulo-cabecalho Parâmetros da atividade
	.cabecalho.pl-4.mb-7.rounded
		v-row.flex-row
			v-col(cols="12", md="3")
						v-label Tipo de parâmetro
						p.label-atividade {{ (atividadeLicenciavel.parametros[0].parametroUm && atividadeLicenciavel.parametros[0].parametroDois) ? "Composto" : "Simples" }}
			v-col.pa-0(cols="12", md="9")
				v-row
					v-col(cols="12", md="6")
						v-label Parâmetro 1
						p.label-atividade {{atividadeLicenciavel.parametros[0].parametroUm.codigo}} - {{atividadeLicenciavel.parametros[0].parametroUm.nome}}
					v-col(cols="12", md="6")
						v-label Descrição da unidade do parâmetro 1
						p.label-atividade {{atividadeLicenciavel.parametros[0].descricaoUnidadeUm ? atividadeLicenciavel.parametros[0].descricaoUnidadeUm : atividadeLicenciavel.parametros[0].parametroUm.nome}}

				v-row(v-if="atividadeLicenciavel.parametros[0].parametroDois")
					v-col(cols="12", md="6")
						v-label Parametro 2
						p.label-atividade {{atividadeLicenciavel.parametros[0].parametroDois.codigo}} - {{atividadeLicenciavel.parametros[0].parametroDois.nome}}
					v-col(cols="12", md="6")
						v-label Descrição da unidade do parâmetro 2
						p.label-atividade {{atividadeLicenciavel.parametros[0].descricaoUnidadeDois ? atividadeLicenciavel.parametros[0].descricaoUnidadeDois : atividadeLicenciavel.parametros[0].parametroDois.nome}}

	GridListagemInclusao.mt-7(
		:tituloListagem="tituloListagemParametro",
		:headers="headerParametro",
		:dadosListagem="atividadeLicenciavel.parametros",
		:inputPesquisa="inputPesquisa"
		:resumo='resumo'
		:itemsPerPage="itemsPerPage",
		:hideFooter="hideFooter",
	)

</template>

<script>

import GridListagemInclusao from '@/components/GridListagemInclusao';
import { HEADERCNAE, HEADERPARAMETRO } from '@/utils/dadosHeader/ListagemResumoAtividadeLicenciavel';

export default {

	name:'Resumo',

	components: {
		GridListagemInclusao
	},

	props: {

		atividadeLicenciavel: {
			type: [Object]
		},

	},

	data: () => {

		return {

			inputPesquisa: false,
			resumo: true,
			hideFooter: true,
			itemsPerPage: 20,
			headerCnae: HEADERCNAE,
			headerParametro: HEADERPARAMETRO,
			tituloListagemCnae: "Listagem de CNAEs adicionados para esta atividade",
			tituloListagemParametro: "Listagem de relações parâmetros / portes adicionadas",

		};

	},

	methods: {

		preparaExibicaoTipoLicenca() {

			let tiposLicencas = '';

			this.atividadeLicenciavel.dados.licencas.forEach(licenca => {
				tiposLicencas += licenca.sigla + ', ';
			});

			return tiposLicencas.substring(0, tiposLicencas.length - 2);

		},

		preparaExibicaoLocalizacao() {

			let localizacao = '';

			this.atividadeLicenciavel.dados.tiposAtividade.forEach(localizaoSelect => {

				localizacao += localizaoSelect === 'URBANA' ? localizacao.length > 0 ? ' e Zona urbana' : 'Zona urbana' : localizacao.length > 0 ? ' e Zona rural' : 'Zona rural';

			});

			return localizacao;

		},

		preparaExibicaoGeometria() {

			let geometria = '';

			let dados = this.atividadeLicenciavel.dados;

			geometria += dados.geoPonto ? 'Ponto' : '';
			geometria += dados.geoLinha ? geometria.length > 0 ? dados.geoPoligono ? ', Linha' : ' e Linha' : 'Linha' : '';
			geometria += dados.geoPoligono ? geometria.length > 0 ? ' e Polígono' : 'Polígono' : '';

			return geometria;
		},

		preparaExibicaoExecucao() {
			return this.atividadeLicenciavel.dados.foraEmpreendimento === "true" ? "Sim" : "Não";
		}

	},

	created(){
	},

	mounted() {

		if (this.atividadeLicenciavel.parametros.length === 4) {

			this.headerParametro = [... HEADERPARAMETRO];
			this.headerParametro.splice(3,3);

		}

	}

};

</script>

<style lang="less">

@import "../../../../assets/css/variaveis.less";

.titulo-cabecalho{
	font-size: 18px;
}

.cabecalho, .vinculacoes{
	border: 1px solid @border-components;

	.label-atividade {
		margin: 0;
	}
}

</style>