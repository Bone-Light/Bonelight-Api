package org.example.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class InvokeCheckDTO {
    String accessKey;
    String nonce;
    String timestamp;
    String sign;
    String body;
}
