package com.finnotive.mlm;

public class SharePojo {
    private String groupid;
    private String shareid;
    private String startdate;
    private String enddate;

    public SharePojo() {
    }

    public SharePojo(String groupid, String shareid, String startdate, String enddate) {
        this.groupid = groupid;
        this.shareid = shareid;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    @Override
    public String toString() {
        return "SharePojo{" +
                "groupid='" + groupid + '\'' +
                ", shareid='" + shareid + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                '}';
    }
}
