package com.keyloop.example.inventorydashboard.enums;

public enum VehicleStatus {

    AGING_REVIEW("Aging - Under Review"),
    AUCTION_READY("Scheduled for Auction"),
    AVAILABLE("Available"),
    DEALER_TRANSFER("Pending Dealer Transfer"),
    HOLD("On Hold"),
    PRICE_REDUCTION_PLANNED("Price Reduction Planned"),
    SOLD("Sold");

    private final String displayName;

    VehicleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
