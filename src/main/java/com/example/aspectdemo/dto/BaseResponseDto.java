package com.example.aspectdemo.dto;

import com.example.aspectdemo.dto.type.StatusDto;

public class BaseResponseDto {
    private StatusDto status;
    private String message;


    public BaseResponseDto(StatusDto status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponseDto(StatusDto status) {
        this.status = status;
    }

    public BaseResponseDto() {
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
