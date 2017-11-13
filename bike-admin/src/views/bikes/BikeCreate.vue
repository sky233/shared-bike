<template>
  <demo-page label="新增自行车">
    <div>
      <vue-toast ref='toast'></vue-toast>
    </div>
    <md-input-container>
      <md-input></md-input>
    </md-input-container>
  </demo-page>

</template>

<script type="application/ecmascript">
  import api from '../../constant/api'
  import * as types from '../../store/types'

  export default {
    name: 'BikesCreate',
    data () {
      return {
        bike: {},
      }
    },
    mounted(){
      this.$store.commit(types.TITLE, '新增自行车');
    },
    methods: {
      save () {

      }
    }
  }
</script>

<style lang="stylus">
</style>
