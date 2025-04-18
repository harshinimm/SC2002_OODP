import java.io.*;
import java.util.*;

public class HDBManager extends User {
    private List<Applicant> applicants;
    private String createdProjects;
    private String registeredProject;
    private boolean isApproved;
    private String name;

    // Constructor for HDBManager
    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, maritalStatus, age, password);
        this.registeredProject = "";
        this.isApproved = false;
        this.name = name;
        this.applicants = new ArrayList<>();
    }


    public static ArrayList<HDBManager> loadManagers(String filePath) {
        ArrayList<HDBManager> managers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    managers.add(new HDBManager(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading managers: " + e.getMessage());
        }
        return managers;
    }


    public static void updateManagerCSV(String managerFile, HDBManager updatedManager) {
        ArrayList<HDBManager> managers = loadManagers(managerFile);

        for (HDBManager manager : managers) {
            if (manager.getNric().equals(updatedManager.getNric())) {
                manager.registeredProject = updatedManager.registeredProject;
                manager.isApproved = updatedManager.isApproved;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(managerFile))) {
            for (HDBManager manager : managers) {
                bw.write(manager.getName() + "," + manager.getNric() + "," + manager.getPassword() + "," +
                        manager.getAge() + "," + manager.getMaritalStatus() + "," +
                        manager.registeredProject + "," + (manager.isApproved ? "true" : "false") + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating manager CSV: " + e.getMessage());
        }
    }

    public String getNric() {
        return super.getNric();
    }

    public String getName() { return name;}

    public String getCreatedProjects() {
        return createdProjects;
    }

    public String getRegisteredProject() {
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
            System.out.println(" Choose your further action");
            System.out.println("1. Create a BTO Project");
            System.out.println("2. Edit a BTO Project");
            System.out.println("3. Delete a BTO project");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Report.generateReport("src/Report.csv");
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

    protected void viewAllCreatedProjects() {
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
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (Objects.equals(values[11], this.getName())){
                    for (String value : values) {
                        System.out.print(value + " ");
                    }
                }

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

    protected void viewOfficerRegistration() {
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

                if (!Objects.equals(values[7], "Approved"){
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
                    HDBOfficer.approveProject(Scanner scanner);
                    break;
                case 2:
                    System.out.print("Enter Officer NRIC: ");
                    String nric = scanner.nextLine();
                    HDBOfficer.rejectProject(Scanner scanner);
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

                if (!Objects.equals(values[7], "Approved"){
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
                    Applicant.approveProject(Scanner scanner);
                    break;
                case 2:
                    System.out.print("Enter Applicant NRIC: ");
                    String nric = scanner.nextLine();
                    Applicant.rejectProject(Scanner scanner);
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