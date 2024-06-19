package com.example.unityserve;

public class opportunityModel {
    public String opportunityTitle;
    public String OpportunityDateTime;

    public String OpportunityLocation;
    public String OpportunityDescription;
    public String contact;

    public String deadline;
    public String orgName;

    public int orgimage;

    int  postPhoto;

    public String category;

    public opportunityModel(String opportunityTitle, String opportunityDateTime, String opportunityLocation,
                            String opportunityDescription, String contact, String deadline, String orgName,int postPhoto, int orgimage, String category) {
        this.opportunityTitle = opportunityTitle;
        OpportunityDateTime = opportunityDateTime;
        OpportunityLocation = opportunityLocation;
        OpportunityDescription = opportunityDescription;
        this.contact = contact;
        this.deadline = deadline;
        this.postPhoto = postPhoto;
        this.orgName = orgName;
        this.orgimage = orgimage;
        this.category = category;
    }


    public String getOpportunityTitle() {
        return opportunityTitle;
    }

    public String getOpportunityDateTime() {
        return OpportunityDateTime;
    }

    public String getOpportunityLocation() {
        return OpportunityLocation;
    }

    public String getOpportunityDescription() {
        return OpportunityDescription;
    }

    public String getContact() {
        return contact;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getPostPhoto() {
        return postPhoto;
    }

    public String getOrgName() {
        return orgName;
    }

    public int getOrgimage() {
        return orgimage;
    }

    public String getCategory() {
        return category;
    }
}
