package com.dydzik.factory;

import com.dydzik.entity.BusinessClient;
import com.dydzik.entity.Client;
import com.dydzik.entity.IndividualClient;

public class ClientFactory {

    public static Client getClient(String type, int id, String name) {
        if (type.equalsIgnoreCase("individual")) {
            return new IndividualClient("individual", id, name);
        } else if (type.equalsIgnoreCase("business")) {
            return new BusinessClient("business", id, name);
        } else {
            throw new IllegalArgumentException("Unknown client type: " + type);
        }
    }
}
