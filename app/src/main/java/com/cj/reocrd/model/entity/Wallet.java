package com.cj.reocrd.model.entity;

/**
 * Created by Administrator on 2018/4/22.
 */

public class Wallet {
    private String balance;
    private String useableblance;
    private String freeze;
    private String score;
    private String stock;
    private String gold;
    private String sellscore;

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getSellscore() {
        return sellscore;
    }

    public void setSellscore(String sellscore) {
        this.sellscore = sellscore;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUseableblance() {
        return useableblance;
    }

    public void setUseableblance(String useableblance) {
        this.useableblance = useableblance;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
