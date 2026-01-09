package com.enterprise.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard API Response Wrapper
 * Best Practice: Tất cả API response đều có cùng format
 * 
 * Success:
 * {
 * "status": 200,
 * "message": "Success",
 * "data": { ... },
 * "timestamp": "2026-01-08T14:00:00"
 * }
 * 
 * Error:
 * {
 * "status": 400,
 * "message": "Validation error",
 * "error": "BAD_REQUEST",
 * "path": "/api/admin/groups",
 * "timestamp": "2026-01-08T14:00:00"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private String error;
    private String path;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // Success responses
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .status(201)
                .message("Created successfully")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> noContent() {
        return ApiResponse.<Void>builder()
                .status(204)
                .message("Deleted successfully")
                .build();
    }

    // Error responses
    public static <T> ApiResponse<T> error(int status, String message, String error, String path) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .error(error)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> badRequest(String message, String path) {
        return error(400, message, "BAD_REQUEST", path);
    }

    public static <T> ApiResponse<T> notFound(String message, String path) {
        return error(404, message, "NOT_FOUND", path);
    }

    public static <T> ApiResponse<T> unauthorized(String message, String path) {
        return error(401, message, "UNAUTHORIZED", path);
    }

    public static <T> ApiResponse<T> forbidden(String message, String path) {
        return error(403, message, "FORBIDDEN", path);
    }

    public static <T> ApiResponse<T> internalError(String message, String path) {
        return error(500, message, "INTERNAL_SERVER_ERROR", path);
    }
}
