package com.example.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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

            Path savePath = Paths.get(uploadPath, savedName); // savedName한 파일의 패스를 추출

            try {
                Files.copy(file.getInputStream(), savePath);
                // 파일이 복사될 대상 경로인 savePath를 MultipartFile 객체인 file의 내용을 읽어오기 위한 InputStream을 반환 후 savePath를 복사함
                uploadNames.add(savedName); // saveName으로 설정한 파일 이름을 uploadName 리스트에 추가
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadNames;
    }
}
