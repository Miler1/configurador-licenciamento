import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ApiService from './services/api.service'
import vuetify from './plugins/vuetify';
import { VueMaskDirective } from 'v-mask'

Vue.config.productionTip = false
Vue.directive('mask', VueMaskDirective);

ApiService.init().then(() => {
	new Vue({
		router,
		store,
		vuetify,
		render: h => h(App)
	}).$mount('#app')
})