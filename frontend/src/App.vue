<template lang='pug'>

	#app
		router-view

</template>

<script>

import MainWrapper from '@/views/MainWrapper'
import Login from '@/views/Login'

export default {
	
	name: 'App',

	components: { MainWrapper, Login },

	data() {
		return {
			usuarioLogado: false
		}
	}

}
</script>

<style lang="less">

html{ 
	
	overflow-y: auto !important;

	body{

		margin: 0;

		#app {
			font-family: Avenir, Helvetica, Arial, sans-serif;
			-webkit-font-smoothing: antialiased;
			-moz-osx-font-smoothing: grayscale;
		}
	}
}

</style>
