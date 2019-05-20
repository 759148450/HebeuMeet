package com.hebeu.meet.domain;

import java.util.Arrays;
import java.util.Date;

public class Activity {
    private Integer activityId;

    private String userId;

    private String title;

    private Integer typeId;

    private Integer sexLimit;

    private Integer peopleLimit;

    private Date activityDate;

    private String activityContent;

    private Date createDate;

    private String applyState;

    private String activityPlace;

    private byte[] img;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSexLimit() {
        return sexLimit;
    }

    public void setSexLimit(Integer sexLimit) {
        this.sexLimit = sexLimit;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent == null ? null : activityContent.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState == null ? null : applyState.trim();
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace == null ? null : activityPlace.trim();
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", typeId=" + typeId +
                ", sexLimit=" + sexLimit +
                ", peopleLimit=" + peopleLimit +
                ", activityDate=" + activityDate +
                ", activityContent='" + activityContent + '\'' +
                ", createDate=" + createDate +
                ", applyState='" + applyState + '\'' +
                ", activityPlace='" + activityPlace + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }
}