const HEADERCNAE = [
	{ text: 'Código', value: 'codigo', width: '155px'},
	{ text: 'Nome', value: 'nome'}
];

const HEADERPARAMETRO = [
	{ text: 'Parâmetro 1', value: 'parametroUm.codigo', sortable: false},
	{ text: 'Valor mínimo', value: 'limiteInferiorUm', sortable: false},
	{ text: 'Valor máximo', value: 'limiteSuperiorUm', sortable: false},
	{ text: 'Parâmetro 2', value: 'parametroDois.codigo', sortable: false},
	{ text: 'Valor mínimo', value: 'limiteInferiorDois', sortable: false},
	{ text: 'Valor máximo', value: 'limiteSuperiorDois', sortable: false},
    { text: 'Porte', value: 'porte.nome', width: '100px', sortable: false},
	{ text: 'Licenciamento municipal', value: 'licenciamentoMunicipal', sortable: false, align: 'center', width: '140px'},
	{ text: 'Repasse para outro órgão', value: 'repasseOutroOrgao', sortable: false, align: 'center', width: '140px'}
];

export {
	HEADERCNAE,
	HEADERPARAMETRO
};
