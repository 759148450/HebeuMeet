package com.hebeu.meet.domain;

public class Activity {
    private Integer activityId;

    private String userId;

    private String title;

    private Integer typeId;

    private Integer sexLimit;

    private Integer peopleLimit;

    private String activityDate;

    private String activityContent;

    private String createDate;

    private String applyState;

    private String activityPlace;

    private String img;

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

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate == null ? null : activityDate.trim();
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent == null ? null : activityContent.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
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
                ", activityDate='" + activityDate + '\'' +
                ", activityContent='" + activityContent + '\'' +
                ", createDate='" + createDate + '\'' +
                ", applyState='" + applyState + '\'' +
                ", activityPlace='" + activityPlace + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}