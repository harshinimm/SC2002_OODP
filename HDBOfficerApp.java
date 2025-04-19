import java.util.Scanner;

public class HDBOfficerApp {
    public static void main(String[] args) {
        HDBOfficer officer = new HDBOfficer("", "", "", 0, "", "pending","", "", null);

        Scanner sc = new Scanner(System.in);
        boolean quit = false;

        System.out.println("Welcome to the HDB Officer Portal");

        System.out.print("Enter your NRIC: ");
        String nric = sc.nextLine();

        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        boolean isAuthenticated = officer.isOfficer(nric, password);
        System.out.println("Welcome Officer"+officer.getName());

        if (!isAuthenticated) {
            System.out.println("Invalid credentials or not registered as HDB Officer.");
            return;
        }

        while (!quit) {
            System.out.println("""
                \nHDB Officer Menu:
                1. Apply for Project
                2. View Project Details
                3. Answer Enquiries
                4. Generate Receipt for Applicant
                5. Exit
                Please select an option (1-5):
                """);

            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1 -> {
                    System.out.print("Enter Project Name: ");
                    String projectName = sc.nextLine();
                    System.out.print("Enter Flat Type: ");
                    String flatType = sc.nextLine();
                    officer.applyForProject(projectName, flatType);
                }
                case 2 -> {
                    System.out.print("Enter Project Name: ");
                    String projectName = sc.nextLine();
                    officer.viewDetails(projectName);
                }
                case 3 -> {
                    System.out.print("Enter Project Name to handle enquiries: ");
                    String projectName = sc.nextLine();
                    officer.enquiry(projectName);
                }
                case 4 -> {
                    System.out.print("Enter Applicant NRIC to generate receipt: ");
                    String applicantNric = sc.nextLine();
                    officer.generateReceipt(applicantNric);
                }
                case 5 -> {
                    quit = true;
                    System.out.println("Logging out. Have a great day!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}
