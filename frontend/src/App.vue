<template lang="pug">

				v-app
								v-main
												Login

</template>

<script>
import Login from '@/views/Login';

export default {
	name: 'App',

	components: {
		Login,
	},

	data: () => ({
		//
	}),
};
</script>

<style lang="less">

				html {
								overflow-y: auto !important;

								body {

												margin: 0;

												#app {
																font-family: Roboto, sans-serif;
																-webkit-font-smoothing: antialiased;
																-moz-osx-font-smoothing: grayscale;
												}
								}
				}

</style>
