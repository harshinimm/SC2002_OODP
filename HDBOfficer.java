import java.io.*;
import java.util.*;

class HDBOfficer extends Applicant {
    public Enquiry enquiry;
    public Project project;
    public String registeredProject;
    public boolean isApproved;

    public final String officerCSV = "C:/Users/gmaha/IdeaProjects/BTO_Officer/src/OfficerList.csv";
    public final String applicantCSV = "C:/Users/gmaha/IdeaProjects/BTO_Officer/src/ApplicantList.csv";
    public final String projectCSV = "C:/Users/gmaha/IdeaProjects/BTO_Officer/src/ProjectList.csv";
    public final String enquiryCSV = "C:/Users/gmaha/IdeaProjects/BTO_Officer/src/Enquiry.csv";

    public HDBOfficer(String name, String nric, String password, int age, String maritalStatus,
                      String applicationStatus, String appliedProject, String flatType, List<String> enquiries) {
        super(name, nric, password, age, maritalStatus, applicationStatus, appliedProject, flatType, enquiries);
        this.registeredProject = "";
        this.isApproved = false;
    }

    public boolean login(String nric, String password) {
        try (Scanner scanner = new Scanner(new File(officerCSV))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip header
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields[1].equals(nric) && fields[4].equals(password)) {
                    System.out.println("Login successful. Welcome Officer " + fields[0] + "!");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error during login.");
        }
        return false;
    }

    public boolean isOfficer() {
        return login(this.getNric(), this.getPassword());
    }

    public boolean hasAppliedAsApplicant() {
        try (Scanner scanner = new Scanner(new File(applicantCSV))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields.length >= 2 && fields[1].equals(this.getNric())) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Applicant file not found.");
        }
        return false;
    }

    public boolean isHandlingAnotherProject(String projectName) {
        try (Scanner scanner = new Scanner(new File(projectCSV))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields[1].equals(this.getNric()) && !fields[0].equals(projectName)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Project file not found.");
        }
        return false;
    }

    public boolean canRegisterForProject(String projectName) {
        return !hasAppliedAsApplicant() && !isHandlingAnotherProject(projectName);
    }

    public boolean applyProjects(String projectName) {
        if (!canRegisterForProject(projectName)) {
            System.out.println("You cannot apply for this project.");
            return false;
        }

        Project project = new Project(projectName);
        boolean applied = project.apply(this);

        System.out.println(applied ? "Application submitted successfully." : "You have already applied.");
        return applied;
    }

    public boolean viewStatus(String nric, String appliedProject) {
        try (Scanner scanner = new Scanner(new File(projectCSV))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields.length >= 14 && fields[1].equals(this.getAppliedProject()) && !fields[13].equals(nric)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Project file not found.");
        }
        return false;
    }

    public boolean pendingApproval() {
        // Placeholder for future logic
        return false;
    }

    public void viewDetails(String projectName) {
        try (Scanner scanner = new Scanner(new File(projectCSV))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields[0].equals(projectName)) {
                    System.out.println("Project Name   : " + fields[0]);
                    System.out.println("Neighbourhood  : " + fields[1]);
                    System.out.println("Room Types     : " + fields[2] + " and " + fields[5]);
                    System.out.println("Selling Prices : " + fields[4] + " and " + fields[7]);
                    System.out.println("Manager        : " + fields[11]);
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Project file not found.");
        }
    }

    public void editProjectDetails(String projectName) {
        throw new UnsupportedOperationException("You cannot edit project");
    }

    public void enquiry(String projectName) {
        Enquiry.viewProjectEnquiries(projectName);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of the enquiry you want to reply to:");
        int enquiryIndex = scanner.nextInt() - 1;

        if (enquiryIndex >= 0 && enquiryIndex < Enquiry.allEnquiries.size()) {
            Enquiry selectedEnquiry = Enquiry.allEnquiries.get(enquiryIndex);
            System.out.println("Enquiry Selected: ");
            System.out.println("Officer: " + selectedEnquiry.getOfficerName());
            System.out.println("Question: " + selectedEnquiry.getQuestion());
            scanner.nextLine(); // consume newline
            System.out.println("Enter your reply:");
            String reply = scanner.nextLine();
            selectedEnquiry.reply(reply, this.getName(), projectName);

            System.out.println("Reply submitted successfully!");
        } else {
            System.out.println("Invalid enquiry number. Please try again.");
        }

        enquiry.addEnquiryToCSV(enquiryCSV);
    }

    public void applyForProject(String projectName, String flatType) {
        this.applicationStatus = "Booked";
        List<String> updatedLines = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(projectCSV))) {
            if (sc.hasNextLine()) updatedLines.add(sc.nextLine()); // Add header

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] fields = line.split(",");

                if (fields[0].equals(projectName)) {
                    if (flatType.equals("3 Room")) {
                        int count = Integer.parseInt(fields[3].trim());
                        if (count > 0) {
                            fields[3] = Integer.toString(count - 1);
                        } else {
                            System.out.println("No 3 Room flats available.");
                            return;
                        }
                    } else if (flatType.equals("2 Room")) {
                        int count = Integer.parseInt(fields[6].trim());
                        if (count > 0) {
                            fields[6] = Integer.toString(count - 1);
                        } else {
                            System.out.println("No 2 Room flats available.");
                            return;
                        }
                    }

                    // Reconstruct the updated line
                    line = String.join(",", fields);
                }

                updatedLines.add(line);
            }

            // Write updated lines back
            try (PrintWriter writer = new PrintWriter(new FileWriter(projectCSV))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Successfully updated flat count in project file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error processing project file: " + e.getMessage());
        }
    }

    public void generateReceipt(String applicantNric) {
        String applicantName = "", maritalStatus = "", flatType = "", appliedProject = "", neighborhood = "";
        String flatType1 = "", flatType2 = "", price1 = "", price2 = "";
        int age = 0;

        try (Scanner appScan = new Scanner(new File(applicantCSV))) {
            if (appScan.hasNextLine()) appScan.nextLine();
            while (appScan.hasNextLine()) {
                String[] a = appScan.nextLine().split(",");
                if (a[1].equals(applicantNric)) {
                    applicantName = a[0];
                    age = Integer.parseInt(a[3]);
                    maritalStatus = a[4];
                    flatType = a[7];
                    appliedProject = a[6];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Applicant file not found.");
            return;
        }

        try (Scanner projScan = new Scanner(new File(projectCSV))) {
            if (projScan.hasNextLine()) projScan.nextLine();
            while (projScan.hasNextLine()) {
                String[] p = projScan.nextLine().split(",");
                if (p[0].equals(appliedProject)) {
                    neighborhood = p[1];
                    flatType1 = p[2];
                    price1 = p[4];
                    flatType2 = p[5];
                    price2 = p[7];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Project file not found.");
            return;
        }

        System.out.printf("""
            === Applicant Booking Receipt ===
            Applicant Name   : %s
            NRIC             : %s
            Age              : %d
            Marital Status   : %s
            Flat Type Booked : %s
            --- Project Details ---
            Project Name     : %s
            Neighborhood     : %s
            Flat Types       : %s ($%s), %s ($%s)
            ===============================
            """,
                applicantName, applicantNric, age, maritalStatus, flatType,
                appliedProject, neighborhood, flatType1, price1, flatType2, price2
        );
    }
}
