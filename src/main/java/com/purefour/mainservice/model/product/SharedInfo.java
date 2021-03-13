package com.purefour.mainservice.model.product;

import com.purefour.mainservice.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharedInfo {

    private List<User> sharingUsers;

    private int totalSharedProducts;

    private int totalOwnedProducts;
}
