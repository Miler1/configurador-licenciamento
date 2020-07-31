<template lang='pug'>

#app
    v-app
		v-main
			router-view

</template>

<script>

export default {
	
	name: 'App',

	data() {
	}

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
