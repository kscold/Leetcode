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
import java.util.Optional;
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

    @Override
    public ProductDTO get(Long pno) { //

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        return entityToDTO(product);
    }

    @Override
    public void modify(ProductDTO productDTO) {
        // 조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();
        // 변경 내용 반영
        product.changePrice(productDTO.getPrice());
        product.changeName(productDTO.getPname());
        product.changeDesc(productDTO.getPdesc());
        product.changeDel(productDTO.isDelFlag());

        // 이미지 처리
        List<String> uploadFileNames = productDTO.getUploadFileNames();
        // 어떤 이미지가 들어있는지 확인할 수 없음

        // 따라서 일단 이미지 데이터를 모두 삭제
        product.clearList();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {
            // uploadFileNames이 null이 아니고 비어있지 않으면
            uploadFileNames.forEach(uploadFileName -> {
                product.addImageString(uploadFileName);
            });

        }

        // 저장
        productRepository.save(product);

    }

    @Override
    public void remove(Long pno) {
        // 원래 대형 서비스라면 삭제 로직은 존재하지 않고 flag 값만 수정하는 것이 다임
        productRepository.deleteById(pno);
    }

    private ProductDTO entityToDTO(Product product) { // 엔티티를 다시 DTO로 바꾸는 메서드
        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .delFlag(product.isDelFlag())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if (imageList == null || imageList.size() == 0) {
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList(); // 문자열로 변경

        productDTO.setUploadFileNames(fileNameList); // 업로드할 파일이름을 변경

        return productDTO;
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
