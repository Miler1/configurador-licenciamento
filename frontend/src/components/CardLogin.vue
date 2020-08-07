<template lang="pug">

	v-card#card-login.elevation-12
		v-row#row-login.ma-0.pa-5
			v-col#col-logo(cols="12")
				v-img(
					:src="require('@/assets/img/logo_configurador_licenciamento.png')"
					height="116px",
					width="310px"
				)

			v-col.pa-0(cols="12")

				v-card-text.pa-0
					v-form#form-login

						label(class="label-login") CPF
						v-text-field#QA-btn-cpf.pa-0.ma-0(
							solo,
							dense,
							v-model='usuarioAutenticacao.login'
							name="cpf",
							type="text",
							color="#84A98C",
							class="pa-0 ma-0",
							append-icon='mdi-account',
							:error-messages = "this.autenticacoValida || usuarioAutenticacao.login ? [] : this.messageError",
							@click.native='resetErros',)

						label(class="label-login") Senha
						v-text-field#QA-btn-senha.pa-0.ma-0(
							solo,
							dense,
							v-model='usuarioAutenticacao.password'
							name="senha",
							:type="show? 'text' : 'password'"
							@click:append='show = !show',
							color="#84A98C",
							class="pa-0 ma-0",
							@click.native='resetErros',
							:error-messages = "this.autenticacoValida || usuarioAutenticacao.password ? [] : this.messageError",
							:append-icon="usuarioAutenticacao.password ? (show ? 'mdi-eye' : 'mdi-eye-off') : 'mdi-key'"
						)

				v-card-actions(class="pa-0")
					v-btn#QA-btn_login(width="100%", @click='handleLogar')
						v-icon(left) mdi-login
						span Entrar

			v-col#col-esqueci-senha(cols="12", align="start")
				a#link-esquecida-senha(href="") Esqueci minha senha

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
			} else {
				this.autenticacoValida = false;
				this.messageError = 'Obrigatório';
			}
		},

		resetErros() {
			this.autenticacoValida = true;
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

			#QA-btn-login {
				background-color: @green-primary;
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
