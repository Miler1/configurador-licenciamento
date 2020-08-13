import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ApiService from './services/api.service';
import vuetify from './plugins/vuetify';
import VueSimpleAlert from "vue-simple-alert";

Vue.config.productionTip = false;

Vue.use(VueSimpleAlert);

ApiService.init().then(() => {
	new Vue({
		router,
		store,
		vuetify,
		render: h => h(App)
	}).$mount('#app');
});
