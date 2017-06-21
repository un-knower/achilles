package com.quancheng.achilles.dao.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liujiejian
 * @version 2016年9月12日
 */
public class OrderRecordVo {

    @ApiModelProperty(value = "订单号")
    private String  orderNum;
    @ApiModelProperty(value = "所属企业")
    private String  company;
    @ApiModelProperty(value = "下单人姓名")
    private String  realname;
    @ApiModelProperty(value = "下单人工号")
    private String  jobNum;
    @ApiModelProperty(value = "下单人邮箱")
    private String  email;
    @ApiModelProperty(value = "下单人手机")
    private String  mobile;
    @ApiModelProperty(value = "销售id")
    private Integer salesId;
    
    @ApiModelProperty(value = "子公司")
    private String  branch;
    @ApiModelProperty(value = "事业部")
    private String  businessunit;
    @ApiModelProperty(value = "部门")
    private String  sector;
    @ApiModelProperty(value = "产品组")
    private String  region;
    @ApiModelProperty(value = "大区")
    private String  productgroup;
    @ApiModelProperty(value = "成本中心")
    private String  costcenter;

    @ApiModelProperty(value = "餐厅名")
    private String  restaurantName;
    // @ApiModelProperty(value = "所属商户")
    @ApiModelProperty(value = "餐厅id")
    private Integer restaurantId;
    @ApiModelProperty(value = "所属商户")
    private String  merchantName;
    @ApiModelProperty(value = "维护销售")
    private String  salesName;
    @ApiModelProperty(value = "城市")
    private String  cityName;
    @ApiModelProperty(value = "业务类型")
    private String  serviceType;
    @ApiModelProperty(value = "订单类型")
    private String  orderType;
    @ApiModelProperty(value = "支付类型")
    private String  payType;
    @ApiModelProperty(value = "报备原因")
    private String  reportReason;
    @ApiModelProperty(value = "是否需要包厢")
    private String  isRoom;
    @ApiModelProperty(value = "下单时间")
    private String  createTime;
    @ApiModelProperty(value = "接单时间")
    private String  receiveTime;
    @ApiModelProperty(value = "预约时间")
    private String  yuyueTime;
    @ApiModelProperty(value = "是否立即配送")
    private String  isDelivery;
    @ApiModelProperty(value = "消费确认时间")
    private String  confirmTime;
    @ApiModelProperty(value = "支付时间")
    private String  payTime;
    @ApiModelProperty(value = "报备时间")
    private String  reportTime;
//    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "预计消费人数")
    private Integer peopleNum;
    @ApiModelProperty(value = "预计消费金额")
    private Double  predictCost;
    @ApiModelProperty(value = "实际用餐人数")
    private Integer actualPeople;
    @ApiModelProperty(value = "实际消费金额")
    private Double  money;
    @ApiModelProperty(value = "人均消费")
    private String  averageMoney;
    @ApiModelProperty(value = "收货地址")
    private String  address;
    @ApiModelProperty(value = "是否餐厅首单")
    private String  isRestaurantFirst;
    @ApiModelProperty(value = "结算类型")
    private String  manageType;
    @ApiModelProperty(value = "是否有评价")
    private String  isScore;
    @ApiModelProperty(value = "评价分数")
    private Integer score;
    @ApiModelProperty(value = "评价日期")
    private String  orderRateCreateTime;
    @ApiModelProperty(value = "菜品口味评分")
    private Integer dishScore;
    @ApiModelProperty(value = "送达时间评分")
    private Integer deliveryTimeScore;
    @ApiModelProperty(value = "餐厅环境评分")
    private Integer restaurantScore;
    @ApiModelProperty(value = "商品包装评分")
    private Integer packagingScore;
    @ApiModelProperty(value = "商宴通服务评分")
    private Integer sytServiceScore;
    @ApiModelProperty(value = "评论")
    private String  reason;
    @ApiModelProperty(value = "回访日期")
    private String  replyTime;
    @ApiModelProperty(value = "回访结果")
    private String  replyContent;
    @ApiModelProperty(value = "返点")
    private String  rates;
    @ApiModelProperty(value = "返点金额")
    private Double  rateMoney;
    @ApiModelProperty(value = "提前预订时间")
    private String  preOrderTime;
    @ApiModelProperty(value = "订单响应时间")
    private String  responseOrderTime;
    @ApiModelProperty(value = "order status china")
    private String  orderSatus;
    @ApiModelProperty(value = "code")
    private String approvalCode;
    @ApiModelProperty(value = "刷卡卡号")
    private String cardNumber;
    @ApiModelProperty(value = "餐厅地址")
    private String restaurantAddress;
    
    @ApiModelProperty(value = "无包间是否接受大厅")
    private String isHall;
    @ApiModelProperty(value = "用餐人备注")
    private String userComment;
    
    public String getOrderSatus() {
        return orderSatus;
    }

    public void setOrderSatus(String orderSatus) {
        this.orderSatus = orderSatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getIsHall() {
		return isHall;
	}

	public void setIsHall(String isHall) {
		this.isHall = isHall;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
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

    public String getIsRoom() {
        return isRoom;
    }

    public void setIsRoom(String isRoom) {
        this.isRoom = isRoom;
    }

    public String getCreateTime() {
        return createTime;
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

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getIsRestaurantFirst() {
        return isRestaurantFirst;
    }

    public void setIsRestaurantFirst(String isRestaurantFirst) {
        this.isRestaurantFirst = isRestaurantFirst;
    }

    public String getManageType() {
        return manageType;
    }

    public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getIsScore() {
        return isScore;
    }

    public void setIsScore(String isScore) {
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

    public String getPreOrderTime() {
        return preOrderTime;
    }

    public void setPreOrderTime(String preOrderTime) {
        this.preOrderTime = preOrderTime;
    }

    public String getResponseOrderTime() {
        return responseOrderTime;
    }

    public void setResponseOrderTime(String responseOrderTime) {
        this.responseOrderTime = responseOrderTime;
    }

	@Override
	public String toString() {
		return "OrderRecordVo [orderNum=" + orderNum + "]";
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

}
