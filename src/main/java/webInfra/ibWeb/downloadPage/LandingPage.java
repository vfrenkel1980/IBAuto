package webInfra.ibWeb.downloadPage;

public class LandingPage {

    private String name;
    private String lname;
    private String email;
    private String pass;
    private String phone;
    private String country;
    private String industry;
    private String company;
    private String devCount;
    private boolean mailing;

    public LandingPage(String name, String lname, String email, String pass, String phone, String country, String industry, String company, String devCount, boolean mailing) {
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.country = country;
        this.industry = industry;
        this.company = company;
        this.devCount = devCount;
        this.mailing = mailing;
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

    public String getPass() {
        return pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCompany() {
        return company;
    }

    public String getDevCount() {
        return devCount;
    }

    public boolean isMailing() {
        return mailing;
    }
}
