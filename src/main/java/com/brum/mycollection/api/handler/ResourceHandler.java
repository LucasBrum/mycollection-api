package com.brum.mycollection.api.handler;

import com.brum.mycollection.api.exception.ArtistException;
import com.brum.mycollection.api.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(ArtistException.class)
	public ResponseEntity<Response<String>> handlerArtistException(ArtistException artistException) {
		Response<String> response = new Response<>();
		response.setData(artistException.getMessage());
		response.setStatusCode(artistException.getHttpStatus().value());

		return ResponseEntity.status(artistException.getHttpStatus()).body(response);
	}
}
