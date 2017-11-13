<template>
  <demo-page label="新增自行车">
    <div>
      <vue-toast ref='toast'></vue-toast>
    </div>
    <md-input-container>
      <md-input></md-input>
    </md-input-container>
    <div>
      <baidu-map class="map"></baidu-map>
    </div>
  </demo-page>

</template>

<style>
  /* 地图容器必须设置宽和高属性 */
  .map {
    width: 400px;
    height: 300px;
  }
</style>

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
  // 百度地图API功能
  //  var map = new BMap.Map("allmap");
  //  map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
  //  function showInfo(e){
  //    alert(e.point.lng + ", " + e.point.lat);
  //  }
  //  map.addEventListener("click", showInfo);
</script>

<style lang="stylus">
</style>
