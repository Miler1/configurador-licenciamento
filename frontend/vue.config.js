module.exports = {
	"devServer": {
		"proxy": "http://localhost:9909/"
	},
	"runtimeCompiler": true,
	"publicPath": "/",
	"outputDir": "../backend/src/main/resources/static",
	"transpileDependencies": [
		"vuetify"
	]
}