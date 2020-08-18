import FinalidadeEnum from '../enums/finalidadeEnum.js';

let finalidades = new Map();

const text = function() {

	FinalidadeEnum.forEach(finalidade => {
		finalidades.set(finalidade.value, finalidade.text);
	});

	return finalidades;
};

const value = function() {

	FinalidadeEnum.forEach(finalidade => {
		finalidades.set(finalidade.text, finalidade.value);
	});

	return finalidades;
};

export default {text, value};