package org.example.client;

import lombok.Data;

@Data
public class LightClient {
    private static final String GATEWAY_HOST = "http://localhost:18000";
    private String accessKey;
    private String secretKey;

    public LightClient(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


}
