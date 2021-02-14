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

import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {
    //TODO dorobić DailySynchronizationService (sprawdzanie dat ważności produktów i wysyłanie powiadomień, mechanizm globalnej synchronizacji)

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
        return "OK";
    }

    private void sendNotificationFakeCallback(NotificationMessage notificationMessage) {
        log.info("Notification has been send!");
        log.info("Subject: {}", notificationMessage.getSubject());
        log.info("Content: {}", notificationMessage.getContent());
        log.info("ImageUrl: {}", notificationMessage.getImageUrl());
        log.info("Data: {}", notificationMessage.getData());
    }

    public void sendNotificationAboutOutdatedProduct(Product outdatedProduct, User outdatedProductUser) {
        //TODO dodać bundle z wiadomościami w ang/pl
        NotificationMessage outdatedProductNotificationMessage = NotificationMessage.builder()
                .subject("Termin ważności twojego produktu upływa jutro!")
                .content(outdatedProduct.getProductCard().getName())
                .imageUrl(outdatedProduct.getProductCard().getPhotoUrl())
                .data(Map.of())
                .build();
        try {
            sendNotification(outdatedProductNotificationMessage, "userToken");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
