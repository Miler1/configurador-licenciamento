import FinalidadeEnum from '../enums/finalidadeEnum.js';

const mapFinalidadeEnum = function() {

	let finalidades = new Map();

	FinalidadeEnum.forEach(finalidade => {
		finalidades.set(finalidade.value, finalidade.text);
		finalidades.set(finalidade.text, finalidade.value);
	});

	return finalidades;
};

export default mapFinalidadeEnum;