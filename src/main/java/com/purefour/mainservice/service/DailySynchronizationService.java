package com.purefour.mainservice.service;

import com.purefour.mainservice.model.product.OutdatedProductWithUsersData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DailySynchronizationService {

    private final ProductService productService;
    private final NotificationService notificationService;
    //    @Scheduled(cron = "1 * * * * *") //every minute
    @Scheduled(cron = "0 0 * * * *") //every midnight
    public void handleOutdatedProducts() {
        log.info("[DailySynchronizationService Job] handleOutdatedProducts...");

        final List<OutdatedProductWithUsersData> outdatedProductsWithUsersData = productService.getOutdatedProducts();

        outdatedProductsWithUsersData.forEach(outdatedProductWithUsersData ->
                outdatedProductWithUsersData.getUsers().forEach(outdatedProductUser ->
                        notificationService.sendNotificationAboutOutdatedProduct(outdatedProductWithUsersData.getOutdatedProduct(), outdatedProductUser)
        ));
    }
}
