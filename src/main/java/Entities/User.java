package Entities;




import java.io.Serializable;


public class User implements Serializable {

    private int userId;


    private String login;




    private String password;


    private Buyer buyer;


    private Role role;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public void setRoleValue(String roleName) {
        this.role.setRoleValue(roleName);
    }
    public String getRoleValue() {
        return role.getRoleValue();
    }
    public void setBuyerName(String buyerName){
        this.buyer.setName(buyerName);
    }
    public String getBuyerName(){
        return buyer.getName();
    }




    public String getBuyerPhoneNumber() {
        return buyer.getPhoneNumber();
    }

    public void setBuyerPhoneNumber(String phoneNumber) {
        this.buyer.setPhoneNumber(phoneNumber);
    }

    public Address getBuyerAddress() {
        return buyer.getAddress();
    }

    public void setPersonAddress(Address address) {
        this.buyer.setAddress(address);
    }

    public Sex getBuyerSex() {
        return buyer.getSex();
    }

    public void setBuyerSex(Sex sex) {
        this.buyer.setSex(sex);
    }
    public void setBuyerEmail(String email) {
        this.buyer.setEmail(email);
    }
    public String getBuyerEmail() {
        return buyer.getEmail();
    }
    public String getSexValue() {
        return buyer.getSex().getSexValue();
    }

    public void setSexValue(String sexValue) {
        this.buyer.getSex().setSexValue(sexValue);
    }

    public String getCountry() {
        return buyer.getAddress().getCountry();
    }

    public void setCountry(String country) {
        this.buyer.getAddress().setCountry(country);
    }

    public String getCity() {
        return buyer.getAddress().getCity();
    }

    public void setCity(String city) {
        this.buyer.getAddress().setCity(city);
    }

    public String getStreet() {
        return buyer.getAddress().getStreet();
    }

    public void setStreet(String street) {
        this.buyer.getAddress().setStreet(street);
    }

    public int getHouseNumber() {
        return buyer.getAddress().getHouseNumber();
    }

    public void setHouseNumber(int houseNumber) {
        this.buyer.getAddress().setHouseNumber(houseNumber);
    }

    public int getPostIndex() {
        return buyer.getAddress().getPostIndex();
    }

    public void setPostIndex(int postIndex) {
        this.buyer.getAddress().setPostIndex(postIndex);
    }
}