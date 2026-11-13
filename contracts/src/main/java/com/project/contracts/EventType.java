package com.project.contracts;

public enum EventType {
    MEMBER_REGISTERED("MemberRegistered","member-events",MemberRegistered.class),
    MEMBERSHIP_PENDING("MembershipPending","membership-events", MembershipPending.class),
    INVOICE_GENERATED("InvoiceCreated","payment-events", InvoiceCreated.class),
    PAYMENT_COMPLETED("PaymentSuccessful", "payment-events", PaymentSuccessfull.class),
    MEMBERSHIP_ACTIVATED("MembershipActivated","membership-events", MembershipActivated.class);

    private final String wireName;
    private final String topic;
    private final Class<?> payloadClass;

    EventType(String wireName, String topic, Class<?> payloadClass) {
        this.wireName = wireName;
        this.topic = topic;
        this.payloadClass = payloadClass;
    }
    public String getWireName(){return wireName;}
    public String getTopic(){return topic;}
    public Class<?> getPayloadClass(){return payloadClass;}

    public static EventType forPayload(Class<?> payloadClass){
        for(EventType type : values()){
            if(type.payloadClass == payloadClass)
                return type;
        }
        throw new IllegalArgumentException("No event type for Payload class : "+payloadClass.getName());
    }
}
