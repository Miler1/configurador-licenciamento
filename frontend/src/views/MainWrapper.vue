<template lang="pug">

#main-wrapper
    v-app
        MenuLateral

</template>

<script>

import MenuLateral from '@/components/MenuLateral.vue'

export default {

	name: 'MainWrapper',

	components: {MenuLateral}
}

</script>

<style lang="less" scoped>

#main-wrapper {
  height: 100vh;
}

</style>