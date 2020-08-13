const RelatorioService = {

	baixarRelatorio: async (url) => {

		let src = process.env.VUE_APP_URL_CONFIGURADOR + url;
		window.open(src);
	}

};

export default RelatorioService;