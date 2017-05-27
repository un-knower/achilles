package com.quancheng.achilles.dao.model;


/**
 * @author liujiejian
 * @version 2016年9月9日
 */
public class OrderRecord {

    private String  orderNum;
    private Integer company;
    private String  realname;
    private String  jobNum;
    private String  email;
    private String  mobile;

    private String  branch;
    private String  businessunit;
    private String  sector;
    private String  region;
    private String  productgroup;
    private String  costcenter;

    private String  restaurantName;
    private Integer restaurantId;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBusinessunit() {
        return businessunit;
    }

    public void setBusinessunit(String businessunit) {
        this.businessunit = businessunit;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    private String  merchantName;

    private Integer salesId;

    private String  cityName;
    private String  serviceType;
    private String  orderType;
    private String  payType;
    private String  reportReason;
    private Integer isRoom;
    private String    createTime;
    private String    receiveTime;
    private String    yuyueTime;
    private Integer isDelivery;
    private String    confirmTime;
    private String    payTime;
    private String    reportTime;
    private Integer status;
    private Integer peopleNum;
    private Double  predictCost;
    private Integer actualPeople;
    private Double  money;
    private String  averageMoney;
    private String  address;
    private Integer isRestaurantFirst;
    private String  manageType;
    private Integer isScore;
    private Integer score;
    private String    orderRateCreateTime;
    private Integer dishScore;
    private Integer deliveryTimeScore;
    private Integer restaurantScore;
    private Integer packagingScore;
    private Integer sytServiceScore;
    private String  reason;
    private String    replyTime;
    private String  replyContent;
    private String  rates;
    private Double  rateMoney;
    private Long    preOrderTime;
    private Long    responseOrderTime;
    /**审批编号*/
    private String approvalCode;
    /**审批编号*/
    private String cardNumber;
    public Integer getIsRoom() {
        return isRoom;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public Integer getIsRestaurantFirst() {
        return isRestaurantFirst;
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public Integer getIsScore() {
        return isScore;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public void setIsRoom(Integer isRoom) {
        this.isRoom = isRoom;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getYuyueTime() {
        return yuyueTime;
    }

    public void setYuyueTime(String yuyueTime) {
        this.yuyueTime = yuyueTime;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Double getPredictCost() {
        return predictCost;
    }

    public void setPredictCost(Double predictCost) {
        this.predictCost = predictCost;
    }

    public Integer getActualPeople() {
        return actualPeople;
    }

    public void setActualPeople(Integer actualPeople) {
        this.actualPeople = actualPeople;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(String averageMoney) {
        this.averageMoney = averageMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIsRestaurantFirst(Integer isRestaurantFirst) {
        this.isRestaurantFirst = isRestaurantFirst;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public void setIsScore(Integer isScore) {
        this.isScore = isScore;
    }

    public String getOrderRateCreateTime() {
        return orderRateCreateTime;
    }

    public void setOrderRateCreateTime(String orderRateCreateTime) {
        this.orderRateCreateTime = orderRateCreateTime;
    }

    public Integer getDishScore() {
        return dishScore;
    }

    public void setDishScore(Integer dishScore) {
        this.dishScore = dishScore;
    }

    public Integer getDeliveryTimeScore() {
        return deliveryTimeScore;
    }

    public void setDeliveryTimeScore(Integer deliveryTimeScore) {
        this.deliveryTimeScore = deliveryTimeScore;
    }

    public Integer getRestaurantScore() {
        return restaurantScore;
    }

    public void setRestaurantScore(Integer restaurantScore) {
        this.restaurantScore = restaurantScore;
    }

    public Integer getPackagingScore() {
        return packagingScore;
    }

    public void setPackagingScore(Integer packagingScore) {
        this.packagingScore = packagingScore;
    }

    public Integer getSytServiceScore() {
        return sytServiceScore;
    }

    public void setSytServiceScore(Integer sytServiceScore) {
        this.sytServiceScore = sytServiceScore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public Double getRateMoney() {
        return rateMoney;
    }

    public void setRateMoney(Double rateMoney) {
        this.rateMoney = rateMoney;
    }

    public Long getPreOrderTime() {
        return preOrderTime;
    }

    public void setPreOrderTime(Long preOrderTime) {
        this.preOrderTime = preOrderTime;
    }

    public Long getResponseOrderTime() {
        return responseOrderTime;
    }

    public void setResponseOrderTime(Long responseOrderTime) {
        this.responseOrderTime = responseOrderTime;
    }

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
