package com.nhnacademy.illuwa.review.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadResponse {
    private String objectName;
    private String url;
    private String changedName;
}
