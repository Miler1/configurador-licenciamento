<template lang="pug">

	v-container#container-versao.fill-height
		CardVersao(
			:config="config"
		)


</template>

<script>

import CardVersao from "@/views/versao/CardVersao";

import VersaoService from '@/services/versao.service';


export default {

	name: 'Versao',

	components: {CardVersao},

	container: {
		CardVersao
	},

	data: () => {
		return {
			config: {}
		};
	},

	beforeCreate() {
		VersaoService.getVersao()
			.then( (response) => {
				this.config = JSON.parse(JSON.stringify(response.data));
			})
			.catch((error) => {
				console.error(error);
			});
	}
};

</script>

<style lang="less" scoped>

	#container-versao {
		align-content: center;
		background: url("../../assets/img/bg_login.jpeg") no-repeat center fixed;
		background-size: cover;
		justify-content: center;
		max-width: 100% !important;
		padding: 0;
		position: fixed;
	}

</style>