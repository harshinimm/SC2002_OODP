import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Enquiry {
    HDBOfficer officer;
    public static List<Enquiry> allEnquiries = new ArrayList<>();
    public static final String enquiryCSV = "C:/Users/gmaha/IdeaProjects/BTO_Officer/src/Enquiry.csv";

    public final String officerName;
    public final String projectAssigned;
    public final String question;
    public String reply;
    public boolean status;

    public Enquiry(String officerName, String projectAssigned, String question) {
        this.officerName = officerName;
        this.projectAssigned = projectAssigned;
        this.question = question;
        this.status = false;
        allEnquiries.add(this);
    }

    public void reply(String reply, String replyingOfficer, String replyingProject) {
        this.reply = reply;
        this.status = true;
        System.out.println(replyingOfficer + " replied: " + reply);
    }

    public static void viewAllEnquiries() {
        System.out.println("All Enquiries:");
        for (Enquiry e : allEnquiries) {
            System.out.println(e);
        }
    }

    public static void viewProjectEnquiries(String projectName) {
        System.out.println("Enquiries for Project: " + projectName);
        for (Enquiry e : allEnquiries) {
            if (e.projectAssigned.equals(projectName)) {
                System.out.println(e);
            }
        }
    }

    public String getOfficerName() {
        return officerName;
    }

    public String getProjectAssigned() {
        return projectAssigned;
    }

    public String getQuestion() {
        return question;
    }

    public String getReply() {
        return reply;
    }

    public boolean isReplied() {
        return status;
    }

    // ✅ Static method that takes an Enquiry object and appends it to CSV
    public static void addEnquiryToCSV(String filePath, Enquiry enquiry) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(enquiry.officerName).append(",");
            writer.append(enquiry.projectAssigned).append(",");
            writer.append(enquiry.question).append(",");
            writer.append(enquiry.reply == null ? "" : enquiry.reply).append(",");
            writer.append(String.valueOf(enquiry.status)).append("\n");
        } catch (IOException e) {
            System.out.println("Error adding enquiry to CSV: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Officer: " + officerName + ", Project: " + projectAssigned +
                ", Question: " + question + ", Reply: " + (reply == null ? "Pending" : reply) +
                ", Status: " + (status ? "Replied" : "Pending");
    }
}
