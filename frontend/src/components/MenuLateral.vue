<template lang="pug">

v-navigation-drawer(v-model='drawer' :mini-variant.sync='mini' permanent)
	v-list-item.px-2
		v-btn(icon, @click.stop='mini = !mini')
			v-icon mdi-menu
		v-list-item-title John Leider
	v-list(dense)
		v-list-item(v-for='item in items' :key='item.title' :to='item.path')
			v-list-item-icon
				v-icon {{ item.icon }}
			v-list-item-content
				v-list-item-title {{ item.title }}

</template>

<script>

export default {

	name: 'MenuLateral',

	data () {
		return {
			drawer: true,
			items: [
				{ title: 'Home', icon: 'mdi-home-city', path: 'teste' },
				{ title: 'My Account', icon: 'mdi-account', path: 'tese' },
				{ title: 'Users', icon: 'mdi-account-group-outline', path: 'tese' },
			],
			mini: true,
		}
	}
}

</script>

<style>

</style>