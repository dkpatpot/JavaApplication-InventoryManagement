package ku.cs.model.promotion;

import java.time.LocalDate;

public class Promotion {
    private int PM_ID;
    private String PM_Name;
    private String PM_Description;
    private int P_ID;
    private LocalDate StartDate;
    private LocalDate EndDate;

    public Promotion(int PM_ID, String PM_Name, String PM_Description, int p_ID, LocalDate startDate, LocalDate endDate) {
        this.PM_ID = PM_ID;
        this.PM_Name = PM_Name;
        this.PM_Description = PM_Description;
        P_ID = p_ID;
        StartDate = startDate;
        EndDate = endDate;
    }

    public int getPM_ID() {
        return PM_ID;
    }

    public void setPM_ID(int PM_ID) {
        this.PM_ID = PM_ID;
    }

    public String getPM_Name() {
        return PM_Name;
    }

    public void setPM_Name(String PM_Name) {
        this.PM_Name = PM_Name;
    }

    public String getPM_Description() {
        return PM_Description;
    }

    public void setPM_Description(String PM_Description) {
        this.PM_Description = PM_Description;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }
}
