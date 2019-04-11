package org.communis.websocket.tester.temp.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BrigadeWrapper {
    @JsonProperty(defaultValue = "1")
    Long id;
    String name;
    List<Integer> integers;
    List<Double> doubles;
    List<Float> floats;
    List<Long> longs;
    List<AddressWrapper> addresses;
    AddressWrapper wrapper;
    TestEnum _enum;
}
