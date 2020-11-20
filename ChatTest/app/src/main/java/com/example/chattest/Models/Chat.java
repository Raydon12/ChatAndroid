package com.example.chattest.Models;

public class Chat {

    private String Receiver;
    private String Sender;
    private String Message;

    public Chat(String receiver, String sender, String message) {
        Receiver = receiver;
        Sender = sender;
        Message = message;
    }

    public Chat(){}
    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
