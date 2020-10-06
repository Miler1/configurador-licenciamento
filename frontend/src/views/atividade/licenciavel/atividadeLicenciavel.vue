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
			:editarItem="editarItem",
			:ativarDesativarItem="ativarDesativarItem",
			:parametrosFiltro="parametrosFiltro",
			:buttonCadastrar="buttonCadastrar",
			:abrirTelaCadastro="abrirTelaCadastro"
		)

</template>

<script>

import GridListagem from '@/components/GridListagem';
// import RelatorioService from '@/services/relatorio.service';
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
			tituloAba: "atividade",
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
			// RelatorioService.baixarRelatorio("/tipoCaracterizacaoAtividade/atividadeLicenciavel/relatorio");
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
			// this.$router.push({name: 'EditarAtividadeLicenciavel', params: { idAtividadeDispensavel: item.id }});
		},

		ativarDesativarItem(item) {

			// this.$fire({

			// 	title: item.ativo ?
			// 		'<p class="title-modal-confirm">Desativar CNAE dispensável - ' + item.nome+ '</p>' :
			// 		'<p class="title-modal-confirm">Ativar CNAE dispensável - ' + item.nome+ '</p>',

			// 	html: item.ativo ?
			// 		`<p class="message-modal-confirm">Ao desativar o CNAE dispensável, ele não estará mais disponível no sistema.</p>
			// 		<p class="message-modal-confirm">
			// 			<b>Tem certeza que deseja desativar o CNAE dispensável? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
			// 		</p>` :
			// 		`<p class="message-modal-confirm">Ao ativar o CNAE dispensável, ele ficará disponível no sistema.</p>
			// 		<p class="message-modal-confirm">
			// 			<b>Tem certeza que deseja ativar o CNAE dispensável? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
			// 		</p>`,
			// 	showCancelButton: true,
			// 	confirmButtonColor: item.ativo ? '#E6A23C' : '#67C23A',
			// 	cancelButtonColor: '#FFF',
			// 	showCloseButton: true,
			// 	focusConfirm: false,
			// 	confirmButtonText: item.ativo ? '<i class="fa fa-minus-circle"></i> Desativar' : '<i class="fa fa-check-circle"></i> Ativar',
			// 	cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
			// 	reverseButtons: true

			// }).then((result) => {

			// 	if(result.value) {

			// 		item.ativo = !item.ativo;

			// 		TipoCaracterizacaoAtividadeService.ativarDesativarAtividadeDispensavel(item.id)
			// 			.then(() => {

			// 				if (item.ativo) {
			// 					snackbar.alert(SUCCESS_MESSAGES.atividadeDispensavel.ativar, snackbar.type.SUCCESS);
			// 				} else {
			// 					snackbar.alert(SUCCESS_MESSAGES.atividadeDispensavel.desativar, snackbar.type.SUCCESS);
			// 				}

			// 				this.updatePagination();
			// 				this.resetaDadosFiltragem();

			// 			})
			// 			.catch(error => {

			// 				console.error(error);

			// 				if (item.ativo) {
			// 					snackbar.alert(ERROR_MESSAGES.atividadeDispensavel.ativar);
			// 				} else {
			// 					snackbar.alert(ERROR_MESSAGES.atividadeDispensavel.desativar);								
			// 				}

			// 			});

			// 	}

			// }).catch((error) => {
			// 	console.error(error);
			// });

		}

	},

};

</script>