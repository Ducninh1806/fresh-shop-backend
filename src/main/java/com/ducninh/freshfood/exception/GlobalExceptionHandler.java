package com.ducninh.freshfood.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex) {
		if(ex instanceof LogicException) {
			ErrorCode errorCode = ((LogicException) ex).getErrorCode();
			switch (errorCode){
				case RECORD_NOT_FOUND:
				case RECORD_NOT_EXISTED:
					return new ResponseEntity<>(getErrorDetails(errorCode), HttpStatus.NOT_FOUND);
				case RECORD_EXISTED:
					return new ResponseEntity<>(getErrorDetails(errorCode), HttpStatus.CONFLICT);
				case EMAIL_EXISTED:
				case FORMAT_NOT_SUPPORT:
				case BAD_REQUEST:
					return new ResponseEntity<>(getErrorDetails(errorCode), HttpStatus.BAD_REQUEST);
				default:
					return new ResponseEntity<>(getErrorDetails(errorCode), HttpStatus.INTERNAL_SERVER_ERROR);

			}
		}
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(getErrorDetails(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	private ErrorDetails getErrorDetails(ErrorCode errorCode){
		return new ErrorDetails(new Date(), errorCode.getCode(), errorCode.getMessage());
	}
}
