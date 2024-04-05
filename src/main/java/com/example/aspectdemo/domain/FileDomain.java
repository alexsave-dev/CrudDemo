package com.example.aspectdemo.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FileDomain {
    String name;
    byte[] content;
    int size;
}
