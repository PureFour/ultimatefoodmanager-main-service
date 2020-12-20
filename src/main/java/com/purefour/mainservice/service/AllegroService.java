package com.purefour.mainservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.purefour.mainservice.feign.AllegroApiClient;
import com.purefour.mainservice.feign.AllegroAuthClient;
import com.purefour.mainservice.model.product.Price;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class AllegroService {

    private final AllegroAuthClient apiAuthClient;
    private final AllegroApiClient allegroClient;

    @Value("${allegroApi.encodedClientKey}")
    private final String apiKey;

    public Price getProductPriceByBarcode(String barcode) {
        final String bearerToken = apiAuthClient.getClientToken(apiKey);

        final JsonNode apiResponse = allegroClient.getProductByBarcode(bearerToken, barcode);

        final JsonNode foundItems = apiResponse.findValue("items");
        final ArrayNode regularOffers = foundItems.withArray("regular");
        final ArrayNode promotedOffers = foundItems.withArray("promoted");

        final ArrayNode allProducts = regularOffers.addAll(promotedOffers);

        return getFirstFilteredProductPrice(allProducts);
    }

    private Price getFirstFilteredProductPrice(ArrayNode products) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(products.elements(), Spliterator.ORDERED), false)
                .filter(this::offerTypeFilter)
                .map(this::mapToProductPrice)
                .findFirst().orElse(new Price());
    }

    private boolean offerTypeFilter(JsonNode jsonNode) {
        final String offerType = Optional.ofNullable(jsonNode.get("sellingMode"))
                .map(node -> node.get("format"))
                .map(JsonNode::asText).orElse("");

        return !offerType.isEmpty() && offerType.equals("BUY_NOW");
    }

    private Price mapToProductPrice(JsonNode jsonNode) {
        final JsonNode priceNode = jsonNode.findValue("price");
        final Double priceValue = Optional.ofNullable(priceNode.get("amount")).map(JsonNode::asDouble).orElse(0.0);
        final String priceCurrency = Optional.ofNullable(priceNode.get("currency")).map(JsonNode::asText).orElse("PLN");

        return Price.builder()
                .value(priceValue.floatValue())
                .currency(priceCurrency).build();
    }
}
