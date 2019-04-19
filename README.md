# Spring invoker
[![](https://jitpack.io/v/shewaeger/spring-invoker.svg)](https://jitpack.io/#shewaeger/spring-invoker)

Утилита для спринга, которая умеет вызвать методы спринговых бинов. Позволяет делать вызов методов из веб-интерфейса. 

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
    compile 'com.github.shewaeger:spring-invoker:1.0.2'
}

```

### Включение библиотеки в проекте.
Что бы подключить библиотеку нужно указать аннотацию ```@EnableMethodInvoker```.

Для указания бина, методы которого нужно вызвать, нужно указать над ним аннотацию ```@InvokedClass```. Для указания метода, который нужно вызвать из интерфейса, нужно указать аннотацию ```@SendMethod```. У ```@SendMethod``` есть параметр ```description```. Он служит для отображения описания метода в веб-интерфейсе.

Пример класса:
```java

@Service
@InvokedClass
@Log4j2
public class InvokedService {

    @SendMethod(
            description = "Test method for invoking."
    )
    public SomeClass method(SomeClass1 wrapper, Long id){
       log.info(wrapper);
       return new SomeClass(id);
    }

}

```

После запуска веб-интерфейс будет доступен по адресу http://localhost:8080/spring-invoker.html
