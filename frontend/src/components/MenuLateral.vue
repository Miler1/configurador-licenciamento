<template lang="pug">

v-navigation-drawer(app v-model='drawer', :mini-variant.sync='mini', dark, color='#84A98C')
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
							v-icon(v-if='item.icon.vuetify') {{ item.icon.value }}
							i(v-else :class='item.icon.value').fs-21.pl-1
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
						v-icon(v-if='item.icon.vuetify') {{ item.icon.value }}
						i(v-else :class='item.icon.value').fs-21.pl-1
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
			{ title: 'CNAE', icon: {vuetify: true, value: 'fa fa-industry'}, path: '/home/cnae'},
			{ title: 'Licença', icon: {vuetify: true, value: 'mdi-card-account-details'}, path: '/home/licenca'},
			/*{ title: 'Municípios capacitados', icon: {vuetify: true, value: 'mdi-city'}, path: 'municipios-capacitados'},*/
			{ title: 'Tipologia', icon: {vuetify: true, value: 'mdi-format-list-bulleted-square'}, path: '/home/tipologia'},
			{ title: 'Parâmetros', icon: {vuetify: false, value: 'fa fa-sliders'}, path: '/home/parametro'},
			{ title: 'Taxa', icon: {vuetify: true, value: 'mdi-cash-usd-outline'}, path: '/home/taxa'},
			{ title: 'Requisitos', icon: {vuetify: true, value: 'mdi-clipboard-list'}, model: true,
				children: [
					{ title: 'Documentos', path: '/home/documentos'},
					{ title: 'Requisitos administrativos', path: '/home/requisitos-administrativos'},
					{ title: 'Requisitos técnicos', path: '/home/requisitos-tecnicos' }
				],
			},
			{ title: 'Atividades licenciáveis', icon: {vuetify: true, value: 'mdi-account-multiple-plus'}, path: '/home/atividades-licenciaveis'},
			{ title: 'Atividades dispensáveis', icon: {vuetify: true, value: 'mdi-account-multiple-remove'}, model: true,
				children: [
					{ title: 'Perguntas', path: '/home/perguntas' },
					{ title: 'CNAEs dispensáveis', path: '/home/cnaes-dispensaveis' }
				],
			}
		],
		mini: false,
		active: 1
	}),
};

</script>

<style lang="less">

.v-list-item {
	.v-list-group__header__append-icon {

		margin: 0 !important;
    	min-width: 25px !important;

	}
}

.fs-21 {
	font-size: 21px;
}

</style>