<template lang='pug'>

#app
	//- vue-confirm-dialog.open-dialog
	v-app
		v-main
			router-view

			//- Elemento para exibir mensagens de alertas globais
			v-snackbar(v-for='(snackbar, index) in snackbars.filter(s => s.showing)'
						:key='snackbar.text + Math.random()'
						v-model='snackbar.showing'
						:timeout='snackbar.timeout'
						:color='snackbar.color'
						:style='`padding-bottom: ${(index * 60) + 8}px`')
				
				| {{snackbar.text}}
				template(v-slot:action=	'{ attrs }')
					v-btn(text @click='snackbar.showing = false' v-bind="attrs")
						| Close

</template>

<script>

import { mapGetters } from 'vuex';
import Snackbar from '@/components/Snackbar';

export default {
	
	name: 'App',

	components: { Snackbar },

	computed: {
		...mapGetters(['snackbars'])
	},

};
</script>

<style lang="less">

html{ 
	
	overflow-y: auto !important;

	body{

		margin: 0;

		#app {
			font-family: Roboto, sans-serif;
			-webkit-font-smoothing: antialiased;
			-moz-osx-font-smoothing: grayscale;

			.open-dialog {
				
			}
		}

		.swal2-shown {
			font-family: Roboto, sans-serif;
			-webkit-font-smoothing: antialiased;
			-moz-osx-font-smoothing: grayscale;

			.swal2-header {
				align-items: baseline;
				border-bottom: 1px solid #ddd;
			}

			.title-modal-confirm {
				font-size: 18px;
				text-align: left;
			}

			.message-modal-confirm {
				font-size: 16px;
				margin-top: 20px;
				text-align: left;
			}

			.swal2-cancel {
				margin-right: 130px;
				color: #777777;
				border: 1px solid #E0E0E0 !important;
				outline: none;
			}

			.swal2-close {
				margin-top: 4px;
			}
		}

		//Não foi achado uma maneira de trocar para cursor default no input de sanha do card login
		.mdi-key {
			cursor: default !important;
		}
	
	}
}

</style>
