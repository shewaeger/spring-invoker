<template>
  <div id="app">
    <group-controller 
      v-for="(groupController, key, index) in groupControllers"
      :propControllers="groupController"
      :controllerName="key"
      :key="index"></group-controller>
    <v-dialog v-model="dialog.show" persistent >
        <v-card>
          <v-card-title
            class="headline red lighten-2"
            primary-title
          >
            Error
          </v-card-title>
  
          <v-card-text class="headline">
            {{dialog.message}}
          </v-card-text>
        </v-card>
    </v-dialog>
  </div>
</template> 

<script>
    import GroupController from "./components/GroupController.vue"
    export default {
      components: {
        GroupController        
      },
      data: () => ({
        groupControllers: [],
        dialog: {
          show: false,
          message: ""
        }
      }),
      created() {
        this.$axios.get("/ws/web-socket-api")
        .then( response => {
          this.groupControllers = this.$_.groupBy( response.data, (item) => {
            var t = item.idOwner.split(".");
            return t[t.length - 1];
          });
        })
        .catch(e => {
          // eslint-disable-next-line
          console.error(e);

          this.dialog.message = "Unable to connect to server"
          this.dialog.show = true;
        })
      }      
    }


</script>

<style>
#app {
  padding:20px;
}
</style>
