// Глобальная регистрация библиотек в корневом компоненте Vue
import axios from 'axios';
import lodash from 'lodash';
import sockjs from 'sockjs-client';
import stomp from 'stompjs';

export default {
  install: function (Vue) {
    Object.defineProperty(Vue.prototype, '$axios', { value: axios });
    Object.defineProperty(Vue.prototype, '$_', { value: lodash });
    Object.defineProperty(Vue.prototype, '$sockJS', { value: sockjs });
    Object.defineProperty(Vue.prototype, '$stomp', { value: stomp });
  },
};
