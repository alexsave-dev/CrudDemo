package com.example.aspectdemo.controller.helper;

import com.example.aspectdemo.dto.BaseResponseDto;
import com.example.aspectdemo.dto.type.StatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonControllerHelper {
    public static ResponseEntity<BaseResponseDto> makeErrorResponse(Exception e) {
        BaseResponseDto dto = new BaseResponseDto(StatusDto.ERROR, e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto> makeSuccessResponse() {
        BaseResponseDto dto = new BaseResponseDto(StatusDto.SUCCESS);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
