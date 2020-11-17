package org.template.core.mesassging;

public class JmsMessageError {
    private String queue;
    private String messageId;
    private String message;
    private Integer deliveriesTries;

    public String getQueue() {
        return queue;
    }

    public JmsMessageError setQueue(String queue) {
        this.queue = queue;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public JmsMessageError setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JmsMessageError setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getDeliveriesTries() {
        return deliveriesTries;
    }

    public JmsMessageError setDeliveriesTries(Integer deliveriesTries) {
        this.deliveriesTries = deliveriesTries;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "queue='" + queue + '\'' +
                ", messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", deliveriesTries=" + deliveriesTries +
                '}';
    }
}
