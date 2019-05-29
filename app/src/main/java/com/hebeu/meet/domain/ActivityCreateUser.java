package com.hebeu.meet.domain;

public class ActivityCreateUser {
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

    private String userName;

    private String head;

    private Integer sex;

    private Integer integral;

    private String college;

    private String className;

    private String qq;

    private String phone;

    private String email;

    /*Vanilla5-25*/
    private String join_id;

    private String joinState;

    private String words;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    @Override
    public String toString() {
        return "ActivityCreateUser{" +
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
                ", userName='" + userName + '\'' +
                ", head='" + head + '\'' +
                ", sex=" + sex +
                ", integral=" + integral +
                ", college='" + college + '\'' +
                ", className='" + className + '\'' +
                ", qq='" + qq + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public void setJoin_id(String join_id) {
        this.join_id = join_id;
    }

    public String getJoin_id() {
        return join_id;
    }

    public String getJoinState() {
        return joinState;
    }

    public void setJoinState(String joinState) {
        this.joinState = joinState;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}