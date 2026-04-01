package api.payloads;

import com.google.gson.Gson;

import silver.api.Payload;

public class UpdateContact implements Payload {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String email;
    private String phone;
    private String street1;
    private String street2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;

    public UpdateContact() {
        firstName = null;
        lastName = null;
        birthdate = null;
        email = null;
        phone = null;
        street1 = null;
        street2 = null;
        city = null;
        postalCode = null;
        country = null;
        stateProvince = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "\nUpdateContact [firstName=" + firstName + ", lastName=" + lastName + ", birthdate=" + birthdate
                + ", email="
                + email + ", phone=" + phone + ", street1=" + street1 + ", street2=" + street2 + ", city=" + city
                + ", stateProvince=" + stateProvince + ", postalCode=" + postalCode + ", country=" + country + "]";
    }

    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);

    }

    @Override
    public void setValue(String fieldName, String fieldValue) {

        switch (fieldName) {
            case "firstName":
                setFirstName(fieldValue);
                break;
            case "lastName":
                setLastName(fieldValue);
                break;
            case "birthdate":
                setBirthdate(fieldValue);
                break;
            case "phone":
                setPhone(fieldValue);
                break;
            case "street1":
                setStreet1(fieldValue);
                break;
            case "street2":
                setStreet2(fieldValue);
                break;
            case "city":
                setCity(fieldValue);
                break;
            case "postalCode":
                setPostalCode(fieldValue);
                break;
            case "country":
                setCountry(fieldValue);
                break;
            case "stateProvince":
                setStateProvince(fieldValue);
                break;
            case "email":
                setEmail(fieldValue);
                break;                
            default:
                break;
        }

    }

}
