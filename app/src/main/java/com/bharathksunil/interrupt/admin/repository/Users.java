package com.bharathksunil.interrupt.admin.repository;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Bharath on 19-02-2018.
 */
@IgnoreExtraProperties
public class Users {
    private String Name, Designation, PhoneNo, Email, ProfileUrl;
    private String[] Roles;

    public Users() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.ProfileUrl = profileUrl;
    }

    public String[] getRoles() {
        return Roles;
    }

    public void setRoles(String[] roles) {
        Roles = roles;
    }
}
