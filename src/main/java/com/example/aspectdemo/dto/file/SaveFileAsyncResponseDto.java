package com.example.aspectdemo.dto.file;

import com.example.aspectdemo.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SaveFileAsyncResponseDto extends BaseResponseDto {
    private Long id;
}
