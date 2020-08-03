<template lang="pug">

#main-wrapper
    MenuLateral

    MenuSuperior
    
    router-view

</template>

<script>

import MenuLateral from '@/components/MenuLateral.vue'
import MenuSuperior from '@/components/MenuSuperior.vue'

export default {

	name: 'MainWrapper',

	components: {MenuLateral, MenuSuperior}
}

</script>

<style lang="less" scoped>

</style>