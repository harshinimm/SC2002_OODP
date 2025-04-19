import java.util.ArrayList;
import java.util.List;

public class Projectcheck {
    public static String projectName;
    public String name;
    public static List<Application> applications;

    public Projectcheck(String projectName) {
        this.projectName = projectName;
        this.applications = new ArrayList<>();
        this.name=name;
    }

    public static boolean apply(HDBOfficer officer) {
        for (Application app : applications) {
            if (app.getOfficer().getName().equals(officer.getName())) {
                System.out.println("Officer " + officer.getName() + " has already applied to this project.");
                return false;
            }
        }

        applications.add(new Application(officer, "pending"));
        System.out.println("Officer " + officer.getName()+ " has successfully applied to project: " + projectName);
        return true;
    }
    public String checkStatus(HDBOfficer officer) {
        for (Application app : applications) {
            if (app.getOfficer().getName().equals(officer.getName())) {
                return "Officer " + officer.getName() + " has a(n) " + app.getStatus() + " application.";
            }
        }
        return "Officer " + officer.getName() + " has not applied for this project yet.";
    }

}
