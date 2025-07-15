package com.nhnacademy.illuwa.auth.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageResponse {
    boolean success;
    String email;
    String message;
    String text;
}
