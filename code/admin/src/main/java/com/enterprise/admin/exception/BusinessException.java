package com.enterprise.admin.exception;

import lombok.Getter;

/**
 * Business Exception - Lỗi nghiệp vụ
 * Best Practice: Tách biệt Technical Exception và Business Exception
 * 
 * Business Exception: do logic nghiệp vụ không hợp lệ
 * - Không cần log stack trace
 * - Trả về message cho user
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
