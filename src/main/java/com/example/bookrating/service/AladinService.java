package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.ReviewSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AladinService {

    @Value("${aladin.api.key}")
    private String aladinApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    private ReviewService reviewService;

    public AladinService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    // 알라딘 API 매뉴얼
    // https://docs.google.com/document/d/1mX-WxuoGs8Hy-QalhHcvuV17n50uGI2Sg_GHofgiePE/edit?tab=t.0
    public List<BookDto> getItemList(int categoryId, String queryType, int size) {
        // 1️알라딘 API 호출
        String url = UriComponentsBuilder.fromHttpUrl("http://www.aladin.co.kr/ttb/api/ItemList.aspx")
                .queryParam("ttbkey", aladinApiKey)
                .queryParam("QueryType", queryType)
                .queryParam("MaxResults", size)
                .queryParam("start", 1)
                .queryParam("SearchTarget", "Book")
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .queryParam("CategoryID", categoryId)
                .queryParam("Cover", "Big")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("item");

        // 알라딘 API에서 가져온 책 정보를 `AladinBookDto`로 변환
        return items.stream()
                .map(this::convertToDto) // 리뷰 정보를 포함한 DTO로 변환
                .collect(Collectors.toList());
    }

    private BookDto convertToDto(Map<String, Object> item) {
        String isbn = (String) item.get("isbn13");
        String title = (String) item.get("title");
        String thumbnail = (String) item.get("cover");
        String contents = (String) item.get("description");
        String datetime = (String) item.get("pubDate");
        String authors = (String) item.get("author");
        String publisher = (String) item.get("publisher");
        String url = (String) item.get("link");

        // 리뷰 테이블에서 해당 책의 평점 & 리뷰 개수 조회
        ReviewSummaryDto reviewSummary = reviewService.getReviewSummaryByIsbn(isbn);

        return new BookDto(
                null, // 알라딘 API에서 가져온 데이터이므로 내부 DB ID 없음
                isbn,
                title,
                thumbnail,
                contents,
                datetime,
                authors,
                publisher,
                url,
                reviewSummary.getAverageRating(),
                reviewSummary.getReviewCount()
        );
    }
}