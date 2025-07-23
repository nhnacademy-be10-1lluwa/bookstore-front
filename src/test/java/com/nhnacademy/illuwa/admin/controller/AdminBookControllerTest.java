package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.book.dto.BookDetailWithExtraInfoResponse;
import com.nhnacademy.illuwa.book.dto.BookExternalResponse;
import com.nhnacademy.illuwa.book.service.MinioStorageService;
import com.nhnacademy.illuwa.category.dto.CategoryResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.tag.client.TagServiceClient;
import com.nhnacademy.illuwa.tag.dto.TagResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest(controllers = AdminBookController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    ProductServiceClient productServiceClient;
    @MockBean
    MinioStorageService minioStorageService;
    @MockBean
    TagServiceClient tagServiceClient;

    // 공통 더미 데이터 생성 헬퍼
    private BookDetailWithExtraInfoResponse createDummyBook(Long id) {
        BookDetailWithExtraInfoResponse book = new BookDetailWithExtraInfoResponse();
        book.setId(id);
        book.setTitle("Title " + id);
        book.setImgUrl("http://storage.java21.net:8000/book-img.png");
        book.setBookTags(List.of(new TagResponse(1L, "Fiction")));
        return book;
    }

    private TagResponse createDummyTag() {
        return new TagResponse(1L, "Fiction");
    }

    private CategoryResponse createDummyCategory() {
        return new CategoryResponse(1L, 0L, "Novels", List.of());
    }

    private BookExternalResponse createDummyExternalBook() {
        BookExternalResponse ext = new BookExternalResponse();
        ext.setTitle("External Book");
        ext.setIsbn("1234567890");
        ext.setCover("http://cover.image");
        return ext;
    }

    @Test
    @DisplayName("관리자 책 관리 페이지 테스트")
    void test_adminBookPage() throws Exception {
        mockMvc.perform(get("/admin/books/management"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/books"));
    }

    @Test
    @DisplayName("책 관리 페이지 테스트")
    void test_bookManagePage() throws Exception {
        List<BookDetailWithExtraInfoResponse> books = List.of(createDummyBook(1L), createDummyBook(2L));

        Page<BookDetailWithExtraInfoResponse> page = new PageImpl<>(books);

        when(productServiceClient.getAllBooksWithExtraInfo(anyInt(), anyInt(), anyString())).thenReturn(page);
        when(tagServiceClient.getTagsByBookId(anyLong())).thenReturn(List.of(createDummyTag()));
        when(tagServiceClient.getAllTags()).thenReturn(List.of(createDummyTag()));

        mockMvc.perform(get("/admin/books")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/manage"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(model().attributeExists("bookPage"))
                .andExpect(model().attributeExists("currentSort"));
    }

    @Test
    @DisplayName("Api 검색 후 결과 보여주기 테스트")
    void test_searchApiAndShowResults() throws Exception {
        List<BookExternalResponse> searchResults = List.of(createDummyExternalBook());

        when(productServiceClient.searchAladinBooksByTitle(anyString())).thenReturn(searchResults);

        mockMvc.perform(get("/admin/books/search")
                        .param("title", "anyTitle"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/book_search_results"))
                .andExpect(model().attributeExists("searchResults"));
    }

    @Test
    @DisplayName("Api로 등록 폼 전송 테스트")
    void test_showRegisterFormFromApi() throws Exception {
        BookExternalResponse book = createDummyExternalBook();
        List<CategoryResponse> categories = List.of(createDummyCategory());

        when(productServiceClient.findBookByApi(anyString())).thenReturn(book);
        when(productServiceClient.getCategoryTree()).thenReturn(categories);

        mockMvc.perform(get("/admin/books/source/{isbn}/form", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/book_register_api"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("categoryTree"));
    }

    @Test
    @DisplayName("책 이미지를 통한 Api 책 등록 테스트")
    void test_registerBookFromApi_withCover() throws Exception {
        when(minioStorageService.uploadBookImage(any())).thenReturn("https://minio/book-cover.png");

        // form-data로 multipart 요청 테스트
        mockMvc.perform(multipart("/admin/books/source")
                        .file("coverFile", "dummy image content".getBytes())
                        .param("title", "title")
                        .param("author", "author")
                        .param("publisher", "publisher")
                        .param("contents", "contents")
                        .param("pubDate", "2025-07-22")
                        .param("isbn", "1234")
                        .param("regularPrice", "10000")
                        .param("salePrice", "8000")
                        .param("description", "desc")
                        .param("count", "10")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).registerBookByApi(any());
    }

    @Test
    @DisplayName("이미지 없는 Api를 통한 책 등록 테스트")
    void test_registerBookFromApi_withoutCover() throws Exception {
        mockMvc.perform(multipart("/admin/books/source")
                        .param("title", "title")
                        .param("author", "author")
                        .param("publisher", "publisher")
                        .param("contents", "contents")
                        .param("pubDate", "2025-07-22")
                        .param("isbn", "1234")
                        .param("regularPrice", "10000")
                        .param("salePrice", "8000")
                        .param("description", "desc")
                        .param("count", "10")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).registerBookByApi(any());
    }

    @Test
    @DisplayName("책 등록 페이지 테스트")
    void test_registerBookPage() throws Exception {
        List<CategoryResponse> categories = List.of(createDummyCategory());

        when(productServiceClient.getCategoryTree()).thenReturn(categories);

        mockMvc.perform(get("/admin/books/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/book_register"))
                .andExpect(model().attributeExists("categoryTree"));
    }

    @Test
    @DisplayName("수동 책 등록 테스트")
    void test_registerBookManual() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .param("title", "title")
                        .param("author", "author")
                        .param("publisher", "publisher")
                        .param("contents", "contents")
                        .param("pubDate", "2025-07-22")
                        .param("isbn", "1234")
                        .param("regularPrice", "10000")
                        .param("salePrice", "8000")
                        .param("description", "desc")
                        .param("count", "10")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).registerBookManual(any(), any());
    }

    @Test
    @DisplayName("책 삭제 테스트")
    void test_deleteBook() throws Exception {
        mockMvc.perform(post("/admin/books/{id}/delete", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).deleteBook(1L);
    }

    @Test
    @DisplayName("책 업데이트 폼 테스트")
    void test_updateBookForm() throws Exception {
        BookDetailWithExtraInfoResponse book = createDummyBook(1L);
        List<CategoryResponse> categories = List.of(createDummyCategory());

        when(productServiceClient.getBookDetailWithExtraInfo(any())).thenReturn(book);
        when(productServiceClient.getCategoryTree()).thenReturn(categories);

        mockMvc.perform(get("/admin/books/{id}/form", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book/book_update"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("categoryTree"));
    }

    @Test
    @DisplayName("새로운 이미지 책 업데이트 테스트")
    void test_updateBook_withNewCover() throws Exception {
        when(minioStorageService.uploadBookImage(any())).thenReturn("https://minio/new-cover.png");

        mockMvc.perform(multipart("/admin/books/{id}/update", 1L)
                                .file("coverFile", "dummy data".getBytes())
                                .param("title", "new title")
                                .param("author", "new author")
                                .param("publisher", "publisher")
                                .param("pubDate", "2025-07-22")
                                .param("isbn", "isbn-new")
                                .param("regularPrice", "10000")
                                .param("salePrice", "8000")
                                .param("description", "desc")
                                .param("contents", "contents")
                                .param("count", "5")
                                .param("status", "active")
                                .param("giftwrap", "true")
                                .param("level1", "1")
                                .param("level2", "2")
                                .param("categoryId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).updateBook(eq(1L), any());
    }

    @Test
    @DisplayName("이미지 없는 책 업데이트 테스트")
    void test_updateBook_withoutNewCover() throws Exception {
        mockMvc.perform(post("/admin/books/{id}/update", 1L)
                        .param("title", "new title")
                        .param("author", "new author")
                        .param("publisher", "publisher")
                        .param("pubDate", "2025-07-22")
                        .param("isbn", "isbn-new")
                        .param("regularPrice", "10000")
                        .param("salePrice", "8000")
                        .param("description", "desc")
                        .param("contents", "contents")
                        .param("cover", "existing-cover-url")
                        .param("count", "5")
                        .param("status", "active")
                        .param("giftwrap", "true")
                        .param("level1", "1")
                        .param("level2", "2")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).updateBook(eq(1L), any());
    }

    @Test
    @DisplayName("책 이미지 업로드 테스트")
    void test_uploadBookImage() throws Exception {
        when(minioStorageService.uploadBookImage(any())).thenReturn("https://minio/uploaded-image.png");

        mockMvc.perform(multipart("/admin/books/image")
                        .file("file", "dummy image content".getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().string("https://minio/uploaded-image.png"));
    }

    @Test
    @DisplayName("책 태그 추가 테스트")
    void test_addTagToBook() throws Exception {
        mockMvc.perform(post("/admin/books/{bookId}/tags", 1L)
                        .param("tagId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).addTagToBook(1L, 2L);
    }

    @Test
    @DisplayName("책 태그 삭제 테스트")
    void test_removeTagFromBook() throws Exception {
        mockMvc.perform(post("/admin/books/{bookId}/tags/{tagId}/delete", 1L, 2L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(productServiceClient).removeTagFromBook(1L, 2L);
    }

}
