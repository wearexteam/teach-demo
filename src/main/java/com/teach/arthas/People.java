package com.teach.arthas;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class People {
    private int age;
    private byte gender;
    private String name;
    private String address;
    private List<People> children;
}