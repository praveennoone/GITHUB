package com.gavs.hishear.web.domain;

/**
 * User: v.martos
 * Date: 31.10.14
 */
public class WelcomeMessage {
    public static enum Type {
        INFO,
        WARN
    }

    private String text;
    private Type type;

    public WelcomeMessage() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(String type) {
        this.type = "W".equalsIgnoreCase(type) ? Type.WARN : Type.INFO;
    }
}
