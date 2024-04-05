package com.example.aspectdemo.controller;


import com.example.aspectdemo.dto.BaseResponseDto;
import com.example.aspectdemo.dto.tracker.GetAllTrackerByTypeResponseDto;
import com.example.aspectdemo.dto.tracker.GetFastestExecutionByTypeResponseDto;
import com.example.aspectdemo.dto.tracker.TrackerDto;
import com.example.aspectdemo.dto.type.StatusDto;
import com.example.aspectdemo.model.Tracker;
import com.example.aspectdemo.service.TrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.aspectdemo.controller.helper.CommonControllerHelper.makeErrorResponse;

@RestController
@RequestMapping("/tracker")
@Slf4j
public class TrackerController {

    private final TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @GetMapping("/getAllByType")
    public ResponseEntity<BaseResponseDto> GetAllTrackerByType(@RequestParam String type) {
        try {
            GetAllTrackerByTypeResponseDto dto = GetAllTrackerByTypeResponseDto.builder()
                    .trackerDtoList(trackerService.getAllByType(type).stream().map(
                            it -> TrackerDto.builder()
                                    .id(it.getId())
                                    .methodSignature(it.getMethodSignature())
                                    .duration(it.getDuration())
                                    .build()).toList())
                    .build();
            dto.setStatus(StatusDto.SUCCESS);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return makeErrorResponse(e);
        }
    }

    @GetMapping("/getLongestExecutionForType")
    public ResponseEntity<BaseResponseDto> getFastestExecutionForType(@RequestParam String type) {
        try {
            Tracker tracker = trackerService.getLongestExecutionByType(type);
            GetFastestExecutionByTypeResponseDto dto = GetFastestExecutionByTypeResponseDto.builder()
                    .tracker(TrackerDto.builder()
                            .id(tracker.getId())
                            .duration(tracker.getDuration())
                            .build())
                    .build();
            dto.setStatus(StatusDto.SUCCESS);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return makeErrorResponse(e);
        }
    }
}
