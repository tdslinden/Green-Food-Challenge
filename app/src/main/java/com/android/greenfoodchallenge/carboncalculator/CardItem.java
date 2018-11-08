package com.android.greenfoodchallenge.carboncalculator;

public class CardItem {

    private int background;
    private String title;
    private String description;
    private String buttonText;

    public CardItem(int background, String title, String description, String buttonText) {
        this.background = background;
        this.title = title;
        this.description = description;
        this.buttonText = buttonText;
    }

    public int getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getDescription() {
        return description;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
