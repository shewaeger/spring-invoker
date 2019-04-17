# Web socket tester
Библиотека инкапсулирует в себе вызовы класса ```SimpMessagingTemplate``` таких, как ```convertAndSend(...)``` и ```convertAndSendToUser(...)```. Так же библиотека содержит утилиту для тестирования отправки сообщений в котроллеры, с аннотацией ```@WebSocketController```.

### Подключение библиотеки в проект

#### Gradle
```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

    ...

dependencies {
    compile 'com.github.shewaeger:websocket:0.0.2'
}

```
Если в проекте используется spring < 1.5.20 в зависимостях указать:

```java
dependencies {
    compile group: 'com.google.guava', name: 'guava', version: '20.0'
}
```

### Активация библиотеки
Что бы подключить библиотеку нужно указать аннотацию ```@EnableWebSocketControllers``` и сконфигурировать stomp в проекте. [Пример](https://spring.io/guides/gs/messaging-stomp-websocket/).

Для объявления контроллера используется интерфейс. Над интерфейсом должна стоять аннотация ```@WebSocketController```. Для того, что бы указать канналы, над методом нужно поставить аннотацию ```@SendTo```, которая принимает коллекцию каналов. Библиотека так же может генерировать канал из имени метода. Для этого метод должен начинаться с ```sendTo```.

Метод должен принимать хотя бы один параметр - объект, который будет отправляться в канал.

Если нужно отправить сообщение пользователю, то первый параметр должен быть ```java.lang.String```, а второй - объект, который должен отправиться.

Пример:
```java
@WebSocketController
public interface UserWSController {

    /* Сообщение отправится в /queue/user и /queue/user/other/queue */
    @SendTo("/queue/user/other/queue")
    void sendToQueueUser(User user); 
        
    @SendTo({"/queue/user/events", "/queue/user/other/queue"})
    void userEvents(String user, UserEvents);

}
```

Утилита для тестирования доступна по адресу http://example.com/web-socket-tester.html
