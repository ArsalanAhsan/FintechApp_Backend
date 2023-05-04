package com.iconsult.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDtoRequest {

    @JsonProperty("mobileNumber")
    private String mobileNumber;

}
