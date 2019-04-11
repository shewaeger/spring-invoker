# Web socket tester
Тестирование отправки сообщений в веб-сокеты. 
Для определения класса, который будет отправлять сообщения в веб-сокет служит аннотация ```@WebSocketController```. Интерфейс, над которым стоит аннотация должен содержать объявления методов начинающихся на sendTo, либо с аннотацией ```@SendTo```.
Аннотация приорететнее имени метода.

Пример:
```
@WebSocketController
public interface UserWSController {

    /* Сообщение отправится в /queue/user */
    void sendToQueueUser(User user); 
    
    @SendTo({"/queue/user/events", "/queue/user/other/queue"})
    void userEvents(String user, UserEvents);

}
```

Фронт доступен по адрессу http://example.com/web-socket-tester
