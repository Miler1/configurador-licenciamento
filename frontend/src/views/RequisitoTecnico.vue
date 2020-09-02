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
import snackbar from '@/services/snack.service';
import { HEADER } from '@/utils/dadosHeader/ListagemRequisitoTecnicoHeader';
import { SUCCESS_MESSAGES, ERROR_MESSAGES } from '@/utils/helpers/messages-utils';

export default {

	name: "RequisitoTecnico",

	components: {
		GridListagem
	},

	data: () => {
		return {
			tituloAba: "grupo de requisito técnico",
			tituloListagem: "Listagem de grupos de requisitos técnicos cadastrados",
			placeholderPesquisa: "Pesquisar por código ou descrição do grupo de requisito técnico",
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
					this.dadosListagem.nomeItem = "grupos de requisitos";

				})
				.catch(erro => {

					console.error(erro);
					snackbar.alert(ERROR_MESSAGES.requisitoTecnico.listagem + erro.message);
					
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
					'<p class="title-modal-confirm">Desativar grupo de requisito técnico - ' + item.codigo+ '</p>' :
					'<p class="title-modal-confirm">Ativar grupo de requisito técnico - ' + item.codigo+ '</p>',

				html: item.ativo ?
					`<p class="message-modal-confirm">Ao desativar o grupo de requisito técnico, ele não estará mais disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja desativar o grupo de requisito técnico? Esta opção pode ser desfeita a qualquer momento ao ativá-lo novamente.</b>
					</p>` :
					`<p class="message-modal-confirm">Ao ativar o grupo de requisito técnico, ele ficará disponível no sistema.</p>
					<p class="message-modal-confirm">
						<b>Tem certeza que deseja ativar o grupo de requisito técnico? Esta opção pode ser desfeita a qualquer momento ao desativá-lo novamente.</b>
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
							
							if(item.ativo) {
								snackbar.alert(SUCCESS_MESSAGES.requisitoTecnico.desativar, snackbar.type.SUCCESS);
							} else {
								snackbar.alert(SUCCESS_MESSAGES.requisitoTecnico.ativar, snackbar.type.SUCCESS);
							}

							this.updatePagination();
							this.resetaDadosFiltragem();

						})
						.catch(error => {

							console.error(error);
							if(!item.ativo) {
								snackbar.alert(ERROR_MESSAGES.requisitoTecnico.desativar);
							} else {
								snackbar.alert(ERROR_MESSAGES.requisitoTecnico.ativar);
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