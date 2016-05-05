package com.sachi.lifetweak.model;

/**
 * Created by jombay on 30/3/16.
 */
public class Options {
    private String txtOptions;
    private int optionsImg;

    public Options(String txtOptions, int optionsImg) {
        this.txtOptions = txtOptions;
        this.optionsImg = optionsImg;
    }

    public Options() {
    }

    public String getTxtOptions() {
        return txtOptions;
    }

    public void setTxtOptions(String txtOptions) {
        this.txtOptions = txtOptions;
    }

    public int getOptionsImg() {
        return optionsImg;
    }

    public void setOptionsImg(int optionsImg) {
        this.optionsImg = optionsImg;
    }
}
