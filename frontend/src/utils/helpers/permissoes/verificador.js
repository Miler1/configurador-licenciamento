import Index from '@/store/index.js'
import ROTA_PERMISSAO from '@/utils/helpers/permissoes/rotas/permissao.js'

export function verificarPermissao (permissoes, redirecionarParams) {
	let qtdPermissoes
	let permissoesAceitas = 0

	const usuario = Index.getters.usuarioLogado

	if (permissoes && usuario) {
		qtdPermissoes = permissoes.length
		permissoes.forEach(permissao => {
			usuario.perfilSelecionado.permissoes.forEach(usuarioPermissao => {
				if (permissao === usuarioPermissao.codigo) {
					permissoesAceitas++
				}
			})
		})
	}

	if (permissoesAceitas === qtdPermissoes && usuario) {
		return true
	} else {
		if (usuario && redirecionarParams && redirecionarParams.rota === undefined) {
			this.$router.push({ name: redirecionarParams })
		} else if (usuario && redirecionarParams && redirecionarParams.rota && redirecionarParams.params) {
			this.$router.push({ name: redirecionarParams.rota, params: redirecionarParams.params })
		} else {
			return false
		}
	}
}

export function verificaPermissaoPelaRota (nomeDaRota) {
	const usuario = Index.getters.usuarioLogado

	return usuario && ROTA_PERMISSAO[nomeDaRota] &&
    usuario.perfilSelecionado.permissoes.filter(p => {
    	return p.codigo === ROTA_PERMISSAO[nomeDaRota].PERMISSAO
    }).length > 0
}