package com.example.demo.util;

public class ErrorInfo {

    private CodeValue category;
    private String messageText;
    private String state;
    private String source;
    private String code;
    private String description;

    public CodeValue getCategory() {
        return category;
    }

    public void setCategory(CodeValue category) {
        this.category = category;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "category=" + category +
                ", messageText='" + messageText + '\'' +
                ", state='" + state + '\'' +
                ", source='" + source + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
