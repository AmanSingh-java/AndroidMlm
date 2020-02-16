package com.finnotive.mlm;

public class RegistrationModel {
    private String username;
    private String email;
    private String mobile;
    private String password ;
    private String conpassword;
    private String reffercode;

    public RegistrationModel(String username, String email, String mobile, String password, String conpassword, String reffercode) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.conpassword = conpassword;
        this.reffercode = reffercode;
    }

    public RegistrationModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

    public String getReffercode() {
        return reffercode;
    }

    public void setReffercode(String reffercode) {
        this.reffercode = reffercode;
    }

    @Override
    public String toString() {
        return "RegistrationModel{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", conpassword='" + conpassword + '\'' +
                ", reffercode='" + reffercode + '\'' +
                '}';
    }
}
