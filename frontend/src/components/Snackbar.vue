<template lang="pug">

	v-snackbar(v-model="show")
		| {{message}}
		v-btn(flat icon color="accent" @click.native="show = false")
			v-icon mdi-close

</template>

<script>

export default {
	data () {
		return {
			show: false,
			message: ''
		};
	}
};

</script>