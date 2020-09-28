const ER_BASE = 'Erro! Não foi possível ';

const ERROR_MESSAGES = {

	logout: 'Erro ao realizar logout.',
	unauthorized: 'Erro! Sua sessão expirou, faça login novamente.',

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
		editar: ER_BASE + 'editar a tipologia. ',
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

	pergunta: {
		cadastro: ER_BASE + 'cadastrar a pergunta. ',
		listagem: ER_BASE + 'listar as perguntas. ',
		editar: ER_BASE + 'editar a pergunta. ',
		ativar: ER_BASE + 'ativar a pergunta. ',
		desativar: ER_BASE + 'desativar a pergunta. '
	},

	documento: {
		cadastro: ER_BASE + 'cadastrar o documento. ',
		listagem: ER_BASE + 'listar os documentos. ',
		editar: ER_BASE + 'editar o documento. ',
		ativar: ER_BASE + 'ativar o documento. ',
		desativar: ER_BASE + 'desativar o documento. ',
	},

	requisitoAdministrativo: {
		cadastro: ER_BASE + 'cadastrar o requisito administrativo. ',
		listagem: ER_BASE + 'listar os requisitos administrativos. ',
		editar: ER_BASE + 'editar o requisito administrativo. ',
		ativar: ER_BASE + 'ativar o requisito administrativo. ',
		desativar: ER_BASE + 'desativar o requisito administrativo. ',
	},

	requisitoTecnico: {
		cadastro: ER_BASE + 'cadastrar o grupo de requisito técnico. ',
		editar: ER_BASE + 'editar o grupo de requisito técnico. ',
		listagem: ER_BASE + 'listar os grupos de requisitos técnicos. ',
		ativar: ER_BASE + 'ativar o grupo de requisito técnico. ',
		desativar: ER_BASE + 'desativar o grupo de requisito técnico. ',
		adicionar: ER_BASE + 'adicionar o requisito técnico. ',
	},

	taxaLicenciamento: {
		cadastro: ER_BASE + 'cadastrar a tabela de taxas de licenciamento. ',
		editar: ER_BASE + 'editar a tabela de taxas de licenciamento. ',
		listagem: ER_BASE + 'listar as tabelas de taxas de licenciamento. ',
		ativar: ER_BASE + 'ativar a tabela de taxas de licenciamento. ',
		desativar: ER_BASE + 'desativar a tabela de taxas de licenciamento. ',
		adicionarValores: ER_BASE + 'adicionar a taxa de licenciamento. ',
	},

	taxaAdministrativa: {
		cadastro: ER_BASE + 'cadastrar a taxa administrativa. ',
		editar: ER_BASE + 'editar a taxa administrativa. ',
		listagem: ER_BASE + 'listar as taxas administrativas. ',
		ativar: ER_BASE + 'ativar a taxa administrativa. ',
		desativar: ER_BASE + 'desativar a taxa administrativa. ',
	},

	atividadeDispensavel: {

		cadastro: ER_BASE + 'cadastrar o CNAE dispensável. ',
		editar: ER_BASE + 'editar o CNAE dispensável. ',
		listagem: ER_BASE + 'listar os CNAEs dispensáveis. ',
		ativar: ER_BASE + 'ativar o CNAE dispensável. ',
		desativar: ER_BASE + 'desativar o CNAE dispensável. ',
		desativarErro: "Não foi possível inativar o registro. Ele se encontra vinculado a uma atividade ativa no sistema. ",

		cnaes: {
			adicionarValores: ER_BASE + 'adicionar a relação CNAE/Tipologia. ',
			avancarEtapa: 'Não foi possível avançar a etapa. É necessário adicionar pelo menos uma relação. ',
		},

		perguntas: {
			avancarEtapa: 'Não foi possível avançar a etapa. É necessário adicionar pelo menos uma pergunta. ',
		},

	},

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
	},

	pergunta: {
		ativar: 'Sucesso! A pergunta foi ativada.',
		desativar: 'Sucesso! A pergunta foi desativada.'
	},

	documento: {
		ativar: 'Sucesso! O documento foi ativado.',
		desativar: 'Sucesso! O documento foi desativado.',
	},

	requisitoAdministrativo: {
		ativar: 'Sucesso! O requisito administrativo foi ativado.',
		desativar: 'Sucesso! O requisito administrativo foi desativado.',
	},

	requisitoTecnico: {
		ativar: 'Sucesso! O grupo de requisito técnico foi ativado.',
		desativar: 'Sucesso! O grupo de requisito técnico foi desativado.',
	},

	taxaAdministrativa: {
		ativar: 'Sucesso! A taxa administrativa foi ativada.',
		desativar: 'Sucesso! A taxa administrativa foi desativida.',
	},

	taxaLicenciamento: {
		ativar: 'Sucesso! A tabela de taxas de licenciamento foi ativada.',
		desativar: 'Sucesso! A tabela de taxas de licenciamento foi desativada.',
	},

	atividadeDispensavel: {
		ativar: 'Sucesso! O CNAE dispensável foi ativado.',
		desativar: 'Sucesso! O CNAE dispensável foi desativado.',
	},
	
};

export {
	ERROR_MESSAGES,
	SUCCESS_MESSAGES
};