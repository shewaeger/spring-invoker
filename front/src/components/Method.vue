<template>
    <div class="ws-controller">
        <div class="description" v-if="controller.description != ''">
            <h3>Description:</h3>
            <p>{{controller.description}}</p>
        </div>
        <v-textarea
        name="input-7-1"
        :label="paramNames[key]"
        outline
        auto-grow
        v-for="(parameter, key) in parameters" 
            :key="key"
        v-model="parameters[key]"
    ></v-textarea>
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
    components: {
    },
    props: ["controller"],
    data: () => ({
        response: "",
        errorResponse: false,
        parameters: [],
        paramNames: [],
        generatedObjects: []
        
    }),
    created(){
        this.controller.parameters.forEach( el => {
                this.parameters.push(JSON.stringify(this.getObjectFromScheme(el.schema), null, 4));
                this.paramNames.push(el.name);
            }
        );
        
    },
    computed: {
        apiHref: function(){
            return "/ws/web-socket-api/" + this.controller.id;
        }
    },

    methods: {
        sendMessage(){
            
            var jsonRequest = '[' + this.parameters.join(",") + ']'
            var payload = JSON.parse(jsonRequest);
            this.$axios.post(this.apiHref, payload)
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
        /* min-height: 500px !important; */
    }
    .ws-controller {
        padding: 20px 20px 30px;
    }
    .description {
        margin: 10px;
        font-family: monospace;
    }
</style>
