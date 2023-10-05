package ku.cs.model.account;

public class Account {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String postcode;
    private int status;
    public Account() {
    }

    public Account(String username, String password, String name, String email, String phone, String address, String role, String postcode, int status) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.postcode = postcode;
        this.status = status;
    }

    public Account(String role, String name, String username, String password) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidPassword(String password) {
        return ((password!= null)
                && (!password.equals(""))
                && (password.matches("^[a-zA-Z0-9]{8,20}$")));
    }

    public boolean isValidUsername(String username) {
        return ((username!= null)
                && (!username.equals(""))
                && (username.matches("^[a-zA-Z]+(.+){8,20}$")));
    }

    public boolean isValidAddress(String address) {
        return ((address!= null)
                && (!address.equals(""))
                && (address.matches("^[a-zA-Z]+(.+){8,20}$")));
    }

    public boolean isValidPhone(String phone) {
        return ((phone!= null)
                && (!phone.equals(""))
                && (phone.matches("^[0-9]{10}$")));
    }

    public boolean isValidPostcode(String postcode) {
        return ((postcode!= null)
                && (!postcode.equals(""))
                && (postcode.matches("^[0-9]{5}$")));
    }
    public boolean isValidName(String name) {
        return ((name!= null) && (!name.equals("")));
    }

}
