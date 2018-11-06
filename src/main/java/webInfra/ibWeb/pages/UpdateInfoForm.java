package webInfra.ibWeb.pages;

public class UpdateInfoForm {

    String name;
    String lname;
    String phone;
    String country;
    String company;
    String city;

    public UpdateInfoForm(String name, String lname, String phone, String country, String company, String city) {
        this.name = name;
        this.lname = lname;
        this.phone = phone;
        this.country = country;
        this.company = company;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }
}
