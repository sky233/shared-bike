<template>
  <div>
    <md-toolbar>
      <md-button class="md-icon-button" @click="toggleLeftSidenav">
        <md-icon>menu</md-icon>
      </md-button>
      <h2 class="md-title" style="flex: 1">管理系统</h2>
      <md-menu>
        <md-button class="md-icon-button" md-menu-trigger>
          <md-icon>more_vert</md-icon>
        </md-button>
        <md-menu-content>
          <md-menu-item @click="loginOut">退出</md-menu-item>
        </md-menu-content>
      </md-menu>
    </md-toolbar>
    <md-sidenav class="md-left" ref="leftSidenav">
      <md-list>
        <template v-for="item in items">
          <md-list-item v-if="item.children">
            <md-icon>{{item.icon}}</md-icon>
            <span>{{item.name}}</span>
            <md-list-expand>
              <md-list v-for="it in item.children"
                       v-bind:data="it"
                       v-bind:key="it.name">
                <md-list-item class="md-inset" @click="closeLeftSidenav">
                  <router-link exact :to="it.path"> {{it.name}}</router-link>
                </md-list-item>
              </md-list>
            </md-list-expand>
          </md-list-item>
          <md-list-item v-else @click="closeLeftSidenav">
            <router-link exact :to="item.path">
              <md-icon>{{item.icon}}</md-icon>
              <span>{{item.name}}</span>
            </router-link>
          </md-list-item>
        </template>
      </md-list>
    </md-sidenav>
    <router-view></router-view>
  </div>
</template>
<script>
  import NAV from '../router/nav';

  export default {
    data () {
      return {
        items: NAV
      }
    },
    beforeCreate () {
    },
    created () {
    },
    beforeMount () {
    },
    mounted () {
    },
    beforeUpdate () {
    },
    updated () {
    },
    beforeDestory () {
    },
    destoryed () {
    },
    watch: {
      '$route' (nextRoute, route) {
      }
    },
    methods: {
      toggleLeftSidenav () {
        this.$refs.leftSidenav.toggle()
      },
      closeLeftSidenav () {
        this.$refs.leftSidenav.close()
      },
      loginOut () {
        this.$router.push('login')
      }
    }
  }
</script>
