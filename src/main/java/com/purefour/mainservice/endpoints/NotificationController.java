package com.purefour.mainservice.endpoints;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.purefour.mainservice.model.NotificationMessage;
import com.purefour.mainservice.model.exceptions.NotFoundException;
import com.purefour.mainservice.model.exceptions.UnauthorizedException;
import com.purefour.mainservice.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Notification Controller")
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@ApiOperation(value = "Send notification")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Operation successful!"),
		@ApiResponse(code = 401, message = "Unauthorized!"),
		@ApiResponse(code = 404, message = "User not found!", response = NotFoundException.class),
	})
	@PostMapping("send")
	public ResponseEntity<String> sendNotification(@RequestBody NotificationMessage notificationMessage, @RequestParam String token)
            throws UnauthorizedException, FirebaseMessagingException {
		return ResponseEntity.ok(notificationService.sendNotification(notificationMessage, token));
	}

}
