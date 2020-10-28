<template lang="pug">

#toggle-options
	v-col.pa-0
		v-label {{labelOption}}
	v-col.pa-0.mb-1
		v-btn-toggle(
				v-model="modelToggle",
				color="green lighten-4",
				:id="idToggle",
				@change="changeModel()"
			)
			v-btn(v-for="(option, index) in options",
				:key="index"
				:id="option.idOption"
				:color="color",
				:value="option.value",
				:width="option.width ? option.width : '100px'",
				:height="option.height ? option.height : '40px'",
				:disabled="disabled"
			)
				span {{option.label}}
	v-col.d-flex.pa-0
		span.v-messages.theme--light.error--text.v-messages__message.pl-3.mb-3
			| {{ errorMessage(modelToggle, true) }}

</template>

<script>
export default {

	name: "ToggleOptions",

	props: {
		labelOption:  {
			type: [String]
		},
		idToggle: {
			type: [String]
		},
		options: {
			type: [Array]
		},
		color: {
			type: [String],
			default: 'white'
		},
		errorMessage: {
			type: [Function]
		},
		change: {
			type: [Function]
		},
		disabled: {
			type: [Boolean],
			default: false
		}
	},

	data: () => {
		return {
			modelToggle: null
		};
	},

	methods: {
		changeModel() {

			if (this.change) {
				this.change(this.modelToggle);
			}
			this.$emit('changeOption', this.modelToggle);

		},

		clearModel() {
			this.modelToggle = null;
		},

		setModel(valueModel) {
			this.modelToggle = valueModel;
		}
	},

};
</script>

<style lang="less" scoped>

@import "../assets/css/variaveis.less";

.theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn.v-btn--active {
	border-color: @green-primary !important;
	border-left-width: 1px !important;

	span {
		color: @green-primary !important;
	}
}

</style>