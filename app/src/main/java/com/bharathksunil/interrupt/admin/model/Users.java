package com.bharathksunil.interrupt.admin.model;

import android.support.annotation.Keep;

import com.bharathksunil.interrupt.auth.model.User;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * This is the structure of the Organisers and inherits the {@link User}
 * class.
 * @author Bharath on 19-02-2018.
 */
@Keep
@IgnoreExtraProperties
public class Users {
    private String Name, Designation, PhoneNo, Email, ProfileUrl;
    private List<String> Roles;

    @SuppressWarnings("WeakerAccess")
    public Users() {
    }

    public Users(String name, String designation, String phoneNo, String email, String profileUrl, List<String> roles) {
        Name = name;
        Designation = designation;
        PhoneNo = phoneNo;
        Email = email;
        ProfileUrl = profileUrl;
        Roles = roles;
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

    public List<String> getRoles() {
        return Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }
}
