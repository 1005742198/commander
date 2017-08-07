package com.huajin.commander.demo.domain;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TimelineDetailVo {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 动态的实体类型，备案=1,挂牌=2,用户=3,场外回传申请=4
     */
    private Integer entityType;
    /**
     * 实体Id
     */
    private Integer entityId;
    /**
     * 实体所属会员id
     */
    private Integer memberId;
    /**
     * 事件/操作id
     */
    private Integer eventId;
    /**
     * 事件名，界面上的操作动态
     */
    private String eventName;
    /**
     * 事件/操作发生时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    /**
     * 操作人id
     */
    private Integer eventOperatorId;
    /**
     * 事件操作人名称
     */
    private String eventOperatorName;
    /**
     * 操作意见/审核意见
     */
    private String eventRemark;
    /**
     * 实体状态变更描述，由枚举模板属性生成
     */
    private String statusChangeDesc;
    /**
     * 记录创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getEventOperatorId() {
        return eventOperatorId;
    }

    public void setEventOperatorId(Integer eventOperatorId) {
        this.eventOperatorId = eventOperatorId;
    }

    public String getEventOperatorName() {
        return eventOperatorName;
    }

    public void setEventOperatorName(String eventOperatorName) {
        this.eventOperatorName = eventOperatorName;
    }

    public String getEventRemark() {
        return eventRemark;
    }

    public void setEventRemark(String eventRemark) {
        this.eventRemark = eventRemark;
    }

    public String getStatusChangeDesc() {
        return statusChangeDesc;
    }

    public void setStatusChangeDesc(String statusChangeDesc) {
        this.statusChangeDesc = statusChangeDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TimelineDetailVo{" +
                "id=" + id +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", memberId=" + memberId +
                ", eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                ", eventOperatorId=" + eventOperatorId +
                ", eventOperatorName='" + eventOperatorName + '\'' +
                ", eventRemark='" + eventRemark + '\'' +
                ", statusChangeDesc='" + statusChangeDesc + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public static void main(String[] args) {
        //String strDate = "2017-06-20 10:45:12";
        System.out.println(new Date().getTime());
    }
}
