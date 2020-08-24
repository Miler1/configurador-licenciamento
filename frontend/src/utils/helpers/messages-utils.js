const ER_BASE = 'Erro! Não foi possível ';

const ERROR_MESSAGES = {

	logout: 'Erro ao realizar logout.',

	cnae: {
		cadastro: ER_BASE + 'cadastrar o CNAE. ',
		listagem: ER_BASE + 'listar os CNAEs. ',
		editar: ER_BASE + 'editar o CNAE. ',
		ativar: ER_BASE + 'ativar o CNAE. ',
		desativar: ER_BASE + 'desativar o CNAE. '
	},

	licenca: {
		cadastro: ER_BASE + 'cadastrar a licença. ',
		editar: ER_BASE + 'editar a licença. ',
		ativar: ER_BASE + 'ativar a licença. ',
		listagem: ER_BASE + 'listar as licenças.',
		desativar: ER_BASE + 'desativar a licença. '
	},

	tipologia: {
		cadastro: ER_BASE + 'cadastrar a tipologia. ',
		listagem: ER_BASE + 'listar as tipologias. ',
		editar: ER_BASE + 'editar a tipoloigia. ',
		ativar: ER_BASE + 'ativar a tipologia. ',
		desativar: ER_BASE + 'desativar a tipologia. ',
	},

	parametro: {
		cadastro: ER_BASE + 'cadastrar o parâmetro. ',
		listagem: ER_BASE + 'listar os parâmetros. ',
		editar: ER_BASE + 'editar o parâmetro. ',
		ativar: ER_BASE + 'ativar o parâmetro. ',
		desativar: ER_BASE + 'desativar o parâmetro. ',
	},

	documento: {
		cadastro: ER_BASE + 'cadastrar o documento',
		listagem: ER_BASE + 'listar os documentos. ',
	},

	requisitoTecnico: {
		listagem: ER_BASE + 'listar os Requisitos Técnicos',
	}
};

const SUCCESS_MESSAGES = {

	cadastro: 'Sucesso! As informações foram salvas.',
	editar: 'Sucesso! As informações foram editadas.',

	cnae: {
		ativar: 'Sucesso! O CNAE foi ativado.',
		desativar: 'Sucesso! O CNAE foi desativado.',
	},

	licenca: {
		ativar: 'Sucesso! A licença foi ativada.',
		desativar: 'Sucesso! A licença foi desativada.'
	},

	tipologia: {
		ativar: 'Sucesso! A tipologia foi ativada.',
		desativar: 'Sucesso! A tipologia foi desativada.'
	},

	parametro: {
		ativar: 'Sucesso! O parâmetro foi ativado.',
		desativar: 'Sucesso! O parâmetro foi desativado.'
	}
	
};

export {
	ERROR_MESSAGES,
	SUCCESS_MESSAGES
};
