import java.util.*;

public class Applicant extends User {
    public String applicationStatus;
    public String appliedProject;
    public String flatType;
    public List<String> enquiries;

    public Applicant(String name, String nric, String password, int age, String maritalStatus,
                     String applicationStatus, String appliedProject, String flatType, List<String> enquiries) {
        super(name, nric, password, age, maritalStatus);
        this.applicationStatus = applicationStatus;
        this.appliedProject = appliedProject;
        this.flatType = flatType;
        this.enquiries = new ArrayList<>(enquiries);
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    public String getAppliedProject() {
        return appliedProject;
    }

    public List<String> getEnquiries() {
        return enquiries;
    }

    public String getFlatType() {
        return flatType;
    }

    public boolean hasEligibleProjects(List<Project> projectList) {
        for (Project p : projectList) {
            if (!p.isVisible()) continue;
            if (maritalStatus.equalsIgnoreCase("Single") && age >= 35 && p.isAvailable("2-Room")) return true;
            if (maritalStatus.equalsIgnoreCase("Married") && age >= 21 &&
                    (p.isAvailable("2-Room") || p.isAvailable("3-Room"))) return true;
        }
        return false;
    }

    public List<String[]> getEligibleProjects(List<Project> projectList) {
        List<String[]> eligible = new ArrayList<>();

        for (Project p : projectList) {
            if (!p.isVisible()) continue;

            if (maritalStatus.equalsIgnoreCase("Single") && age >= 35) {
                if (p.isAvailable("2-Room")) {
                    eligible.add(new String[]{p.getProjectName(), p.getLocation(), "2-Room", p.getOpeningDate().toString(), p.getClosingDate().toString()});
                }
            } else if (maritalStatus.equalsIgnoreCase("Married") && age >= 21) {
                if (p.isAvailable("2-Room")) {
                    eligible.add(new String[]{p.getProjectName(), p.getLocation(), "2-Room", p.getOpeningDate().toString(), p.getClosingDate().toString()});
                }
                if (p.isAvailable("3-Room")) {
                    eligible.add(new String[]{p.getProjectName(), p.getLocation(), "3-Room", p.getOpeningDate().toString(), p.getClosingDate().toString()});
                }
            }
        }
        return eligible;
    }

    public void applyForProject(String projectName, String flatType) {
        if (appliedProject != null && !appliedProject.isEmpty()) {
            System.out.println("Already applied for a project.");
            return;
        }
        this.appliedProject = projectName;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
    }

    public void withdrawApplication() {
        if (appliedProject == null || appliedProject.isEmpty()) {
            System.out.println("No application to withdraw.");
            return;
        }
        appliedProject = "";
        flatType = "";
        applicationStatus = "";
    }

    public void submitEnquiry(String msg) {
        enquiries.add(msg);
    }

    public void editEnquiry(int index, String newMsg) {
        if (index < 0 || index >= enquiries.size()) return;
        enquiries.set(index, newMsg);
    }

    public void deleteEnquiry(int index) {
        if (index < 0 || index >= enquiries.size()) return;
        enquiries.remove(index);
    }

    public String toCSV() {
        return String.join(",", Arrays.asList(
                name, nric, password, String.valueOf(age), maritalStatus,
                applicationStatus, appliedProject, flatType,
                String.join(";", enquiries)
        ));
    }

    public static Applicant fromCSV(String[] data) {
        List<String> enquiries = new ArrayList<>();
        if (data.length >= 9 && !data[8].isEmpty()) {
            enquiries = Arrays.asList(data[8].split(";"));
        }
        return new Applicant(
                data[0],
                data[1],
                data[2],
                Integer.parseInt(data[3]),
                data[4],
                data.length > 5 ? data[5] : "",
                data.length > 6 ? data[6] : "",
                data.length > 7 ? data[7] : "",
                enquiries
        );
    }
