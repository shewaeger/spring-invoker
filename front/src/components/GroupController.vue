<template>
  <div class="group-controller">
  <v-expansion-panel>
    <v-expansion-panel-content>
      <template v-slot:header>
        <h1>{{controllerName}}</h1>
      </template>
        <v-card>
          <v-card-actions>
              <v-expansion-panel>
                    <v-expansion-panel-content  v-for="controller in propControllers" :key="controller.id" >
                        <template v-slot:header>
                            <div>{{controller.name}}</div>
                        </template>
                        <method :controller="controller" @methodSend="(message) => $emit('methodSend', message)"></method>
                    </v-expansion-panel-content>
              </v-expansion-panel>
          </v-card-actions>
        </v-card>
    </v-expansion-panel-content>
  </v-expansion-panel>
  </div>
</template>
<script>
import Method from "./Method.vue"
export default {
    components: {
        Method
    },
    props: ["propControllers", "controllerName"],
    data : () => ({
        controllers: []
    })
}
</script>

<style>

.group-controller {
    border: 1px solid #000;
    margin-bottom: 10px;
    border-radius: 5px;
}
.group-controller h1{
    font-size: 1em;
}
</style>
