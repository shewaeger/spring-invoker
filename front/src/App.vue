<template>
  <div id="app">
    <group-controller 
      v-for="(groupController, key, index) in groupControllers"
      :propControllers="groupController"
      :controllerName="key"
      :key="index"></group-controller>
  </div>
</template> 

<script>
    import axios from 'axios'
    import 'vuetify/dist/vuetify.min.css'
    import GroupController from "./components/GroupController.vue"
    import __ from "lodash"
    import Vuetify from "vuetify"
    import Vue from "vue";
    Vue.use(Vuetify);
    export default {
      components: {
        GroupController
      },
      data: () => ({
        groupControllers: [],
        errors: []
      }),
      created() {
        axios.get("web-socket-api")
        .then( response => {
          this.groupControllers = __.groupBy( response.data, (item) => {
            var t = item.ownerName.split(".");
            return t[t.length - 1];
          });
        })
        .catch(e => {
          this.errors.push(e);
        })
      },
      
    }


</script>

<style>
#app {
  padding:20px;
}
</style>
