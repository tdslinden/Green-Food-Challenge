package com.android.greenfoodchallenge.carboncalculator;

public class CardItem {

    private int background;
    private String title;
    private String buttonText;


    public CardItem(int background, String title, String buttonText) {
        this.background = background;
        this.title = title;
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

    public void setBackground(int background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

}
