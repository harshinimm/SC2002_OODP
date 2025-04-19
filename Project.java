import java.time.LocalDate;

public class Project {
    private String projectName;
    private String location;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private boolean visible;
    private LocalDate openingDate;
    private LocalDate closingDate;

    public Project(String name, String location, int twoRoom, int threeRoom, boolean visible,
                   LocalDate openingDate, LocalDate closingDate) {
        this.projectName = name;
        this.location = location;
        this.twoRoomUnits = twoRoom;
        this.threeRoomUnits = threeRoom;
        this.visible = visible;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getLocation() {
        return location;
    }

    public boolean isVisible() {
        return visible;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public int getTwoRoomUnits() {
        return twoRoomUnits;
    }

    public int getThreeRoomUnits() {
        return threeRoomUnits;
    }


    public boolean isAvailable(String flatType) {
        if (flatType.equals("2-Room")) return twoRoomUnits > 0;
        if (flatType.equals("3-Room")) return threeRoomUnits > 0;
        return false;
    }

    public void bookUnit(String flatType) {
        if (flatType.equals("2-Room")) twoRoomUnits--;
        else if (flatType.equals("3-Room")) threeRoomUnits--;
    }

    public String toCSV() {
        return String.join(",", projectName, location, String.valueOf(twoRoomUnits),
                String.valueOf(threeRoomUnits), String.valueOf(visible),
                openingDate.toString(), closingDate.toString());
    }

    public static Project fromCSV(String[] data) {
        return new Project(
                data[0],
                data[1],
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Boolean.parseBoolean(data[4]),
                LocalDate.parse(data[5]),
                LocalDate.parse(data[6])
        );
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - 2R: %d, 3R: %d, Open: %s, Close: %s, Visible: %s",
                projectName, location, twoRoomUnits, threeRoomUnits,
                openingDate, closingDate, visible);
    }
}
