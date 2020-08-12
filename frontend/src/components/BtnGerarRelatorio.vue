<template lang="pug">

	v-row
		v-col
			v-btn#QA-btn-gerar-relatorio.float-right(@click="gerarRelatorio", large, outlined, color="#84A98C")
				v-icon mdi-download
				span Gerar Relatório

	
  
</template>

<script>

export default {

	props: {
		gerarRelatorio: {
			type: [Function]
		}
	}

}

</script>

<style>

</style>