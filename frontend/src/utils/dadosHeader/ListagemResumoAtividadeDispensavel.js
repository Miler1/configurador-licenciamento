const HEADERCNAE = [
	{ text: 'Código', value: 'cnae.codigo'},
	{ text: 'Nome', value: 'cnae.nome'},
	{ text: 'Tipologia', value: 'tipologia.nome'},
	{ text: 'Atividade fora do município', value: 'foraMunicipio'}
];

const HEADERPERGUNTA = [
	{ text: 'Pergunta', value: 'texto' },
	{ text: 'Resposta esperada', value: 'respostasEsperadas', sortable: false}
];

export {
	HEADERCNAE,
	HEADERPERGUNTA
};
