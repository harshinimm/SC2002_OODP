import java.util.Scanner;

public class HDBOfficerApp {

    private HDBOfficer officer;

    public HDBOfficerApp() {
        // Initialize HDBOfficerApp without a specific officer initially
    }

    public static void main(String[] args) {
        HDBOfficerApp app = new HDBOfficerApp();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Officer login
        System.out.println("==== HDB Officer Portal ====");
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        officer = new HDBOfficer("Officer Name", nric, password, 30, "Married", "Active", "Project A", "3 Room", null);

        // Mock login verification logic (for simplicity, assuming login is always successful)
        if (!nric.equals("validNric") || !password.equals("validPassword")) {
            System.out.println("Login failed. Please check your credentials.");
            return; // Exit the app if login fails
        }

        System.out.println("Login successful. Welcome Officer " + officer.getName() + "!");
        boolean quit = false;
        while (!quit) {
            System.out.println("""
                \n===== Officer Dashboard =====
                1. Apply for a Project
                2. View Project Details
                3. Respond to Enquiries
                4. Generate Booking Receipt
                5. Book for Applicant
                6. Logout
                ==============================
                """);

            System.out.print("Select an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter Project Name to Apply: ");
                    String projectName = scanner.nextLine();
                    officer.applyProjects(projectName);
                    break;
                case "2":
                    System.out.print("Enter Project Name to View: ");
                    projectName = scanner.nextLine();
                    officer.viewDetails(projectName);
                    break;
                case "3":
                    System.out.print("Enter Project Name to View Enquiries: ");
                    projectName = scanner.nextLine();
                    officer.enquiry(projectName);
                    break;
                case "4":
                    System.out.print("Enter Applicant NRIC for Receipt: ");
                    String applicantNric = scanner.nextLine();
                    officer.generateReceipt(applicantNric);
                    break;
                case "5":
                    System.out.print("Enter Project Name: ");
                    projectName = scanner.nextLine();
                    System.out.print("Enter Flat Type (2 Room / 3 Room): ");
                    String flatType = scanner.nextLine();
                    officer.applyForProject(projectName, flatType);
                    break;
                case "6":
                    System.out.println("Logging out...");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
