package webInfra.ibWeb.pages;

public class SignupPage {

    String name;
    String lname;
    String email;
    String phone;
    String pass;
    String country;
    String state;
    String company;
    String city;
    String licenseeMail;
    String LicenseeName;

    public SignupPage(String name, String lname, String email, String phone, String pass, String country, String state, String company, String city, String licenseeMail, String licenseeName) {
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.country = country;
        this.state = state;
        this.company = company;
        this.city = city;
        this.licenseeMail = licenseeMail;
        LicenseeName = licenseeName;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPass() {
        return pass;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getLicenseeMail() {
        return licenseeMail;
    }

    public String getLicenseeName() {
        return LicenseeName;
    }
}
