package com.purefour.mainservice.model.product;

import com.purefour.mainservice.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutdatedProductWithUsersData {

    private Product outdatedProduct;
    private List<User> users;
}
