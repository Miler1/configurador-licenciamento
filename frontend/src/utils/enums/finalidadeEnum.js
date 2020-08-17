const FinalidadesEnum =  Object.freeze(
		[
			{value: 'ATUALIZACAO', text:'Atualização'},
			{value: 'CADASTRO', text: 'Cadastro'},
			{value: 'DISPENSA', text: 'Dispensa'},
			{value: 'RENOVACAO', text: 'Renovação'},
			{value: 'SOLICITACAO', text: 'Solicitação'}
		]
);

const getMap = function() {

	let finalidades = new Map();

	FinalidadesEnum.forEach(finalidade => {
		finalidades.set(finalidade.value, finalidade);
	});

	return finalidades;
};

export default {FinalidadesEnum, getMap};