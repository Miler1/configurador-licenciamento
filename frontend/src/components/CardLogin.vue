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
						label.label-login CPF
						v-text-field#QA-btn-cpf.pa-0.ma-0(
							solo,
							dense,
							v-model="usuarioAutenticacao.login",
							v-on:keyup.enter="handleLogar",
							v-mask="'###.###.###-##'",
							placeholder="Digite seu CPF",
							name="cpf",
							type="text",
							color="#84A98C",
							append-icon="mdi-account",
							:error-messages="cpfError()",
							:error="errors.login && (!isEmpty(usuarioAutenticacao.login) || !isEmpty(usuarioAutenticacao.password))"
							@click.native="resetCpfError",
						)
						label.label-login Senha
						v-text-field#QA-btn-senha.pa-0.ma-0(
							solo,
							dense,
							v-model='usuarioAutenticacao.password',
							placeholder="Digite sua senha",
							v-on:keyup.enter="handleLogar",
							name="senha",
							:type="show ? 'text' : 'password'",
							@click:append='show = !show',
							color="#84A98C",
							:error-messages="senhaError()",
							:append-icon="usuarioAutenticacao.password ? (show ? 'mdi-eye' : 'mdi-eye-off') : 'mdi-key'",
							@click.native="resetSenhaError",
						)
				v-card-actions.pa-0
					v-btn#QA-btn-login(width="100%", @click='handleLogar')
						v-icon(left) mdi-login
						span Entrar
			v-col#col-esqueci-senha(cols="12", align="start")
				a#link-esquecida-senha(:href="urlEntradaUnica") Esqueci minha senha

</template>

<script>

import LoginService from '../services/login.service';

export default {

	name: "CardLogin",

	data: () => {
	
		return {
			show: false,
			urlEntradaUnica: null,
			
			usuarioAutenticacao: {
				login: null,
				password: null,
			},

			errors:{
				required:{
					senha: false,
					cpf: false,
				},
				message: '',
				login: false,
			},
		}
	},

	methods: {
	
		handleLogar() {
		
			if (this.validNotEmpty()) {
			
				LoginService.logar(this.usuarioAutenticacao)
					.then((response) => {
						this.$router.push('/');
					})
					.catch(error => {
					
						console.error(error);
						this.errors.login = true;
						this.errors.message = error.message;
						
					});
			} else {
			
				this.errors.required.cpf = true;
				this.errors.required.senha = true;
				this.errors.message = 'Obrigatório';
				
			}
		},
		
		validNotEmpty() {
		
			return this.usuarioAutenticacao.login
				&& this.usuarioAutenticacao.login != ''
				&& this.usuarioAutenticacao.password
				&& this.usuarioAutenticacao.password != '';
				
		},
		
		resetCpfError() {
		
			this.resetLoginError();
			this.errors.required.cpf = false;
			
		},
		
		resetSenhaError() {
		
			this.resetLoginError();
			this.errors.required.senha = false;
			
		},
		
		resetLoginError() {
		
			if (this.errors.login) {
				this.errors.login = false;
			}
			
		},
		
		cpfError() {
			return this.errors.required.cpf && !this.usuarioAutenticacao.login ? this.errors.message : [];
		},
		
		senhaError() {
		
			if ( (this.errors.required.senha && !this.usuarioAutenticacao.password) ||
				(this.errors.login && this.usuarioAutenticacao.password && this.usuarioAutenticacao.login)){

				return this.errors.message;
			}
			return [];
			
		},
		
		isEmpty(value) {
			return (value === '' || value === null);
		},
	},

	mounted() {
		this.urlEntradaUnica = process.env.VUE_APP_URL_PORTAL_SEGURANCA;
	},
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
