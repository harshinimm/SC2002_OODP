import java.util.*;
import java.io.*;

public class ApplicantApp {
    private static final String APPLICANT_FILE = "data/ApplicantList.csv";
    private static final String PROJECT_FILE = "data/ProjectList.csv";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Project> projects = FileHandler.loadProjects(PROJECT_FILE);
        List<Applicant> applicants = FileHandler.loadApplicants(APPLICANT_FILE);

        System.out.print("Enter NRIC to login: ");
        String nric = sc.nextLine();
        Applicant applicant = applicants.stream().filter(a -> a.getNric().equals(nric)).findFirst().orElse(null);

        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        System.out.println("Login successful!\n");
        System.out.println("Name: " + applicant.getName());
        System.out.println();

        while (true) {
            System.out.println("Menu");
            System.out.println("-----------------------");
            System.out.println("1. View project");
            System.out.println("2. Apply project");
            System.out.println("3. Withdraw application");
            System.out.println("4. Submit enquiry");
            System.out.println("5. Delete enquiry");
            System.out.println("6. Edit enquiry");
            System.out.println("7. Change password");
            System.out.println("8. Exit");
            System.out.println("-----------------------");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    List<String[]> eligibleProjects = applicant.getEligibleProjects(projects);
                    boolean hasEligible = !eligibleProjects.isEmpty();
                    boolean hasApplied = !applicant.getAppliedProject().isEmpty();

                    if (!hasEligible) {
                        System.out.println("\n⚠️ You do not meet the requirements to apply for a project.\n");
                    } else {
                        System.out.println("\nEligible Projects:");
                        System.out.printf("%-20s %-10s %-10s %-18s %-18s\n", "Project Name", "Location", "Flat Type", "Opening Date", "Closing Date");
                        System.out.println("----------------------------------------------------------------------------------");

                        for (String[] row : eligibleProjects) {
                            System.out.printf("%-20s %-10s %-10s %-18s %-18s\n", row[0], row[1], row[2], row[3], row[4]);
                        }
                    }

                    if (hasApplied) {
                        System.out.println("\nMy Projects:");
                        System.out.printf("%-20s %-10s %-18s %-18s %-18s\n", "Project Name", "Flat Type", "Status", "Opening Date", "Closing Date");

                        Project applied = projects.stream()
                                .filter(p -> p.getProjectName().equalsIgnoreCase(applicant.getAppliedProject()))
                                .findFirst().orElse(null);

                        if (applied != null) {
                            System.out.printf("%-20s %-10s %-18s %-18s %-18s\n",
                                    applied.getProjectName(),
                                    applicant.getFlatType(),
                                    applicant.getApplicationStatus(),
                                    applied.getOpeningDate(),
                                    applied.getClosingDate());
                        }
                        else {
                            System.out.printf("%-20s %-10s %-18s %-18s %-18s\n",
                                    applicant.getAppliedProject(),
                                    applicant.getFlatType(),
                                    applicant.getApplicationStatus(),
                                    "-", "-");
                        }
                    }
                    break;

                case 2:
                    String status = applicant.getApplicationStatus();
                    if (!status.isEmpty() && !status.equalsIgnoreCase("Rejected")) {
                        System.out.println("\n❌ You have already applied, you can only apply to one project at a time.\n");
                        break;
                    }

                    if (!applicant.hasEligibleProjects(projects)) {
                        System.out.println("\n⚠️ You do not meet the requirements to apply for any projects at this time.\n");
                        break;
                    }

                    System.out.printf("%-20s %-10s %-13s %-13s %-18s %-18s\n",
                            "Project Name", "Location", "2-Room Left", "3-Room Left", "Opening Date", "Closing Date");
                    System.out.println("---------------------------------------------------------------------------------------------");

                    for (Project p : projects) {
                        if (!p.isVisible()) continue;

                        boolean show = false;
                        if (applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35) {
                            show = p.isAvailable("2-Room");
                        } else if (applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21) {
                            show = p.isAvailable("2-Room") || p.isAvailable("3-Room");
                        }

                        if (!show) continue;

                        System.out.printf("%-20s %-10s %-13d %-13d %-18s %-18s\n",
                                p.getProjectName(),
                                p.getLocation(),
                                p.getTwoRoomUnits(),
                                p.getThreeRoomUnits(),
                                p.getOpeningDate(),
                                p.getClosingDate());
                    }

                    System.out.print("Enter project name you want to apply: ");
                    String projName = sc.nextLine().trim();

                    Project selected = projects.stream()
                            .filter(p -> p.getProjectName().equalsIgnoreCase(projName) && p.isVisible())
                            .findFirst().orElse(null);

                    if (selected == null) {
                        System.out.println("\n❌ Unsuccessful application. You do not meet the requirements.\n");
                        break;
                    }

                    System.out.println("These are the available choices:");
                    boolean has2 = selected.isAvailable("2-Room");
                    boolean has3 = selected.isAvailable("3-Room");

                    if (!has2 && !has3) {
                        System.out.println("\nProject is full right now.\n");
                        break;
                    }

                    if (has2) {
                        System.out.println("2-Room: " + selected.getTwoRoomUnits() + " units (Open: " +
                                selected.getOpeningDate() + ", Close: " + selected.getClosingDate() + ")");
                    }
                    if (has3) {
                        System.out.println("3-Room: " + selected.getThreeRoomUnits() + " units (Open: " +
                                selected.getOpeningDate() + ", Close: " + selected.getClosingDate() + ")");
                    }

                    System.out.print("Enter flat type to apply (2-Room / 3-Room): ");
                    String flatType = sc.nextLine().trim();

                    boolean isSingleEligible = applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35;
                    boolean isMarriedEligible = applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21;

                    if (!flatType.equalsIgnoreCase("2-Room") && !flatType.equalsIgnoreCase("3-Room")) {
                        System.out.println("\nInvalid flat type entered.");
                        break;
                    }

                    if (flatType.equalsIgnoreCase("3-Room") && !isMarriedEligible) {
                        System.out.println("\nUnsuccessful application. You do not meet the requirements.\n");
                        break;
                    }
                    if (flatType.equalsIgnoreCase("2-Room") && !(isSingleEligible || isMarriedEligible)) {
                        System.out.println("\nUnsuccessful application. You do not meet the requirements.\n");
                        break;
                    }
                    if (!selected.isAvailable(flatType)) {
                        System.out.println("\nSelected flat type is not available.\n");
                        break;
                    }

                    selected.bookUnit(flatType);
                    applicant.applyForProject(projName, flatType);
                    applicant.setApplicationStatus("Pending"); // explicitly mark status
                    FileHandler.saveProjects(PROJECT_FILE, projects);
                    FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                    System.out.println("\nYou have successfully submitted your application, it is pending now.\n");
                    break;

                case 3:
                    if (applicant.getAppliedProject().isEmpty()) {
                        System.out.println("\nYou have no project applied to withdraw.\n");
                    } else {
                        System.out.print("\nAre you sure? Withdrawal submission is irreversible. (yes/no): ");
                        String confirm = sc.nextLine().trim().toLowerCase();

                        if (confirm.equals("yes")) {
                            applicant.setApplicationStatus("Withdrawal Pending");
                            FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                            System.out.println("\nYour withdrawal request has been submitted. Status: Withdrawal Pending\n");
                        } else {
                            System.out.println("\nWithdrawal cancelled.\n");
                        }
                    }
                    break;

                case 4:
                    System.out.print("\nEnter enquiry: ");
                    String enquiry = sc.nextLine();
                    applicant.submitEnquiry(enquiry);
                    FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                    break;

                case 5:
                    List<String> enquiries = applicant.getEnquiries();
                    if (enquiries.isEmpty()) {
                        System.out.println("\nNo enquiries submitted to delete.\n");
                        break;
                    }

                    System.out.println("\nYour enquiries:");
                    for (int i = 0; i < enquiries.size(); i++) {
                        System.out.println((i + 1) + ": " + enquiries.get(i));
                    }

                    System.out.print("\nEnter index to delete: ");
                    int delIdx = sc.nextInt(); sc.nextLine();

                    if (delIdx < 1 || delIdx > enquiries.size()) {
                        System.out.println("\nInvalid index. No enquiry deleted.");
                        break;
                    }

                    System.out.print("\nAre you sure you want to delete this enquiry? (yes/no): ");
                    String confirmDel = sc.nextLine().trim();
                    if (!confirmDel.equalsIgnoreCase("yes")) {
                        System.out.println("\nDeletion cancelled.");
                        break;
                    }

                    applicant.deleteEnquiry(delIdx - 1);
                    FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                    System.out.println("\nEnquiry deleted.\n");
                    break;

                case 6:
                    List<String> enqs = applicant.getEnquiries();
                    if (enqs.isEmpty()) {
                        System.out.println("\nNo enquiries submitted to edit.\n");
                        break;
                    }

                    System.out.println("\nYour enquiries:");
                    for (int i = 0; i < enqs.size(); i++) {
                        System.out.println((i + 1) + ": " + enqs.get(i));
                    }

                    System.out.print("\nEnter index to edit: ");
                    int editIdx = sc.nextInt(); sc.nextLine();

                    if (editIdx < 1 || editIdx > enqs.size()) {
                        System.out.println("\nInvalid index. No enquiry edited.\n");
                        break;
                    }

                    System.out.print("New message: ");
                    String newMsg = sc.nextLine();
                    applicant.editEnquiry(editIdx - 1, newMsg);
                    FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                    System.out.println("\nEnquiry updated.");
                    break;

                case 7:
                    System.out.print("\nEnter current password: ");
                    String currentPwd = sc.nextLine().trim();

                    if (!currentPwd.equals(applicant.getPassword())) {
                        System.out.println("\nIncorrect password. Try again.\n");
                        break;
                    }

                    System.out.print("\nEnter new password: ");
                    String newPwd = sc.nextLine().trim();

                    if (newPwd.isEmpty()) {
                        System.out.println("\nPassword cannot be empty.\n");
                        break;
                    }

                    applicant.setPassword(newPwd);
                    FileHandler.saveApplicants(APPLICANT_FILE, applicants);
                    System.out.println("\nPassword updated successfully.\n");
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("\nInvalid choice. Please enter a number from 1 to 7.\n");
                    break;
            }
        }
    }
}
