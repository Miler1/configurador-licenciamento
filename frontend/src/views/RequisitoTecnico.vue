<template lang="pug">

	v-container

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
import RelatorioService from '../services/relatorio.service';
import RequisitoTecnicoService from '../services/requisitoTecnico.service';
import { HEADER } from '@/utils/dadosHeader/ListagemRequisitoTecnicoHeader';
import { SET_SNACKBAR } from '@/store/actions.type';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "RequisitoTecnico",

	components: {
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "requisito técnico",
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

		resetaDadosFiltragem() {

			this.parametrosFiltro.pagina = 0;
			this.parametrosFiltro.itemsPorPagina = 10;
			this.parametrosFiltro.tipoOrdenacao = 'dataCadastro,desc';
			this.parametrosFiltro.stringPesquisa = '';

		},

		abrirTelaCadastro() {

			this.$router.push({name: 'CadastrarRequisitosTecnicos'});

		},

		editarItem(item) {

			this.$router.push({name: 'EditarRequisitosTecnicos', params: { idRequisito: item.id }});

		},

		ativarDesativarItem(item) {

			this.$fire({

				title: item.ativo ? 
					'<p class="title-modal-confirm">Desativar Requisito Técnico - ' + item.codigo+ '</p>' :
					'<p class="title-modal-confirm">Ativar Requisito Técnico - ' + item.codigo+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o Requisito Técnico, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o Requisito Técnico? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o Requisito Técnico, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o Requisito Técnico? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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

					RequisitoTecnicoService.ativarDesativar(item.id)
						.then(() => {
							
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.requisitoTecnico.desativar, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'success', text: SUCCESS_MESSAGES.requisitoTecnico.ativar, timeout: '6000'}
								);

							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(error => {

							console.error(error);
							if(!item.ativo) {
								
								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.requisitoTecnico.desativar + error.message, timeout: '6000'}
								);
							
							} else {

								this.$store.dispatch(SET_SNACKBAR,
									{color: 'error', text: ERROR_MESSAGES.requisitoTecnico.ativar + error.message, timeout: '6000'}
								);

							}

						});

				}

			}).catch((error) => {
				console.error(error);
			});

		}
	},
};

</script>