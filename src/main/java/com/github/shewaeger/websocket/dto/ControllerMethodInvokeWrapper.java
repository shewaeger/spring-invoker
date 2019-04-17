package com.github.shewaeger.websocket.dto;

import lombok.Data;

import java.util.List;

@Data
public class ControllerMethodInvokeWrapper {
    Long id;
    List<ParameterWrapper> parameters;
}
