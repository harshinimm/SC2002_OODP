import java.io.*;
import java.util.*;

public class HDBManager extends User {
    private String createdProjects;
    private static String registeredProject;
    private boolean isApproved;

    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
        this.registeredProject = "";
        this.isApproved = false;
    }

    public String getNric() {
        return super.getNric();
    }

    public String getName() { return name;}

    public String getCreatedProjects() {
        return createdProjects;
    }

    public static String getRegisteredProject() {
        return registeredProject;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    // Non-static methods
    protected void manageBTOProjects() {
        System.out.println("Managing BTO Projects...");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose your further action");
            System.out.println("1. Create a BTO Project");
            System.out.println("2. Edit a BTO Project");
            System.out.println("3. Delete a BTO project");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Creating a new BTO Project... ");
                    String filePath = "src/data/ProjectList.csv";

                    System.out.print("Enter Project Name: ");
                    String projectName = scanner.nextLine();

                    System.out.print("Enter Neighborhood: ");
                    String neighborhood = scanner.nextLine();

                    System.out.print("Enter Type 1: ");
                    String type1 = scanner.nextLine();

                    System.out.print("Enter Number of units for Type 1: ");
                    String unitsType1 = scanner.nextLine();

                    System.out.print("Enter Selling price for Type 1: ");
                    String priceType1 = scanner.nextLine();

                    System.out.print("Enter Type 2: ");
                    String type2 = scanner.nextLine();

                    System.out.print("Enter Number of units for Type 2: ");
                    String unitsType2 = scanner.nextLine();

                    System.out.print("Enter Selling price for Type 2: ");
                    String priceType2 = scanner.nextLine();

                    System.out.print("Enter Application opening date: ");
                    String openingDate = scanner.nextLine();

                    System.out.print("Enter Application closing date: ");
                    String closingDate = scanner.nextLine();

                    System.out.print("Enter Manager: ");
                    String manager = scanner.nextLine();

                    System.out.print("Enter Officer Slot: ");
                    String officerSlot = scanner.nextLine();

                    System.out.print("Enter Officer: ");
                    String officer = scanner.nextLine();

                    String newProjectData = String.join(",", projectName, neighborhood, type1, unitsType1, priceType1, type2, unitsType2, priceType2, openingDate, closingDate, manager, officerSlot, officer);

                    try (FileWriter fw = new FileWriter(filePath, true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        out.println(newProjectData);
                        System.out.println("New project data added successfully.");
                    } catch (IOException e) {
                        System.out.println("Error appending project data: " + e.getMessage());
                    }
                    viewAllCreatedProjects();
                    break;
                case 2:
                    System.out.print("Enter Applicant NRIC: ");
                    String nric = scanner.nextLine();
                    System.out.print("Enter Project Name: ");
                    String projectName = scanner.nextLine();
                    System.out.print("Enter Flat Type: ");
                    String flatType = scanner.nextLine();
                    System.out.print("Enter Booking Status (true/false): ");
                    Boolean bookingStatus = scanner.nextLine().equalsIgnoreCase("true");
                    Report.editReport(nric, projectName, flatType, bookingStatus);
                    break;
                case 3:
                    System.out.print("Enter Applicant NRIC to delete: ");
                    String nricToDelete = scanner.nextLine();
                    Report.deleteReport(nricToDelete);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    protected static void viewAllCreatedProjects() {
        System.out.println("All Projects:");
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/ProjectList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error generating projects: " + e.getMessage());
        }
    }

    protected void viewManagerProjects() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/ProjectList.csv"))) {
            String line;
            System.out.print("Enter Manager name: ");
            Scanner scanner = new Scanner(System.in);
            String Name = scanner.nextLine();

            if (br.readLine() != null) {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

                    // Check if the array has at least 12 elements
                    if (values.length > 10 && Objects.equals(values[10], Name)) {
                        for (String value : values) {
                            System.out.print(value + " ");
                        }
                        System.out.println();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the project list: " + e.getMessage());
        }

    }

    protected static void viewOfficerRegistration() {
        System.out.println("All Projects:");
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/OfficerList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error generating projects: " + e.getMessage());
        }
    }
    protected void approveOfficerRegistration() {
        System.out.println("You can choose to approve or reject Officer Registration");
        System.out.println("Here is a list of pending Approvals.");
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/OfficerList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (Objects.equals(values[7], " Pending")){
                    for (String value : values) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose your further action");
            System.out.println("1. Approve a HDBOfficer");
            System.out.println("2. Reject a HDBOfficer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Officer NRIC: ");
                    String nric = scanner.nextLine();
                    HDBOfficer.approveProject(nric);
                    break;
                case 2:
                    System.out.print("Enter Officer NRIC: ");
                    String Nric = scanner.nextLine();
                    HDBOfficer.rejectProject(Nric);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    protected void approveApplicantRegistration() {
        System.out.println("You can choose to approve or reject Applicant Registration");
        System.out.println("Here is a list of pending Approvals.");
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/ApplicantList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (!Objects.equals(values[7], "Approved")){
                    for (String value : values) {
                        System.out.print(value + " ");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println(" Choose your further action");
            System.out.println("1. Approve a Applicant");
            System.out.println("2. Reject a Applicant");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Applicant NRIC: ");
                    String nric = scanner.nextLine();
                    approveProject(nric);
                    break;
                case 2:
                    System.out.print("Enter Applicant NRIC: ");
                    String Nric = scanner.nextLine();
                    rejectProject(Nric);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void rejectProject(String nric) {
    }

    private void approveProject(String nric) {
    }

    public Applicant getApplicantByNric(String nric) {
        for (Applicant applicant : applicants) {
            if (applicant.getNric().equals(nric)) {
                return applicant;
            }
        }
        return null;
    }


    public void approveApplicantWithdrawal(Applicant applicant) {
        if (applicant.getAppliedProject() == null || applicant.getAppliedProject().isEmpty()) {
            System.out.println("No application to withdraw.");
            return;
        }
        applicant.setAppliedProject("");
        applicant.setFlatType("");
        applicant.setApplicationStatus("");
        System.out.println("Withdrawal approved. Application details cleared.");
    }


//    public void approveApplicantWithdrawal(Applicant applicant) {
//        if (applicant.appliedProject == null || appliedProject.isEmpty()) {
//            System.out.println("No application to withdraw.");
//            return;
//        }
//        appliedProject = "";
//        flatType = "";
//        applicationStatus = "";
//    }

}