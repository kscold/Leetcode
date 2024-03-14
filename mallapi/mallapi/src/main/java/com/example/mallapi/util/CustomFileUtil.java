package com.example.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.example.upload.path}") // application.yaml 파일에 설정한 업로드 페스를 가져옴
    private String uploadPath;

    @PostConstruct // 생성자 실행과 동시에 bean 한번 등록시키기 위해 선언
    public void init() {
        File tempFolder = new File(uploadPath);

        if (tempFolder.exists() == false) { // 만약 위의 패스가 없다면
            tempFolder.mkdir(); // 폴더 경로를 생성
        }

        uploadPath = tempFolder.getAbsolutePath(); // 절대 결로로 문자열 uploadPath를 갱신

        log.info("-----------------------------");
        log.info(uploadPath);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
        if (files == null || files.size() == 0) { // 저장할 파일이 null인 경우
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // 원래 파일이름에 UUID을 _로 겹처서 savedName을 생성함

            Path savePath = Paths.get(uploadPath, savedName); // savedName(이름을 바꾼) 파일의 패스를 추출

            try {
                Files.copy(file.getInputStream(), savePath); // 원본 파일 업로드
                // 파일이 복사될 대상 경로인 savePath를 MultipartFile 객체인 file의 내용을 읽어오기 위한 InputStream을 반환 후 savePath를 복사함

                String contentType = file.getContentType(); // file의 타입을 String에 담아줌

                if (contentType != null || contentType.startsWith("images")) { // 만약 contentType에 값이 있고 images로 문자열이 시작한다면
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName); // 이름을 바꿔서 추출

                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile()); // 썸네일을 생성
                }

                uploadNames.add(savedName); // saveName으로 설정한 파일 이름을 uploadName 리스트에 추가
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName + ".jpeg");

        if (!resource.isReadable()) { // 만약 파일이름이 정상적이지 않으면
            resource = new FileSystemResource(uploadPath + File.separator + "default.jpeg"); // 기본이미지를 보여줌
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(fileName -> {
            // 썸네일 삭제
            String thumbnameFileName = "s_" + fileName;

            Path thumbnailPath = Paths.get(uploadPath, thumbnameFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
