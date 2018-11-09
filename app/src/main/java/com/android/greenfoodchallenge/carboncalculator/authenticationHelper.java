package com.android.greenfoodchallenge.carboncalculator;

// helps in the authentication process
public class authenticationHelper {
    private String userId;
    authenticationHelper(){

    }
    public void setUserId(String user){
        userId=user;
    }
    public String getUserId(){
        return userId;
    }
}
