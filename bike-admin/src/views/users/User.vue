<template>
  <demo-page label="客户管理">
    <div>
      <vue-toast ref='toast'></vue-toast>
    </div>
    <md-button class="md-fab md-fab-top-right md-mini" style="top:38px;right:38px;z-index:10;">
      <md-icon>add</md-icon>
    </md-button>
    <md-progress v-if="remoteRequest" md-indeterminate></md-progress>
    <md-table-card style="box-shadow:none;">
      <md-table>
        <md-table-header>
          <md-table-row>
            <md-table-head>ID</md-table-head>
            <md-table-head>手机号</md-table-head>
            <md-table-head>操作</md-table-head>
          </md-table-row>
        </md-table-header>
        <md-table-body>
          <md-table-row v-for="item in users"
                        v-bind:data="item"
                        v-bind:key="item.id">
            <md-table-cell>{{item.id}}</md-table-cell>
            <md-table-cell>{{item.cellNo}}</md-table-cell>
            <md-table-cell>
              <md-button class="md-icon-button">
                <md-icon>delete</md-icon>
              </md-button>
            </md-table-cell>
          </md-table-row>
        </md-table-body>
      </md-table>
      <md-table-pagination
        md-size="10"
        md-total="5"
        md-page="1"
        md-label="Rows"
        md-separator="of"
        :md-page-options="[]"
      ></md-table-pagination>
    </md-table-card>
  </demo-page>

</template>

<script type="application/ecmascript">
  import api from '../../constant/api'
  import * as types from '../../store/types'

  export default {
    name: 'Users',
    data () {
      return {
        users: [],
        remoteRequest: false
      }
    },
    mounted(){
      this.$store.commit(types.TITLE, '客户管理');
      this.getUsers();
    },

    methods: {
      getUsers(){
        this.remoteRequest = true;
        this.axios.get(api.users)
          .then(response => {
            this.users = response.data;
            this.remoteRequest = false;
          }).catch(err => {
          this.remoteRequest = false;
          const toast = this.$refs.toast
          toast.showToast('网络错误', {})
        })
      }
    }
  }
</script>

<style lang="stylus">
</style>
