package com.example.backend.kernel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ResponseApi<T> {
    private T data;
    private HttpStatus status;
    private boolean error;
    private String message;

    public ResponseApi(HttpStatus status, boolean error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
