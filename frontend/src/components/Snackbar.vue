<template lang="pug">
	v-snackbar(v-model="show", :multi-line="multiLine", :top="top", :right="right")
		| {{message}}
		v-btn(flat icon color="accent" @click.native="show = false")
			v-icon mdi-close

</template>

<script>

export default {
	data: () => ({
		show: false,
		multiLine: true,
		top: true,
		right: true,
	})
}

</script>