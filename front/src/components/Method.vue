<template>
    <div class="ws-controller">
        <div class="description">
            <h3>Channels:</h3>
            <ul>
                <li v-for="(channel, index) in controller.channels" :key="index"> {{channel}}</li>
            </ul>
        </div>

        <v-text-field
            v-model="userValue"
            label="User"
            outline
            v-if="controller.hasUser">
        </v-text-field>
        <v-textarea
            name="input-7-1"
            :label="controller.name"
            v-model="jsonData"
            outline
            auto-grow
        >
        </v-textarea>
        <v-btn @click="sendMessage" color="#050">Send</v-btn>
        <div class="serverResponse" v-if=" response && response.length != 0">
            <h3>Resopnse:</h3>
            <v-textarea
                name="input-7-1"
                :label="controller.name"
                v-model="response"
                outline
                :readonly="true"
                auto-grow
                :error="errorResponse"
            ></v-textarea>
        </div>
    </div>
</template>

<script>

export default {
    props: ["controller"],
    data: () => ({
        jsonData: "",
        userValue: "",
        tmp: {},
        generatedObjects: {},
        response: "",
        errorResponse: false
    }),
    created(){
        this.jsonData = JSON.stringify(this.getObjectFromScheme(this.controller.parameter), null, 4);
    },

    computed: {
        apiHref: function(){
            if(this.controller.hasUser)
                return "/ws/web-socket-api/" + this.controller.id + "/" + this.userValue;
            else
                return "/ws/web-socket-api/" + this.controller.id;
            
        }
    },
    methods: {
        sendMessage(){
            var request = JSON.parse(this.jsonData);
            if(!request)
                request = this.jsonData;
            this.$axios.post(this.apiHref, request)
            .then((response) => {
                this.errorResponse = false;
                this.response = JSON.stringify(response.data, null, 4);
            })
            .catch(e => {
                this.errorResponse = true;
                this.response = JSON.stringify(e.response.data, null, 4);
            })
        },

        getObjectFromScheme(scheme){
            var retObject = {};
            switch(scheme.type){
                case "string":
                    if(scheme.enum){
                        if(scheme.enum[0])
                            retObject = scheme.enum[0];
                        else
                            retObject = "";
                    }
                    else if (scheme.format){
                        switch(scheme.format){
                            case "date-time":
                                retObject = new Date().toString();
                                break;
                            default:
                                retObject = scheme.format
                                break;
                        }
                    }
                    else {
                        retObject = "string";
                    }
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
                    this.$_.forEach(properties, (value, key) => {
                        
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
    .ws-controller textarea{
        font-family: monospace;
        min-height: 500px !important;
    }
    .ws-controller {
        padding: 20px 20px 30px;
    }
    .description {
        margin: 10px;
        font-family: monospace;
    }
</style>
