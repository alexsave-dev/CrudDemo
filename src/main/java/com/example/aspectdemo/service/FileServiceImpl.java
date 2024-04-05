package com.example.aspectdemo.service;

import com.example.aspectdemo.annotation.TrackTime;
import com.example.aspectdemo.annotation.TrackTimeAsync;
import com.example.aspectdemo.domain.FileDomain;
import com.example.aspectdemo.model.File;
import com.example.aspectdemo.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final Map<String, byte[]> files;

    private final String imageUrl;
    private final int numberOfProfiles;

    private final FileRepository fileRepository;

    public FileServiceImpl(@Value("${demo.number-of-profiles}") int numberOfProfiles,
                           @Value("${demo.profile-image-url}") String imageUrl, FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.files = new HashMap<>();
        this.imageUrl = imageUrl;
        this.numberOfProfiles = numberOfProfiles;
    }

    @Override
    public void downloadFiles() throws IOException {
        files.clear();
        for (int i = 0; i < numberOfProfiles; i++) {
            long currentTime = System.currentTimeMillis();
            files.put("cat" + currentTime, readImageFromWebsite());
        }
    }

    @Override
    public List<FileDomain> getAllFilesContent()  {
        List<FileDomain> resultList = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry: files.entrySet()) {
            resultList.add(FileDomain.builder()
                    .name(entry.getKey())
                    .content(entry.getValue())
                    .size(entry.getValue().length)
                    .build());
        }
        return resultList;
    }

    @Override
    public List<FileDomain> getAllFilesSize() {
        List<FileDomain> resultList = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry: files.entrySet()) {
            resultList.add(FileDomain.builder()
                    .name(entry.getKey())
                    .size(entry.getValue().length)
                    .build());
        }
        return resultList;
    }

    @Override
    public FileDomain getFileByName(String name) {
        return FileDomain.builder()
                .name(name)
                .content(files.get(name))
                .size(files.get(name).length)
                .build();
    }

    @Override
    @TrackTime
    public File saveFileSync(String name, byte[] content) {
        return saveFile(name, content);
    }

    @Override
    @TrackTimeAsync
    public void saveFileAsync(String name, byte[] content) {
        saveFile(name, content);
    }

    private File saveFile(String name, byte[] content) {
        return fileRepository.save(File.builder()
                .name(name)
                .content(content)
                .build());
    }

    private byte[] readImageFromWebsite() throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "image/png");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readImageData(httpURLConnection.getInputStream());
            } else {
                throw new IOException("Failed to download image. HTTP response code: " + responseCode);
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private byte[] readImageData(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        try (inputStream; ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }
}
