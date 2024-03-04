package com.example.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO; // builder 생성자를 통해 인스턴스 생성

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll") // 빌더 패턴을 사용하는데 빌터 패턴에 메서드 이름을 명시
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) { // 받는 생성자를 엔티티와 DTO 총 페이지 수를 받도록 설정

        /*this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) total;

        this.current = pageRequestDTO.getPage(); // 현재 페이징 위치

        // 끝페이지 end 값 구하기
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        int start = end - 9;

        // 정말 페이지의 남은 데이터 구하기
        int last = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize()));

        end = end > last ? last : end; // end가 last 보다 크면 last가 정말 마지막 값이므로 last가 되고 아니라면 end가 됨

        this.prev = start > 1; // boolean 값 시작 페이징이 1 페이징보다 크면

        // 다음으로 가는 링크 계산하기 위한 변수 선언
        this.next = totalCount > end * pageRequestDTO.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()); // int[] -> List<Integer> 로 변환*/

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) total;

        this.current = pageRequestDTO.getPage(); // 현재 페이징 위치

        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        int start = end - 9;

        int last = (int) (Math.ceil((totalCount / (double) pageRequestDTO.getSize())));

        end = end > last ? last : end;

        this.prev = start > 1;


        this.next = totalCount < end * pageRequestDTO.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());


        this.prevPage = prev ? start - 1 : 0; // 2 페이징 이상이면, 하나 전 페이징으로 감소 이하면 0 페이지으로 설정
        this.nextPage = next ? end + 1 : 0; // 2 페이징 이상히면, 앞의 페이징으로 증가 이하면 0 페이지로 설정
      /*  this.prevPage = current - 1; // 이전 페이징 번호 설정
        this.nextPage = current + 1;// 다음 페이징 번호 설정*/

        this.totalPage = this.pageNumList.size(); // 전체 페이징
    }

}
