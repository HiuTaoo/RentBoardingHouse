package com.hiutao.RentBoardingHouse.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String Username;
    private String Password;
    private String Email;
    private String PhoneNum;
    private String Nickname;
    private Boolean IsOnline;
    private String Role;
    private String Avt;

}
