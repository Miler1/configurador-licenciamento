<template lang="pug">

	v-container#container-login.fill-height
		CardLogin

</template>

<script>

import CardLogin from "@/components/CardLogin";

export default {
	name: "Login",
	components: {CardLogin},
	container: {
		CardLogin
	},
	props: {
		source: String,
	}	
};

</script>

<style lang="less" scoped>

	#container-login {
		align-content: center;
		background: url("../assets/img/bg_login.jpeg") no-repeat center fixed;
		background-size: cover;
		justify-content: center;
		max-width: 100% !important;
		padding: 0;
		position: fixed;
	}

</style>
