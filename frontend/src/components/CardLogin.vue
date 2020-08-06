<template lang="pug">

	v-card.elevation-12(id="card-login")
		v-row(class="ma-0 pa-5", id="row-login")
			v-col(id="col-logo", cols="12")
				v-img(
					:src="require('@/assets/img/logo_configurador_licenciamento.png')"
					height="116px",
					width="310px"
				)

			v-col(class="pa-0", cols="12")

				v-card-text(class="pa-0")
					v-form(id="form-login")

						label(class="label-login") CPF
						v-text-field(
							solo,
							dense,
							name="cpf",
							type="text",
							class="pa-0 ma-0",
							id="QA-btn-cpf"
						)

							v-icon(slot="append") mdi-account

						label(class="label-login") Senha
						v-text-field(
							solo,
							dense,
							name="senha",
							type="password",
							class="pa-0 ma-0",
							id="QA-btn_senha"
						)

							v-icon(slot="append") mdi-key

				v-card-actions(class="pa-0")
					v-btn(id="QA-btn-login", width="100%")
						v-icon(left) mdi-login
						span Entrar

			v-col(id="col-esqueci-senha", cols="12", align="start")
				a(id="link-esquecida-senha", href="") Esqueci minha senha

</template>

<script>

export default {
	name: "CardLogin"
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

					.v-icon {
						color: @icon-color;
					}
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
