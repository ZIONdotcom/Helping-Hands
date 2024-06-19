package com.example.unityserve;

public class recommendedModel {
    String OpportunityTitle;
    String OrgName;

    int OrgImage;

    int OpportunityImage;

    String Category;

    recommendedModel(){

    }


    public recommendedModel(String opportunityTitle, String orgName, int orgImage, int opportunityImage, String category) {
        OpportunityTitle = opportunityTitle;
        OrgName = orgName;
        OrgImage = orgImage;
        OpportunityImage = opportunityImage;
        Category = category;
    }

    public String getOpportunityTitle() {
        return OpportunityTitle;
    }

    public String getOrgName() {
        return OrgName;
    }

    public int getOrgImage() {
        return OrgImage;
    }

    public int getOpportunityImage() {
        return OpportunityImage;
    }

    public String getCategory() {
        return Category;
    }
}
