package com.amit.exception;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amit.constants.AppConstants;

@RestControllerAdvice
public class AppExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(AppExceptionHandler.class);
	public ResponseEntity<AppExceptions> handleException(String exMsg){
		
		logger.debug("{AppConstants.APP_EXCE}{exMsg}");

		AppExceptions ex=new AppExceptions();
		ex.setExCode(AppConstants.APP_CODE);
		ex.setExDesc(exMsg);
		ex.setExDate(LocalDate.now());
		return new ResponseEntity<>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
