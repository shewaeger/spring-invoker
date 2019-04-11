<template>
    <div class="group-controller">
        <h1>{{controllerName}}</h1>
        <method v-for="controller in propControllers" :key="controller.id" :controller="controller"></method>
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
.group-controller h1{
    margin: 10px 0 40px 0;
}
</style>
