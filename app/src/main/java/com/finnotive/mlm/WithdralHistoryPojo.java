package com.finnotive.mlm;

public class WithdralHistoryPojo {
    private String bank;
    private String account;
    private String amount;
    private String ifsc;
    private String date;
    private String paymentid;
    private String status;

    public WithdralHistoryPojo(String bank, String account, String amount, String ifsc, String date, String paymentid, String status) {
        this.bank = bank;
        this.account = account;
        this.amount = amount;
        this.ifsc = ifsc;
        this.date = date;
        this.paymentid = paymentid;
        this.status = status;
    }

    public WithdralHistoryPojo() {
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WithdralHistoryPojo{" +
                "bank='" + bank + '\'' +
                ", account='" + account + '\'' +
                ", amount='" + amount + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", date='" + date + '\'' +
                ", paymentid='" + paymentid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
