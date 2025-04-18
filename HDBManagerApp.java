import java.util.Scanner;

public class HDBManagerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HDBManager manager = new HDBManager("Jessica", "S5678901G", 26, "Married","password");
        showMainMenu(scanner, manager);
        scanner.close();
    }

    private static void showMainMenu(Scanner scanner, HDBManager manager) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome HDB Managers!");
            System.out.println("Please select an option below");
            System.out.println("Main Menu");
            System.out.println("1. Manage BTO Projects");
            System.out.println("2. View all created projects");
            System.out.println("3. View Projects created by a Manager");
            System.out.println("4. View pending and approved HDB Officer registration");
            System.out.println("5. Approve or Reject HDB Officer Registration");
            System.out.println("6. Approve or Reject Applicants Registration");
            System.out.println("7. Approve or Reject Applicants Withdrawal");
            System.out.println("8. View Enquires on all projects");
            System.out.println("9. View and reply to enquires of projects handled by me");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manager.manageBTOProjects();                                    //Done
                    break;
                case 2:
                    manager.viewAllCreatedProjects();                               //Done
                    break;
                case 3:
                    manager.viewManagerProjects();                                  //Done
                    break;
                case 4:
                    manager.viewOfficerRegistration();                              //Maybe extend csv of the officer stuff so that we can view their Registration
                    break;
                case 5:
                    manager.approveOfficerRegistration();
                    break;
                case 6:
                    manager.approveApplicantRegistration();
                    break;
                case 7:
                    System.out.print("Enter Applicant NRIC: ");                     //Done
                    String nricForWithdrawal = scanner.nextLine();
                    Applicant applicant = manager.getApplicantByNric(nricForWithdrawal);
                    if (applicant != null) {
                        manager.approveApplicantWithdrawal(applicant);
                    } else {
                        System.out.println("Applicant not found.");
                    }
                    break;
                case 8:
                    Enquiry.viewAllEnquiries();                                     //Done
                    break;
                case 9:
                    Enquiry.viewProjectEnquiries(manager.getRegisteredProject());   //Done
                    break;
                case 10:
                    exit = true;                                                    //Done
                    System.out.println("Exiting the System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}