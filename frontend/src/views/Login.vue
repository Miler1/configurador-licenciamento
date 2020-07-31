<template lang="pug">
	form
		label nome
		input(type="text")
		label senha
		input(type="text")
		router-view
</template>

<script>
export default {
	name: 'Login'
}
</script>