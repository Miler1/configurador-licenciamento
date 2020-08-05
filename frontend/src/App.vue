<template lang='pug'>

#app
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

import { mapGetters } from 'vuex'
import Snackbar from '@/components/Snackbar'

export default {
	
	name: 'App',

	components: { Snackbar },

	computed: {
		...mapGetters(['snackbars'])
	},

}
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
		}
	}
}

</style>
