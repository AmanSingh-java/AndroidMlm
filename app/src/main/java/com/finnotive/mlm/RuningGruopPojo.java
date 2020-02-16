package com.finnotive.mlm;

public class RuningGruopPojo {
    private String gruopname;
    private String EffectiveStartDate;
    private String EffectiveEndDateEffectiveEndDate;
    private String g_status;

    public RuningGruopPojo() {
    }

    public RuningGruopPojo(String gruopname, String effectiveStartDate, String effectiveEndDateEffectiveEndDate, String g_status) {
        this.gruopname = gruopname;
        EffectiveStartDate = effectiveStartDate;
        EffectiveEndDateEffectiveEndDate = effectiveEndDateEffectiveEndDate;
        this.g_status = g_status;
    }

    public String getGruopname() {
        return gruopname;
    }

    public void setGruopname(String gruopname) {
        this.gruopname = gruopname;
    }

    public String getEffectiveStartDate() {
        return EffectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        EffectiveStartDate = effectiveStartDate;
    }

    public String getEffectiveEndDateEffectiveEndDate() {
        return EffectiveEndDateEffectiveEndDate;
    }

    public void setEffectiveEndDateEffectiveEndDate(String effectiveEndDateEffectiveEndDate) {
        EffectiveEndDateEffectiveEndDate = effectiveEndDateEffectiveEndDate;
    }

    public String getG_status() {
        return g_status;
    }

    public void setG_status(String g_status) {
        this.g_status = g_status;
    }

    @Override
    public String toString() {
        return "RuningGruopPojo{" +
                "gruopname='" + gruopname + '\'' +
                ", EffectiveStartDate='" + EffectiveStartDate + '\'' +
                ", EffectiveEndDateEffectiveEndDate='" + EffectiveEndDateEffectiveEndDate + '\'' +
                ", g_status='" + g_status + '\'' +
                '}';
    }
}
