package com.example.mallapi.controller;

import com.example.mallapi.dto.PageRequestDTO;
import com.example.mallapi.dto.PageResponseDTO;
import com.example.mallapi.dto.ProductDTO;
import com.example.mallapi.service.ProductService;
import com.example.mallapi.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil; // 파일 업로드 부분을 연동

    private final ProductService productService;

    /*@PostMapping("/")
    public Map<String, String> register(ProductDTO productDTO) { // /api/products 주소에 POST 메서드로 productDTO가 주어지면
        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles(); // 파일들을 MutiparFile 객체 리스트로 가져옴

        List<String> uploadedFileNames = fileUtil.saveFiles(files); // 파일 리스트를 정의한 saveFiles 메서드를 통해 저장

        productDTO.setUploadFileNames(uploadedFileNames); // 파일을 업로드함

        log.info(uploadedFileNames);

        return Map.of("RESULT", "SUCCESS"); // 응답 key: value를 반환
    }*/


    @GetMapping("/view/{fileName}") // GET으로 업로드된 파일을 볼 때
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.getList(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {

        List<MultipartFile> files = productDTO.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long pno = productService.register(productDTO);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("result", pno);
    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno) {
        return productService.get(pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable Long pno, ProductDTO productDTO) {
        productDTO.setPno(pno);

        // 기존 저장되고 데이터베이스에 존재
        ProductDTO oldProductDTO = productService.get(pno);

        // 파일 업로드
        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        // 지워지지 않은 파일
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            // 업로드하는 파일이 일전에 없던 파일이라면
            uploadedFileNames.addAll(currentUploadFileNames); // 현재 파일
        }

        productService.modify(productDTO);

        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        if (oldFileNames != null && oldFileNames.size() > 0) {
            // 삭제해야되는 파일들을 뽑음
            List<String> removeFiles =
                    oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            // 0보다 큰 경우는 파일이 존재함, 따라서 -1은 파일이 존재하지 않는 경우만 남겨놓는다는 이야기임

            fileUtil.deleteFiles(removeFiles);

        }

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable Long pno) {
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);
        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }
}
