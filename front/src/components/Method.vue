<template>
    <div class="ws-controller">
       <v-text-field
            v-model="userValue"
            label="User"
            outline
            v-if="controller.hasUser"
        >
        </v-text-field>
        <v-textarea
            name="input-7-1"
            :label="controller.name"
            v-model="jsonData"
            outline
        >
        </v-textarea>
        <v-btn @click="sendMessage" color="#050">Send</v-btn>
    </div>
</template>

<script>
import axios from 'axios'
import __ from 'lodash'
export default {
    props: ["controller"],
    data: () => ({
        jsonData: "",
        userValue: "",
        tmp: {},
        generatedObjects: {}
    }),
    created(){
        this.jsonData = JSON.stringify(this.getObjectFromScheme(this.controller.parameter), null, 4);
    },

    computed: {
        apiHref: function(){
            if(this.controller.hasUser)
                return "web-socket-api/" + this.controller.id + "/" + this.userValue;
            else
                return "web-socket-api/" + this.controller.id;
            
        }
    },
    methods: {
        sendMessage(){
            axios.post(this.apiHref , JSON.parse(this.jsonData))
            .then((response) => {
                // eslint-disable-next-line
                console.log(response);
            })
            .catch(error => {
                // eslint-disable-next-line
                console.error(error.response.data.exception)
            })
        },

        getObjectFromScheme(scheme){
            var retObject = {};
            switch(scheme.type){
                case "string":
                    if(scheme.enum){
                        if(scheme.enum[0])
                            return scheme.enum[0];
                        return "";
                    }
                    retObject = "string";
                    break;
                case "number":
                case "integer":
                    retObject =  0;
                    break;
                case "boolean":
                    retObject =  false;
                    break;
                case "array":
                    retObject =  [this.getObjectFromScheme(scheme.items)];
                    break;
                case "object":
                    if(scheme.$ref && this.generatedObjects[scheme.$ref])
                        return this.generatedObjects[scheme.$ref];
                    var properties = scheme.properties;
                    __.forEach(properties, (value, key) => {
                        
                        retObject[key] = this.getObjectFromScheme(value);
                    })
                    this.generatedObjects[scheme.id] = retObject;
            }
            return retObject;
        },
    }
}
</script>

<style>
    
</style>
