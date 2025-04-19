import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandler {
    public static List<Project> loadProjects(String path) throws IOException {
        List<Project> projects = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 1; i < lines.size(); i++) {  // start from 1 to skip header
            String[] data = lines.get(i).split(",");
            projects.add(Project.fromCSV(data));
        }
        return projects;
    }

    public static List<Applicant> loadApplicants(String path) throws IOException {
        List<Applicant> applicants = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 1; i < lines.size(); i++) {  // Skip header
            String[] data = lines.get(i).split(",");
            applicants.add(Applicant.fromCSV(data));
        }
        return applicants;
    }

    public static void saveProjects(String path, List<Project> projects) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Project p : projects) {
            lines.add(p.toCSV());
        }
        Files.write(Paths.get(path), lines);
    }

    public static void saveApplicants(String path, List<Applicant> applicants) throws IOException {
        List<String> lines = new ArrayList<>();

        // add header row first
        lines.add("Name,NRIC,Password,Age,MaritalStatus,ApplicationStatus,AppliedProject,FlatType,Enquiries");

        for (Applicant a : applicants) {
            lines.add(a.toCSV());
        }

        Files.write(Paths.get(path), lines);
    }

}