module.exports = {
	"devServer": {
		"proxy": "http://localhost:9909/"
	},
	"runtimeCompiler": true,
	"publicPath": process.env.NODE_ENV === 'production' ? '/configurador' : '/',
	"outputDir": "../backend/src/main/resources/static",
	"transpileDependencies": [
		"vuetify"
	]
}