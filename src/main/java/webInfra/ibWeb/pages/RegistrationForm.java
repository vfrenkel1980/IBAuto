package webInfra.ibWeb.pages;

public class RegistrationForm {

    String name;
    String lname;
    String email;
    String pass;
    String phone;
    String country;
    String state;
    String company;
    String city;
    String how;
    String job;
    boolean cpp;
    boolean cshort;
    boolean cslong;
    boolean java;
    boolean unreal;
    boolean unity;
    boolean tfs;
    boolean jenkins;
    boolean mailing;
    boolean multicore;
    boolean aix;
    boolean x32bit;
    boolean maven;
    boolean solaris;
    boolean gradle;
    boolean ant;
    boolean scientific;
    boolean yocto;
    boolean clearcase;
    boolean docker;
    boolean android;
    boolean intel;
    boolean arm;
    boolean centos;
    boolean earlier;
    boolean parallelBuilds;
    boolean unlimitedInitiators;
    boolean advancedReporting;

    public RegistrationForm(String name, String lname, String email, String pass, String phone) {
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
    }

    public RegistrationForm(String name, String lname, String email, String pass, String phone, String country, String state,
                            String company, String city, String how, String job, boolean cpp, boolean cshort, boolean cslong,
                            boolean java, boolean unreal, boolean unity, boolean tfs, boolean jenkins, boolean mailing){
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.company = company;
        this.city = city;
        this.how = how;
        this.job = job;
        this.cpp = cpp;
        this.cshort = cshort;
        this.cslong = cslong;
        this.java = java;
        this.unreal = unreal;
        this.unity = unity;
        this.tfs = tfs;
        this.jenkins = jenkins;
        this.mailing = mailing;
    }

    public RegistrationForm(String name, String lname, String email, String phone, String country, String state,
                            String company, String city, String how, String job, boolean cpp, boolean cshort, boolean cslong,
                            boolean java,  boolean unreal, boolean unity, boolean tfs, boolean jenkins, boolean multicore, boolean aix, boolean x32bit, boolean maven, boolean solaris,
                            boolean gradle, boolean ant, boolean scientific, boolean clearcase, boolean yocto,
                            boolean docker, boolean android, boolean intel, boolean arm, boolean centos, boolean earlier,
                            boolean mailing){
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.company = company;
        this.city = city;
        this.how = how;
        this.job = job;
        this.cpp = cpp;
        this.cshort = cshort;
        this.cslong = cslong;
        this.java = java;
        this.unreal = unreal;
        this.unity = unity;
        this.tfs = tfs;
        this.jenkins = jenkins;
        this.mailing = mailing;
        this.multicore = multicore;
        this.aix = aix;
        this.x32bit = x32bit;
        this.maven = maven;
        this.solaris = solaris;
        this.gradle = gradle;
        this.ant = ant;
        this.scientific = scientific;
        this.clearcase = clearcase;
        this.yocto = yocto;
        this.docker = docker;
        this.android = android;
        this.intel = intel;
        this.arm = arm;
        this.centos = centos;
        this.earlier = earlier;
    }

    public RegistrationForm(String name, String lname, String email, String phone, String country, String state,
                            String company, String city, String how, String job, boolean cpp, boolean cshort, boolean cslong,
                            boolean java,  boolean unreal, boolean unity, boolean tfs, boolean jenkins, boolean parallelBuilds, boolean unlimitedInitiators, boolean advancedReporting, boolean mailing){
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.company = company;
        this.city = city;
        this.how = how;
        this.job = job;
        this.cpp = cpp;
        this.cshort = cshort;
        this.cslong = cslong;
        this.java = java;
        this.unreal = unreal;
        this.unity = unity;
        this.tfs = tfs;
        this.jenkins = jenkins;
        this.mailing = mailing;
        this.parallelBuilds = parallelBuilds;
        this.unlimitedInitiators = unlimitedInitiators;
        this.advancedReporting = advancedReporting;
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

    public String getState() {
        return state;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getHow() {
        return how;
    }

    public String getJob() {
        return job;
    }

    public boolean isCpp() {
        return cpp;
    }

    public boolean isCshort() {
        return cshort;
    }

    public boolean isCslong() {
        return cslong;
    }

    public boolean isJava() {
        return java;
    }

    public boolean isUnreal() {
        return unreal;
    }

    public boolean isUnity() {
        return unity;
    }

    public boolean isTfs() {
        return tfs;
    }

    public boolean isJenkins() {
        return jenkins;
    }

    public boolean isMailing() {
        return mailing;
    }

    public boolean isMulticore() {
        return multicore;
    }

    public boolean isAix() {
        return aix;
    }

    public boolean isX32bit() {
        return x32bit;
    }

    public boolean isMaven() {
        return maven;
    }

    public boolean isSolaris() {
        return solaris;
    }

    public boolean isGradle() {
        return gradle;
    }

    public boolean isAnt() {
        return ant;
    }

    public boolean isScientific() {
        return scientific;
    }

    public boolean isYocto() {
        return yocto;
    }

    public boolean isClearcase() {
        return clearcase;
    }

    public boolean isDocker() {
        return docker;
    }

    public boolean isAndroid() {
        return android;
    }

    public boolean isIntel() {
        return intel;
    }

    public boolean isArm() {
        return arm;
    }

    public boolean isCentos() {
        return centos;
    }

    public boolean isEarlier() {
        return earlier;
    }

    public boolean isParallelBuilds() {
        return parallelBuilds;
    }

    public boolean isUnlimitedInitiators() {
        return unlimitedInitiators;
    }

    public boolean isAdvancedReporting() {
        return advancedReporting;
    }
}
