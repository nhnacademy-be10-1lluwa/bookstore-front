package com.nhnacademy.illuwa.book.service;

import com.nhnacademy.illuwa.config.TestUtils;
import io.minio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MinioStorageServiceTest {
    @Mock
    MinioClient minioClient;

    @InjectMocks
    MinioStorageService storageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TestUtils.setField(storageService, "bucket", "test-bucket");
    }

    @Test
    @DisplayName("책 이미지 업로드 성공")
    void uploadBookImage_success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "cover.jpg", "image/jpeg", "dummy".getBytes());
        String expectUrl = "http://localhost:9000/book/some-file.jpg";
        when(minioClient.putObject(any(PutObjectArgs.class))).thenReturn(mock(ObjectWriteResponse.class));
        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class))).thenReturn(expectUrl);

        String result = storageService.uploadBookImage(file);

        assertThat(result).isEqualTo(expectUrl);
        verify(minioClient).putObject(any(PutObjectArgs.class));
        verify(minioClient).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
    }

    @Test
    @DisplayName("책 이미지 업로드 지원하지 않는 확장자")
    void uploadBookImage_notAllowedExtension(){
        MockMultipartFile file = new MockMultipartFile("file", "cover.exe", "image/jpeg", "dummy".getBytes());

        assertThatThrownBy(() -> storageService.uploadBookImage(file))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 확장자");
    }

    @Test
    @DisplayName("책 이미지 업로드지원하지 않는 ContentType")
    void uploadBookImage_notAllowedContentType() {
        MockMultipartFile file = new MockMultipartFile("file", "cover.jpg", "application/octet-stream", "dummy".getBytes());

        assertThatThrownBy(() -> storageService.uploadBookImage(file))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 ContentType");
    }

    @Test
    @DisplayName("이미지 업로드 실패")
    void uploadBookImage_minioError() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "cover.jpg", "image/jpeg", "dummy".getBytes());
        doThrow(new RuntimeException("fail!")).when(minioClient).putObject(any(PutObjectArgs.class));

        assertThatThrownBy(() -> storageService.uploadBookImage(file))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("이미지 업로드 실패");
    }

    @Test
    @DisplayName("존재하지 않을 시 Bucket 생성")
    void init_createsBucketIfNotExists() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
        doNothing().when(minioClient).makeBucket(any(MakeBucketArgs.class));

        storageService.init();

        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    @DisplayName("Bucket 존재 시")
    void init_bucketAlreadyExists() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);

        storageService.init();

        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, never()).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    @DisplayName("validate 메서드 테스트")
    void validateExtensionTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "cover.exe", "image/jpeg", "dummy".getBytes());
        Method validate = MinioStorageService.class.getDeclaredMethod("validate", MultipartFile.class);
        validate.setAccessible(true);

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> {
            try {
                validate.invoke(storageService, file);
            } catch (Exception e) {
                throw e.getCause();
            }
        });

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 확장자");
    }
}
