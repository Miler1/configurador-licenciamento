<template lang="pug">

v-navigation-drawer(app v-model='drawer', :mini-variant.sync='mini', dark, color='#84A98C')
	v-list-item.px-2.mb-8.mt-3
		v-btn(icon, @click.stop='mini = !mini')
			v-icon mdi-menu

		a(:href="homepage")
			v-img(contain :src="require('@/assets/img/logo_config_branca.png')" height="50px", width="180px")


	v-list(dense)
		v-list-item-group(v-model="active", dark, mandatory, color="#fff")
			template(v-for='(item, index) in items')
				v-list-group(v-if='item.children' :value="item.children[pos] !== undefined ? menuSelected[1] === item.children[pos].path : ''" :key='item.title' color="#fff")
					template(v-slot:activator)
						v-tooltip(top, open-on-click :key='index')
							template(v-slot:activator="{ on, attrs }")
								v-list-item-icon(v-on="on" v-bind="attrs" @click="menuActionClick(items, index)")
									v-icon(v-if='item.icon.vuetify') {{ item.icon.value }}
									i(v-else :class='item.icon.value').fs-21.pl-1
								v-list-item-content(v-on="on" v-bind="attrs" @click="menuActionClick(items, index)")
									v-list-item-title
										| {{ item.title }}

							div(v-if="selected[index]")
								span Ocultar menu
							div(v-if="!selected[index]")
								span Expandir menu
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
		homepage: null,
		selected: [],
		menuSelected: null,
		pos: null,
		items: [
			{ title: 'CNAE', icon: {vuetify: true, value: 'fa fa-industry'}, path: '/home/cnae'},
			{ title: 'Licença', icon: {vuetify: true, value: 'mdi-file-check-outline'}, path: '/home/licenca'},
			/*{ title: 'Municípios capacitados', icon: {vuetify: true, value: 'mdi-city'}, path: 'municipios-capacitados'},*/
			{ title: 'Tipologia', icon: {vuetify: true, value: 'mdi-format-list-bulleted-square'}, path: '/home/tipologia'},
			{ title: 'Parâmetro', icon: {vuetify: false, value: 'fa fa-sliders'}, path: '/home/parametro'},
			{ title: 'Taxas', icon: {vuetify: true, value: 'mdi-cash-usd-outline'}, model: true,
				children: [
					{title: 'Taxa administrativa', path: '/home/taxa-administrativa'},
					{title: 'Taxa de licenciamento', path: '/home/taxa-licenciamento'}
				],
			},
			{ title: 'Requisitos', icon: {vuetify: true, value: 'mdi-clipboard-list'}, model: true,
				children: [
					{ title: 'Documento', path: '/home/documentos'},
					{ title: 'Requisito administrativo', path: '/home/requisitos-administrativos'},
					{ title: 'Requisito técnico', path: '/home/requisitos-tecnicos' }
				],
			},
			{ title: 'Atividade licenciável', icon: {vuetify: true, value: 'mdi-domain-plus'}, model: true, path: '/home/atividades-licenciaveis'},
			{ title: 'Atividade dispensável', icon: {vuetify: true, value: 'mdi-domain-remove'}, model: true,
				children: [
					{ title: 'Pergunta', path: '/home/perguntas' },
					{ title: 'CNAE dispensável', path: '/home/cnaes-dispensaveis' }
				],
			}
		],
		mini: false,
		active: 1
	}),

	methods: {

		menuActionClick(items, index) {

			items.forEach((item, i) => {

				if (item.children) {

					if (this.selected[i] != this.selected[index]) {
						this.selected[i] = false;
					}

				}

			});

			this.selected[index] = !this.selected[index];

		},

	},

	created() {

		this.menuSelected = window.location.hash.split('#');

		this.items.forEach((item, index) => {

			if (item.children) {

				if (item.children.findIndex(e => e.path === this.menuSelected[1]) > -1) {

					this.pos = item.children.findIndex(e => e.path === this.menuSelected[1]);
					this.selected[index] = !this.selected[index];

				}

			}

		});

		this.homepage = window.location.origin + '/#/home/cnae';

	}

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