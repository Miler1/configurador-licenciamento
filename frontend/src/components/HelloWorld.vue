<template lang="pug">

	button(@click='chamabackend')
		| teste

</template>

<script>
import TesteService from '@/services/teste.service'

export default {
	name: 'HelloWorld',
	props: {
		msg: String,
		default: {}
	},
	methods: {
		chamabackend () {
			TesteService.baixarManual().then((response) => { console.log(response) })
		}
	}

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
h3 {
	margin: 40px 0 0;
}
ul {
	list-style-type: none;
	padding: 0;
}
li {
	display: inline-block;
	margin: 0 10px;
}
a {
	color: #42b983;
}
</style>
