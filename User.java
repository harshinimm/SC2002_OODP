public class User {

    protected String name;
    protected String nric;
    private String password;
    private int age;
    private String maritalStatus;

    public User(String name, String nric, int age, String maritalStatus, String password) {
    }

    public static void login(String NRIC,String password){


     }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return nric;
    }
    public String getPassword(){
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

}
