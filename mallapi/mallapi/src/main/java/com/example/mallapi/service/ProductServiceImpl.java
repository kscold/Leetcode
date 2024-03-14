package com.example.mallapi.service;

import com.example.mallapi.domain.Product;
import com.example.mallapi.domain.ProductImage;
import com.example.mallapi.dto.PageRequestDTO;
import com.example.mallapi.dto.PageResponseDTO;
import com.example.mallapi.dto.ProductDTO;
import com.example.mallapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno"));

        Page<Object[]> result = productRepository.selectList(pageable); // 페이징해서 데이터를 받아옴

        // Object[] => 0 product 1 productImage
        // Object[] => 0 product 1 productImage
        // Object[] => 0 product 1 productImage

        List<ProductDTO> dtoList = result.get().map(arr -> { // 데이터를 뿌림
            ProductDTO productDTO = null;

            Product product = (Product) arr[0]; // 0번째 데이터를 뽑음
            ProductImage productImage = (ProductImage) arr[1]; // 1번째 데이터 뽑음

            productDTO = productDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();

            String imageStr = productImage.getFileName(); // 상품 이미지 파일 이름 뽑음
            productDTO.setUploadFileNames(List.of(imageStr)); // 업로드할 이미지 파일 이름을 설정

            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .total(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);

        log.info("---------------------------");
        log.info(product);
        log.info(product.getImageList());

        Long pno = productRepository.save(product).getPno();

        return pno;
    }

    private Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if (uploadFileNames == null || uploadFileNames.size() == 0) {
            return product;
        }

        uploadFileNames.forEach(fileName -> { // 이미지 파일이 있다면 forEach를 돌면서 엔티티의 addImageString에 추가
            product.addImageString(fileName);
        });

        return product;
    }

}
