<template lang="pug">

v-navigation-drawer.teste(app v-model='drawer', :mini-variant.sync='mini', dark, color='#84A98C')
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
					v-list-item.pl-12(v-for='(child, i) in item.children' :key='child.title' :to='child.path')
						v-list-item-icon(v-if='child.icon')
							v-icon {{ child.icon }}
						v-list-item-content
							v-list-item-title
								| {{ child.title }}
				v-list-item(v-else :key='item.title' :to='item.path')
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
			{ title: 'CNAE', icon: 'mdi-home-city', path: 'cnae'},
			{ title: 'Licença', icon: 'mdi-card-account-details', path: 'licenca'},
			{ title: 'Municípios capacitados', icon: 'mdi-city', path: 'municipios-capacitados'},
			{ title: 'Tipologia', icon: 'mdi-format-list-bulleted-square', path: 'tipologia'},
			{ title: 'Parâmetros', icon: 'mdi-home-city', path: 'parametros'},
			{ title: 'Taxa', icon: 'mdi-cash-usd-outline', path: 'taxa'},
			{ title: 'Requisitos', icon: 'mdi-clipboard-list', model: true,
				children: [
					{ title: 'Requisitos administrativos', path: 'requisitos-administrativos'},
					{ title: 'Requisitos técnicos', path: 'requisitos-tecnicos' }
				],
			},
			{ title: 'Atividades licenciáveis', icon: 'mdi-account-multiple-plus', path: 'atividades-licenciaveis'},
			{ title: 'Atividades dispensáveis', icon: 'mdi-account-multiple-remove', model: true,
				children: [
					{ title: 'Perguntas', path: 'perguntas' },
					{ title: 'CNAEs dispensáveis', path: 'cnaes-dispensaveis' }
				],
			}
		],
		mini: false,
		active: 1
	}),
}

</script>

<style lang="less">

.v-list-item {
	.v-list-group__header__append-icon {

		margin: 0 !important;
    	min-width: 25px !important;

	}
}

</style>