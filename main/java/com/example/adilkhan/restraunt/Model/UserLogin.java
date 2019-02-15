package com.example.adilkhan.restraunt.Model;

public class UserLogin {

    String username;
    String mobile;
    String password;
    private String isstaff;
    private String secureCode;

    public UserLogin(){}

    public UserLogin(String username, String mobile, String password, String secureCode) {
        this.username = username;
        this.mobile = mobile;
        this.password = password;
        isstaff = "false";
        this.secureCode = secureCode;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getIsstaff() {
        return isstaff;
    }

    public void setIsstaff(String isstaff) {
        this.isstaff = isstaff;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", isstaff='" + isstaff + '\'' +
                ", secureCode='" + secureCode + '\'' +
                '}';
    }
}
