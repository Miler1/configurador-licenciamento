<template lang="pug">

	v-card.elevation-12(id="card-login")
		v-row(class="ma-0 pa-5", id="row-login")
			v-col(id="col-logo", cols="12")
				v-img(:src="require('@/assets/img/logo_configurador_licenciamento.png')"
					height="116px",
					width="310px")

			v-col(class="pa-0", cols="12")

				v-card-text(class="pa-0")
					v-form(id="form-login")

						label(class="label-login") CPF
						v-text-field(solo,
							dense,
							v-model='usuarioAutenticacao.login'
							name="cpf",
							type="text",
							class="pa-0 ma-0",
							id="QA-btn_cpf",
							append-icon='mdi-account',
							color="grey",
							:error='!this.autenticacoValida'
							:rules='[rules.required]',)

						label(class="label-login") Senha
						v-text-field(solo,
							dense,
							v-model='usuarioAutenticacao.password'
							name="senha",
							:rules='[rules.required]',
							:type="show? 'text' : 'password'"
							class="pa-0 ma-0",
							id="QA-btn_senha",
							@click:append='show = !show',
							color="grey",
							:error-messages = "this.autenticacoValida ? [] : this.messageError",
							:append-icon="usuarioAutenticacao.password ? (show ? 'mdi-eye' : 'mdi-eye-off') : 'mdi-key'")

				v-card-actions(class="pa-0")
					v-btn(id="QA-btn_login", width="100%", @click='handleLogar')
						v-icon(left) mdi-login
						span(id="teste") Entrar

			v-col(id="col-esqueci-senha", cols="12", align="start")
				a(id="link-esquecida-senha", href="") Esqueci minha senha

</template>

<script>

import LoginService from '../services/login.service';
import { SET_SNACKBAR } from '../store/actions.type'

export default {

	name: "CardLogin",

	data: () => {
		return {
			autenticacoValida: true,
			show: false,
			messageError: '',
			usuarioAutenticacao: {
				login: null,
				password: null
			},
			rules: {
				required: value => !!value || 'Obrigatório.',
			}
		}
	},

	methods: {
		validNotEmpty() {
			return this.usuarioAutenticacao.login 
				&& this.usuarioAutenticacao.login != ''
				&& this.usuarioAutenticacao.password
				&& this.usuarioAutenticacao.password != '';
		},

		handleLogar() {
			if(this.validNotEmpty()) {
				LoginService.logar(this.usuarioAutenticacao)
					.then((response) => {
						this.$router.push('/');
					})
					.catch(erro => {
						this.autenticacoValida = false;
						this.messageError = erro.message;
					});
			}
		}
	}


}

</script>

<style lang="less" scoped>
@import "../assets/css/variaveis.less";

	#card-login{
		background-color: rgba(255, 255, 255, 0.91);
		border: 1px solid @border-components;
		border-radius: 10px;
		height: auto;
		width: 350px;

		#row-login {
			flex-direction: column;

			#col-logo {
				padding: 0 0 20px 0;
			}

			#form-login {
				align-content: start;
				color: @text-color;
				font-size: 14px;

				.label-login {
					align-content: start;
				}

				.v-text-field {
					font-size: 16px;
				}

				.v-icon {
					color: @icon-color;
				}
			}

			#QA-btn_login {
				background-color: @bg-color-default;
				color: @btn-text-color;
				font-size: 14px;
				text-transform: none !important;
			}

			#col-esqueci-senha {
				padding: 20px 0 0 0 !important;

				#link-esqueci-senha {
					color: @info;
					font-size: 13px;
				}
			}
		}
	}

</style>
