<template lang="pug">

v-navigation-drawer.teste(v-model='drawer' :mini-variant.sync='mini' dark, color='#84A98C')
	v-list-item.px-2.mb-8.mt-3
		v-btn(icon, @click.stop='mini = !mini')
			v-icon mdi-menu
		v-img(contain :src="require('@/assets/img/logo_config_branca.png')" height="50px", width="180px")
	

	v-list(dense)
		v-list-item-group(v-model="active", dark, mandatory, color="#fff")
			template(v-for='item in items')
				v-list-group(v-if='item.children' :key='item.title' v-model='!item.model' :append-icon="item.model ? 'mdi-chevron-down' : 'mdi-chevron-up'" color="#fff")
					template(v-slot:activator)
						v-list-item-icon
							v-icon {{ item.icon }}
						v-list-item-content
							v-list-item-title
								| {{ item.title }}
					v-list-item.pl-12(v-for='(child, i) in item.children' :key='child.title' link='')
						v-list-item-icon(v-if='child.icon')
							v-icon {{ child.icon }}
						v-list-item-content
							v-list-item-title
								| {{ child.title }}
				v-list-item(v-else :key='item.title' link='')
					v-list-item-icon
						v-icon {{ item.icon }}
					v-list-item-content
						v-list-item-title
							| {{ item.title }}
</template>

<script>

export default {

	name: 'MenuLateral',

	data: () => ({
		drawer: true,
		items: [
			{ title: 'CNAE', icon: 'mdi-home-city', path: 'teste'},
			{ title: 'Licença', icon: 'mdi-account', path: 'tese'},
			{ title: 'Municípios capacitados', icon: 'mdi-account-group-outline', path: 'tese'},
			{ title: 'Tipologia', icon: 'mdi-home-city', path: 'teste'},
			{ title: 'Parâmetros', icon: 'mdi-home-city', path: 'teste'},
			{ title: 'Taxa', icon: 'mdi-home-city', path: 'teste'},
			{ title: 'Requisitos', icon: 'mdi-home-city', model: true,
				children: [
					{ title: 'Requisitos administrativos', path: 'teste' },
					{ title: 'Requisitos técnicos', path: 'teste' }
				],
			},
			{ title: 'Atividades licenciáveis', icon: 'mdi-home-city', path: 'teste'},
			{ title: 'Atividades dispensáveis', icon: 'mdi-home-city', model: true,
				children: [
					{ title: 'Perguntas', path: 'teste' },
					{ title: 'CNAEs dispensáveis', path: 'teste' }
				],
			}
		],
		mini: false,
		active: 0
	}),
}

</script>

<style lang="less">

</style>