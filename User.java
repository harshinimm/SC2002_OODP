public abstract class User {
    protected String name;
    protected String nric;
    protected String password;
    protected int age;
    protected String maritalStatus;

    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public String getName() { return name; }
    public String getNric() { return nric; }
    public String getPassword() { return password; }
    public int getAge() { return age; }
    public String getMaritalStatus() { return maritalStatus; }

    public void setPassword(String password) { this.password = password; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
}


//import java.util.Scanner;

//
//public class User {
//    private String name;
//    private String NRIC;
//    private String password;
//    private int age;
//    private String maritalStatus;
//
//    // Constructor to initialize user attributes
//    public User(String name, String NRIC, String password, int age, String maritalStatus) {
//        this.name = name;
//        this.NRIC = NRIC;
//        this.password = password;
//        this.age = age;
//        this.maritalStatus = maritalStatus;
//    }
//
//    public String getNRIC() {
//        return NRIC;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String newPassword) {
//        this.password = newPassword;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    // Getter and Setter methods for maritalStatus
//    public String getMaritalStatus() {
//        return maritalStatus;
//    }
//
//    public void setMaritalStatus(String maritalStatus) {
//        this.maritalStatus = maritalStatus;
//    }
//
//    // Method to handle user login
//    public static void login() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter UserId:");
//        String NRIC = sc.nextLine();
//        System.out.println("Enter password:");
//        String password = sc.nextLine();
//
//        System.out.println("Entered UserId: " + NRIC);
//        System.out.println("Entered password: " + password);
//    }
//
//}