package com.ducninh.freshfood.exception;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("500", "Internal server error"),
    FORBIDDEN("403", "Permission denied"),
    RECORD_NOT_FOUND("003", "RECORD_NOT_FOUND"),
    RECORD_NOT_EXISTED("004", "RECORD_NOT_EXISTED"),
    UPLOAD_FILE_FAILED("007", "Upload file failed"),
    USER_NOT_ACTIVE("008", "User not active"),
    USER_LOCKED("009", "User locked"),
    RECORD_EXISTED("041","RECORD_EXISTED"),
    EMAIL_EXISTED("500","EMAIL_EXISTED!"),
    CURRENT_PASSWORD_INCORRECT("007","CURRENT PASSWORD INCORRECT!"),
    ROLE_INVALID("400","ROLE DOES NOT EXIST!"),
    ACTIVE_KEY_NOT_FOUND("777","KEY ACTIVE USER NOT FOUND!"),
    RECORD_IS_USING("042", " CURRENT LOCATION IS USING"),
    RECORD_PACKAGE_IS_USING("044", " CURRENT PACKAGE IS USING"),
    ADMIN_DELETE_DENIED("700", "CAN'T DELETE THIS USER"),
    END_DATE_NOT_EMPTY("11", "YOU CAN'T EMPTY FIELD END_DATE"),
    USER_IS_ACITIVED("011","The user has been activated"),
    BAD_REQUEST("404","BAD_REQUEST"),
    DELETE_FILE_FAILED("014", "Delete file false"),
    INVALID_IMAGE("015", "INVALID IMAGE"),
    IMAGE_EXCEEDS_LIMIT("016", "Number of image exceeds limit"),
    ATTACHMENT_INVALID("017", "ATTACHMENT INVALID"),
    EMAIL_NOT_SENT("018", "Email apply job is not sent"),
    FORMAT_NOT_SUPPORT("019", "FORMAT NOT SUPPORT"),
    NAME_EXISTED("020", "NAME EXIST"),
    PRODUCT_NOT_EXISTED("021", "PRODUCT_NOT_EXISTED"),

    ;
    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
