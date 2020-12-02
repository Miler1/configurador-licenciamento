<template lang="pug">

	v-card#card-versao.elevation-12
		v-row#row-versao.ma-0.pa-5
			v-col#col-logo.d-flex.justify-center.pa-0(cols="12")
				v-img.ma-4(
					:src="require('@/assets/img/logo_configurador_licenciamento.png')"
					height="116px",
					width="310px"
				)
			v-col.px-0.mb-4(cols="12")
				h2(style="text-align: center; text-transform: uppercase") {{config.name}}

			v-col.px-0.d-flex.justify-space-between(cols="12")
				span BRANCH / TAG
				span {{config.release}}
			v-col.px-0.d-flex.justify-space-between(cols="12")
				span DATA DO DEPLOY
				span {{config.update}}
			v-col.px-0.d-flex.justify-space-between(cols="12")
				span PATH
				span {{config.path}}
			v-col.px-0.d-flex.justify-space-between(cols="12")
				span VERSÃO SPRING
				span {{config.spring}}
			v-col.px-0.d-flex.justify-space-between(cols="12")
				span VERSÃO JAVA
				span {{config.java}}

</template>

<script>

export default {

	name: 'CardVersao',

	props: {
		config: {
			type: [Object]
		},
	},

};
</script>

<style lang="less" scoped>

@import "../../assets/css/variaveis.less";

	#card-versao{

		background-color: rgba(255, 255, 255, 0.91);
		border: 1px solid @border-components;
		border-radius: 10px;
		height: auto;
		width: 425px;

		#row-versao {
			flex-direction: column;
		}
	}

</style>