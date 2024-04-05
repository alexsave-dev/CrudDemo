package com.example.aspectdemo.dto.tracker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackerDto {
    private Long id;
    private String methodSignature;
    private Long duration;
}
