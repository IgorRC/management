package com.gniuscode.sie.management.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(String message){
        this.success = true;
        this.message = message;
        this.data = null;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> error(String message, T data){
        return new ApiResponse<>(false,message,data,LocalDateTime.now());
    }
}
