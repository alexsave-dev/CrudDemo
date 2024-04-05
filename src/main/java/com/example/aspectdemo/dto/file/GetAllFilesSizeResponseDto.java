package com.example.aspectdemo.dto.file;

import com.example.aspectdemo.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetAllFilesSizeResponseDto extends BaseResponseDto {
    private List<FileDto> files;
}
