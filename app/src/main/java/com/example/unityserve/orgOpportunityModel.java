package com.example.unityserve;

import android.media.Image;

import java.util.Date;

public class orgOpportunityModel {

    String orgname;
    String opportunityTitle;
    String opportunityLocation;
    String eventDescription;
    String contactInformation;
    String category;

    String imageUrl;

    String  id;

    String duedatetxt;
    String datetext;

    String opportunityID;

    String dateTimeDisplay;




    public String getOpportunityID() {
        return opportunityID;
    }

    public void setOpportunityID(String opportunityID) {
        this.opportunityID = opportunityID;
    }


    public String getOrgname() {
        return orgname;
    }

    public String getOpportunityTitle() {
        return opportunityTitle;
    }

    public String getOpportunityLocation() {
        return opportunityLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getCategory() {
        return category;
    }




    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public void setOpportunityTitle(String opportunityTitle) {
        this.opportunityTitle = opportunityTitle;
    }

    public void setOpportunityLocation(String opportunityLocation) {
        this.opportunityLocation = opportunityLocation;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDuedatetxt() {
        return duedatetxt;
    }

    public void setDuedatetxt(String duedatetxt) {
        this.duedatetxt = duedatetxt;
    }

    public String getDatetext() {
        return datetext;
    }

    public void setDatetext(String datetext) {
        this.datetext = datetext;
    }

    public String getDateTimeDisplay() {
        return dateTimeDisplay;
    }

    public void setDateTimeDisplay(String dateTimeDisplay) {
        this.dateTimeDisplay = dateTimeDisplay;
    }
}
