package com.generonumero.blocodaguarda.login.event;

public class UserProfile {

    public static int MALE = 0;
    public static int FEMALE = 1;

    private String name;
    private String email;
    private String gender;
    private String city;
    private String neighborhood;
    private boolean facebook;


    public UserProfile(String name, String email, String gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.facebook = true;
    }

    public UserProfile(String name, String email, String gender, boolean facebook) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.facebook = facebook;
    }

    public UserProfile(String name, String email, String gender, String city, String neighborhood, boolean facebook) {
        this.facebook = facebook;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public boolean isValid() {
        return isValidName() && isValidGender() && isValidEmailAddress();
    }

    public boolean isValidName() {
        if (this.getName() == null || this.getName().equals("")) {
            return false;
        }
        return true;
    }

    public boolean isValidGender() {
        if (this.getGender() == null || this.getGender().equals("")) {
            return false;
        }
        if (!(this.getGender().toLowerCase().equals("male") || this.getGender().toLowerCase().equals("female"))) {
            return false;
        }
        return true;
    }

    public boolean isValidEmailAddress() {
        if (this.getEmail() == null || this.getEmail().equals("")) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}
