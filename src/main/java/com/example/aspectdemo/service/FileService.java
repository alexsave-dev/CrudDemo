package com.example.aspectdemo.service;

import com.example.aspectdemo.domain.FileDomain;
import com.example.aspectdemo.model.File;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void downloadFiles() throws IOException;

    List<FileDomain> getAllFilesContent() throws IOException;

    List<FileDomain> getAllFilesSize() throws IOException;

    FileDomain getFileByName(String name);
    File saveFileSync(String name, byte[] content);
    void saveFileAsync(String name, byte[] content);
}
