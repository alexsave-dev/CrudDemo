package com.example.aspectdemo.dto.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {
    private String name;
    private Integer size;
}
