<template>
  <div>
    <md-whiteframe md-elevation="8" class="login-wrap">
      <form novalidate @submit.stop.prevent="login">
        <md-input-container>
          <label>用户名</label>
          <md-input v-model="user.username"></md-input>
          <span v-if="user.getField('username').hasErrors()">
      {{ user.getField('username').errors | firstMessage }}
    </span>
        </md-input-container>
        <md-input-container md-has-password>
          <label>密码</label>
          <md-input type="password" v-model="user.password"></md-input>
          <span v-if="user.getField('password').hasErrors()">
      {{ user.getField('password').errors | firstMessage }}
    </span>
        </md-input-container>
        <div>
          <md-button v-bind:disabled="user.hasErrors()"
                     type="submit"
                     class="md-raised md-primary login-btn">登录
          </md-button>
        </div>
      </form>
    </md-whiteframe>
  </div>
</template>
<script type="application/ecmascript">
  import * as types from '../store/types'
  import api from '../constant/api'

  export default {
    name: '',
    data () {
      return {
        user: new this.$models.User({
          vm: this,
          dataKey: 'user',
          username: ''
        })
      }
    },
    mounted(){
      this.$store.commit(types.TITLE, 'Login');
    },
    methods: {
      login(){
        this.$info('username:' + this.user.username);
        this.$info('password:' + this.user.password);
        this.axios.post(api.auth_login, JSON.stringify({
          username: this.user.username,
          password: this.user.password
        }), {
          headers: {
            'X-Requested-With': 'XMLHttpRequest',
          }
        })
          .then(response => {
            this.$store.commit(types.LOGIN, response.data);
            let redirect = decodeURIComponent(this.$route.query.redirect || '/');
            this.$router.push({
              path: redirect
            })
          }).catch(err => {
          this.$error(err);
          this.error = err.message;
        })
      }
    }
  }
</script>

<style lang="stylus" scoped>
  .login-wrap
    padding: 112px 48px 64px
    margin: 200px auto 0
    max-width: 360px
    background: url('../assets/logo.png') no-repeat center 32px
    background-size: 48px

  @media (max-width: 480px)
    .login-wrap
      margin-top: 140px
      box-shadow: none

  .login-btn {
    width: 100%;
    margin: 0;
  }
</style>
