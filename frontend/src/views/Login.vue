<template lang="pug">

	v-container(class="fill-height", id="container-login")
		CardLogin

</template>

<script>
import CardLogin from "../components/CardLogin"

export default {
	name: "Login",
	components: {CardLogin},
	props: {
		source: String,
	},
	container: {
		CardLogin
	}
}

</script>

<style lang="less" scoped>

	#container-login {
		align-content: center;
		background: url("../assets/img/bg_login.png") no-repeat center fixed;
		background-size: 100% 100%;
		justify-content: center;
		max-width: 100% !important;
		padding: 0;
		position: fixed;
	}

</style>
