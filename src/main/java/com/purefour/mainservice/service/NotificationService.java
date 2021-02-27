package com.purefour.mainservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.purefour.mainservice.model.NotificationMessage;
import com.purefour.mainservice.model.product.Product;
import com.purefour.mainservice.model.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {

    private static final String PRODUCT_EXPIRING_MESSAGE = "Termin ważności twojego produktu upływa";
    private static final String PRODUCT_EXPIRED_MESSAGE = "Termin ważności twojego produktu upłynął";

    private final FirebaseMessaging firebaseMessaging;

    public String sendNotification(NotificationMessage notificationMessage, String token) throws FirebaseMessagingException {
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getSubject())
                .setBody(notificationMessage.getContent())
                .setImage(notificationMessage.getImageUrl())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();

        sendNotificationFakeCallback(notificationMessage);
        return firebaseMessaging.send(message);
    }

    private void sendNotificationFakeCallback(NotificationMessage notificationMessage) {
        log.info("Notification has been send!");
        log.info("Subject: {}", notificationMessage.getSubject());
        log.info("Content: {}", notificationMessage.getContent());
        log.info("ImageUrl: {}", notificationMessage.getImageUrl());
        log.info("Data: {}", notificationMessage.getData());
    }

    public void sendNotificationAboutOutdatedProduct(Product outdatedProduct, User outdatedProductUser) {
        NotificationMessage outdatedProductNotificationMessage = NotificationMessage.builder()
                .subject(getExpiryDateInfo(outdatedProduct))
                .content(outdatedProduct.getProductCard().getName())
                .imageUrl(outdatedProduct.getProductCard().getPhotoUrl())
                .data(Map.of("Username", outdatedProductUser.getLogin(),
                        "NotificationToken", outdatedProductUser.getNotificationToken(), //debug only
                        "ProductUuid", outdatedProduct.getUuid().toString()))
                .build();
        try {
            sendNotification(outdatedProductNotificationMessage, outdatedProductUser.getNotificationToken());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    private String getExpiryDateInfo(Product outdatedProduct) {
        return outdatedProduct.getMetadata().getExpiryDate().isBefore(LocalDate.now()) ?
                String.format(PRODUCT_EXPIRED_MESSAGE + " %s!", outdatedProduct.getMetadata().getExpiryDate())
                : String.format(PRODUCT_EXPIRING_MESSAGE + " %s!", outdatedProduct.getMetadata().getExpiryDate());
    }

}
