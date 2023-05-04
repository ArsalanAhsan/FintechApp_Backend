package com.iconsult.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDtoResponse {

    private OtpStatus status;
    private String message;
}
