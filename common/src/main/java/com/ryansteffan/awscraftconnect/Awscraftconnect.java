package com.ryansteffan.awscraftconnect;

import com.ryansteffan.awscraftconnect.responses.Instance;
import com.ryansteffan.awscraftconnect.responses.ListInstancesResponse;
import dev.architectury.event.events.client.ClientGuiEvent;

import java.util.logging.Logger;

public final class Awscraftconnect {
    public static final String MOD_ID = "awscraftconnect";
    public static final AWSCraftConfig CONFIG = new AWSCraftConfig(null, null);
    public static AWSCraftClient CLIENT = new AWSCraftModClient(CONFIG);

    public static void init() {
        System.out.println("Initializing AWS Craft Connect...");
        System.out.println("AWS Craft Connect initialized successfully.");
    }
}
