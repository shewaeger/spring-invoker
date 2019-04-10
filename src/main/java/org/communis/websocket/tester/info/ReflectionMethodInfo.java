package org.communis.websocket.tester.info;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReflectionMethodInfo {

    List<String> queues;
    Boolean forUser;

}
