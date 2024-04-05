package com.example.aspectdemo.controller;

import com.example.aspectdemo.domain.FileDomain;
import com.example.aspectdemo.dto.BaseResponseDto;
import com.example.aspectdemo.dto.file.FileDto;
import com.example.aspectdemo.dto.file.GetAllFilesSizeResponseDto;
import com.example.aspectdemo.dto.file.GetFileByNameResponseDto;
import com.example.aspectdemo.dto.file.SaveFileSyncResponseDto;
import com.example.aspectdemo.dto.type.StatusDto;
import com.example.aspectdemo.model.File;
import com.example.aspectdemo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.example.aspectdemo.controller.helper.CommonControllerHelper.makeErrorResponse;
import static com.example.aspectdemo.controller.helper.CommonControllerHelper.makeSuccessResponse;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download")
    public ResponseEntity<BaseResponseDto> download() {
        try {
            fileService.downloadFiles();
            return makeSuccessResponse();
        } catch (IOException e) {
            return makeErrorResponse(e);
        }
    }

    @GetMapping("/getAllFilesSize")
    public ResponseEntity<BaseResponseDto> getAllFilesSize() {
        try {
            List<FileDomain> files = fileService.getAllFilesSize();
            GetAllFilesSizeResponseDto dto = new GetAllFilesSizeResponseDto(
                    files.stream().map(it -> FileDto.builder()
                            .name(it.getName())
                            .size(it.getSize())
                            .build()).toList()
                            );
            dto.setStatus(StatusDto.SUCCESS);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return makeErrorResponse(e);
        }
    }

    @GetMapping("/saveFileSync")
    public ResponseEntity<SaveFileSyncResponseDto> saveFileSync(@RequestParam String name) {
        FileDomain file = fileService.getFileByName(name);
        File savedFile = fileService.saveFileSync(file.getName(), file.getContent());
        SaveFileSyncResponseDto dto = SaveFileSyncResponseDto.builder()
                .id(savedFile.getId())
                .build();
        dto.setStatus(StatusDto.SUCCESS);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/saveFileAsync")
    public ResponseEntity<BaseResponseDto> saveFileAsync(@RequestParam String name) {
        FileDomain file = fileService.getFileByName(name);
        fileService.saveFileAsync(file.getName(), file.getContent());
        return makeSuccessResponse();
    }


    @GetMapping("/getFileByName")
    public ResponseEntity<GetFileByNameResponseDto> getFileByName(@RequestParam String name) {
        FileDomain file = fileService.getFileByName(name);
        GetFileByNameResponseDto dto = new GetFileByNameResponseDto(
                FileDto.builder()
                        .name(file.getName())
                        .size(file.getSize())
                        .build());
        dto.setStatus(StatusDto.SUCCESS);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
