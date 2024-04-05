package com.example.aspectdemo.dto.tracker;

import com.example.aspectdemo.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetFastestExecutionByTypeResponseDto extends BaseResponseDto {
    private TrackerDto tracker;
}
