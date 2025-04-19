public class Application {
    private HDBOfficer officer;
    private String status;

    public Application(HDBOfficer officer, String status) {
        this.officer = officer;
        this.status = status;
    }

    public HDBOfficer getOfficer() {
        return officer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
