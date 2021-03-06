package com.bharathksunil.interrupt.auth.model;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * The structure how the user information is stored in the firebase database
 *
 * @author Bharath Kumar S
 */
@Keep
@SuppressWarnings("unused")
@IgnoreExtraProperties
public class User {
    private String Name, PhoneNo, USN, Department, Semester, Section, Email, ProfileUrl;

    @SuppressWarnings("WeakerAccess")
    public User() {
    }

    public User(String name, String phoneNo, String USN, String department, String semester,
                String section, String email, String profileUrl) {
        Name = name;
        PhoneNo = phoneNo;
        this.USN = USN;
        Department = department;
        Semester = semester;
        Section = section;
        Email = email;
        ProfileUrl = profileUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getEmail() {
        return Email.toLowerCase();
    }

    public void setEmail(String email) {
        Email = email.toLowerCase();
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        ProfileUrl = profileUrl;
    }
}
