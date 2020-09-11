<template lang="pug">

	v-container

		GridListagem.pa-7(
			:tituloAba="tituloAba"
			:tituloListagem="tituloListagem",
			:placeholderPesquisa="placeholderPesquisa",
			:gerarRelatorio="gerarRelatorio",
			:headers="headerListagem",
			:dadosListagem="dadosListagem",
			:updatePagination="updatePagination",
			:editarItem="editarItem",
			:ativarDesativarItem="ativarDesativarItem",
			:parametrosFiltro="parametrosFiltro"
			:buttonCadastrar="buttonCadastrar",
			:abrirTelaCadastro="abrirTelaCadastro"
		)

</template>

<script>

import GridListagem from '@/components/GridListagem';
import snackbar from '@/services/snack.service';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';
import { HEADER } from '@/utils/dadosHeader/ListagemTaxaLicenciamentoHeader';
import TaxaLicenciamentoService from '@/services/taxaLicenciamento.service';
import RelatorioService from '@/services/relatorio.service';

export default {

	name: "TaxaLicenciamento",

	components: {

		GridListagem
	},

	data: () => {
		return {
			tituloAba:'tabela de taxa de licenciamento',
			tituloListagem: 'Listagem de tabelas de taxas de licenciamento cadastradas',
			placeholderPesquisa: "Pesquisar pelo código ou descrição da tabela de taxa de licenciamento",
			headerListagem: HEADER,
			dadosListagem: {
				nomeItem: "tabelas de taxas de licenciamento",
			},
			parametrosFiltro: {
				pagina: 0,
				itemsPorPagina: 10,
				tipoOrdenacao: 'dataCadastro,desc',
				stringPesquisa: ''
			},
			buttonCadastrar: true,
		};
	},

	methods: {

		resetaDadosFiltragem() {
			
			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'dataCadastro,desc';
			this.parametrosFiltro.stringPesquisa = '';

		},

		gerarRelatorio() {
			RelatorioService.baixarRelatorio("/codigoTaxaLicenciamento/relatorio");
		},

		abrirTelaCadastro() {
			this.$router.push({name: 'CadastrarTaxaLicenciamento'});
		},

		editarItem(item) {
			this.$router.push({name: 'EditarTaxaLicenciamento', params: { idTaxaLicenciamento: item.id }});
		},

		updatePagination(taxasFiltro) {

			TaxaLicenciamentoService.listar(taxasFiltro)

				.then((response) => {

					this.dadosListagem = response.data;
					this.dadosListagem.nomeItem = 'tabelas de taxas de licenciamento';

				})
				.catch(erro => {

					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.taxa.listagem + erro.message);

				});

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ? 
					'<p class="title-modal-confirm">Desativar tabela de taxa de licenciamento - ' + item.codigo+ '</p>' :
					'<p class="title-modal-confirm">Ativar tabela de taxa de licenciamento - ' + item.codigo+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar a tabela de taxa de licenciamento, ela não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar a tabela de taxa de licenciamento? Esta opção pode ser desfeita a qualquer momento ao ativá-la novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar a taxa de licenciamento, ela ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar a tabela de taxa de licenciamento? Esta opção pode ser desfeita a qualquer momento ao desativá-la novamente.</b>
					</p>`,
				showCancelButton: true,
				confirmButtonColor: item.ativo ? '#E6A23C' : '#67C23A',
				cancelButtonColor: '#FFF',
				showCloseButton: true,
				focusConfirm: false,
				confirmButtonText: item.ativo ? '<i class="fa fa-minus-circle"></i> Desativar' : '<i class="fa fa-check-circle"></i> Ativar',
				cancelButtonText: '<i class="fa fa-close"></i> Cancelar',
				reverseButtons: true

			}).then((result) => {

				if(result.value) {

					item.ativo = !item.ativo;

					TaxaLicenciamentoService.ativarDesativar(item.id)
						.then(() => {
							
							if(item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.taxaLicenciamento.ativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.taxaLicenciamento.desativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(error => {

							console.error(error);
							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.taxaLicenciamento.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.taxaLicenciamento.ativar);
							}

							item.ativo = !item.ativo;

						});

				}

			}).catch((error) => {
				console.error(error);
			});

		}

	}

};

</script>