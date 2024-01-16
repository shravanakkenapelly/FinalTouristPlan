package com.admin.exceptions;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



@ControllerAdvice
public class GlobalExceptionHandler {
	
		@ExceptionHandler(AdminException.class)
		public ResponseEntity<?> adminException(AdminException ex, WebRequest request){
			ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(Exception.class)
		public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request){
			ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
			return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		@ExceptionHandler(AppException.class)
		public ResponseEntity<?> appException(AppException ex, WebRequest request){
			ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
}
