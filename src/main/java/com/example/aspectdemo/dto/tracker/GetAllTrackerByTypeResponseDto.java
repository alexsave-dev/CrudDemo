package com.example.aspectdemo.dto.tracker;

import com.example.aspectdemo.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetAllTrackerByTypeResponseDto extends BaseResponseDto {
    private List<TrackerDto> trackerDtoList;
}
