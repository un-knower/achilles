package com.quancheng.achilles.dao.odps.model;

import com.quancheng.achilles.dao.annotation.OdpsColumn;

public class OdpsFlyCheck {
    private String id;
    /***/
    @OdpsColumn("client_id")
    private String clientId;
    /** 企业 */
    @OdpsColumn("happen_date")
    private String happenDate;
    /** 日期 */

    private String city;
    /** 区域名称 */
    @OdpsColumn("order_count")
    private Long orderCount;
    /** 订单数 */
    @OdpsColumn("check_count")
    private Long checkCount;
    /** 飞检数 */
    @OdpsColumn("ing_count_correct")
    private Long ingCountCorrect;
    /** 事中-正常 */
    @OdpsColumn("ing_count_incorrect")
    private Long ingCountIncorrect;
    /** 事中-异常 */
    @OdpsColumn("ing_check_rate")
    private Double ingCheckRate;
    /** 事中-飞检比例 */
    @OdpsColumn("ing_count_reason")
    private String ingCountReason;
    /** 事中-异常原因 */
    @OdpsColumn("after_count_correct")
    private Long afterCountCorrect;
    /** 事后-正常 */
    @OdpsColumn("after_count_incorrect")
    private Long afterCountIncorrect;
    /** 事后-异常 */
    @OdpsColumn("after_check_rate")
    private Double afterCheckRate;
    /** 事后-检查比例 */
    @OdpsColumn("after_count_reason")
    private String afterCountReason;
    /** 事后-异常原因 */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getHappenDate() {
        return happenDate;
    }
    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getOrderCount() {
        return orderCount;
    }
    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }
    public Long getCheckCount() {
        return checkCount;
    }
    public void setCheckCount(Long checkCount) {
        this.checkCount = checkCount;
    }
    public Long getIngCountCorrect() {
        return ingCountCorrect;
    }
    public void setIngCountCorrect(Long ingCountCorrect) {
        this.ingCountCorrect = ingCountCorrect;
    }
    public Long getIngCountIncorrect() {
        return ingCountIncorrect;
    }
    public void setIngCountIncorrect(Long ingCountIncorrect) {
        this.ingCountIncorrect = ingCountIncorrect;
    }
    public Double getIngCheckRate() {
        return ingCheckRate;
    }
    public void setIngCheckRate(Double ingCheckRate) {
        this.ingCheckRate = ingCheckRate;
    }
    public String getIngCountReason() {
        return ingCountReason;
    }
    public void setIngCountReason(String ingCountReason) {
        this.ingCountReason = ingCountReason;
    }
    public Long getAfterCountCorrect() {
        return afterCountCorrect;
    }
    public void setAfterCountCorrect(Long afterCountCorrect) {
        this.afterCountCorrect = afterCountCorrect;
    }
    public Long getAfterCountIncorrect() {
        return afterCountIncorrect;
    }
    public void setAfterCountIncorrect(Long afterCountIncorrect) {
        this.afterCountIncorrect = afterCountIncorrect;
    }
    public Double getAfterCheckRate() {
        return afterCheckRate;
    }
    public void setAfterCheckRate(Double afterCheckRate) {
        this.afterCheckRate = afterCheckRate;
    }
    public String getAfterCountReason() {
        return afterCountReason;
    }
    public void setAfterCountReason(String afterCountReason) {
        this.afterCountReason = afterCountReason;
    }
}
