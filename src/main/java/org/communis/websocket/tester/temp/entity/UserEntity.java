package org.communis.websocket.tester.temp.entity;

import lombok.Data;

@Data
public class UserEntity {

    String login;

    String name;

    String email;

    BrigadeWrapper brigade;

    AddressWrapper addressWrapper;

    TestEnum anEnum;

}
