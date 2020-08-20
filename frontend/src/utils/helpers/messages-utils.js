const ERROR_MESSAGES = {

	logout: 'Erro ao realizar logout.',

	cnae: {
		cadastro: 'Erro ao cadastrar CNAE.',
		listagem: 'Erro ao listar os CNAEs.',
		editar: 'Erro ao editar o CNAE.',
		ativar: "Erro ao ativar o CNAE.",
		desativar: "Erro ao desativar o CNAE."
	},

	licenca: {
		cadastro: "Erro ao cadastrar licença.",
		editar: 'Erro ao editar a licença.',
		ativar: "Erro ao ativar a licença.",
		listagem: 'Erro ao listar as licenças.',
		desativar: "Erro ao desativar a licença."
	},

	tipologia: {
		cadastro: "Erro ao cadastrar tipologia.",
		listagem: 'Erro ao listar as tipologias.',
		editar: 'Erro ao editar a tipoloigia.',
		ativar: "Erro ao ativar a tipologia.",
		desativar: "Erro ao desativar a tipologia.",
	}
};

const SUCCESS_MESSAGES = {

	cadastro: "Sucesso! As informações foram salvas.",
	editar: "Sucesso! As informações foram editadas.",

	cnae: {
		ativar: "Sucesso! O CNAE foi ativado.",
		desativar: "Sucesso! O CNAE foi desativado.",
	},

	licenca: {
		ativar: "Sucesso! A licença foi ativada.",
		desativar: "Sucesso! A licença foi desativada."
	},

	tipologia: {
		ativar: "Sucesso! A tipologia foi ativada.",
		desativar: "Sucesso! A tipologia foi desativada."
	}
};

export {
	ERROR_MESSAGES,
	SUCCESS_MESSAGES
};
