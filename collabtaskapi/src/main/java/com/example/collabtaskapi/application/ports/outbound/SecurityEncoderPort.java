package com.example.collabtaskapi.application.ports.outbound;

public interface SecurityEncoderPort {

    String encode(String rawPassword);

}
