<template>
  <el-form ref="form" :model="form" label-width="120px"
           style="margin:20px;width:60%;min-width:600px;">
    <el-form-item label="自行车编号">
      <el-input v-model="form.bikeNumber"></el-input>
    </el-form-item>
    <el-form-item label="位置">
      <el-input disabled v-model="showPosition" :formatter="formatPosition"></el-input>
    </el-form-item>
    <el-form-item label="投放位置">
      <baidu-map class="map-container" center="成都" :zoom="14" :scroll-wheel-zoom="true" @click='getClickMapData'>
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="true"></bm-overview-map>
        <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-marker :position="form.position" :dragging="true" animation="BMAP_ANIMATION_BOUNCE">
          <bm-label content="选择位置" :labelStyle="{color: 'red', fontSize : '24px'}" :offset="{width: -35, height: 30}"/>
        </bm-marker>
      </baidu-map>
    </el-form-item>
    <el-form-item label="备注">
      <el-input type="textarea" v-model="form.remark"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="onSubmit" type="primary">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
<style scoped>
  .map-container {
    width: 100%;
    height: 500px;
  }
</style>
<script>
  import {addBike} from '../../api/api';
  export default {
    data() {
      return {
        showPosition: '[0,0]',
        form: {
          bikeNumber: '',
          position: {lng: '0', lat: '0'},
          remark: ''
        }
      }
    },
    methods: {
      formatPosition(row, column){
        return "(" + row.position.lng + ',' + row.position.lat + ")";
      },
      getClickMapData({point}) {
        this.$info(point);
        this.form.position = Object.assign({}, point);
        this.showPosition = '[' + point.lng + ',' + point.lat + ']'
      },
      onSubmit() {
        this.$info('Submit!');
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              let params = Object.assign({}, this.form);
              addBike(params).then((res) => {
                this.$message({
                  message: '提交成功',
                  type: 'success'
                });
                this.$router.push({path: '/bikes'})
              });
            });
          }
        });
      }
    }
  }

</script>