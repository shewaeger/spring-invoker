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
    import GroupController from "./components/GroupController.vue"
    export default {
      components: {
        GroupController
      },
      data: () => ({
        groupControllers: [],
        errors: []
      }),
      created() {
        this.$axios.get("/ws/web-socket-api")
        .then( response => {
          this.groupControllers = this.$_.groupBy( response.data, (item) => {
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
