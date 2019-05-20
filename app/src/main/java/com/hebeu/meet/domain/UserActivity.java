package com.hebeu.meet.domain;

public class UserActivity {
    private Integer id;

    private Integer activityId;

    private String userId;

    private String words;

    private String joinState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words == null ? null : words.trim();
    }

    public String getJoinState() {
        return joinState;
    }

    public void setJoinState(String joinState) {
        this.joinState = joinState == null ? null : joinState.trim();
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", userId='" + userId + '\'' +
                ", words='" + words + '\'' +
                ", joinState='" + joinState + '\'' +
                '}';
    }
}