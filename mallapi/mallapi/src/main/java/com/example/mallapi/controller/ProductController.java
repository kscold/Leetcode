package com.example.mallapi.controller;

import com.example.mallapi.dto.ProductDTO;
import com.example.mallapi.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil; // 파일 업로드 부분을 연동

    @PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO) { // /api/products 주소에 POST 메서드로 productDTO가 주어지면
        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles(); // 파일들을 MutiparFile 객체 리스트로 가져옴

        List<String> uploadedFileNames = fileUtil.saveFiles(files); // 파일 리스트를 정의한 saveFiles 메서드를 통해 저장

        productDTO.setUploadedFileNames(uploadedFileNames); // 파일을 업로드함

        log.info(uploadedFileNames);

        return Map.of("RESULT", "SUCCESS"); // 응답 key: value를 반환
    }


    @GetMapping("/view/{fileName}") // GET으로 업로드된 파일을 볼 때
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }
}
