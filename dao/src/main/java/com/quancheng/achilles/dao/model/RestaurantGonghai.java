package com.quancheng.achilles.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公海餐厅
 * 
 * @author zhuzhong
 */
@Entity
@Table(name = "tmp_restaurant_query")
public class RestaurantGonghai implements Serializable {

    private static final long serialVersionUID = -3801799321665711586L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String            id;

    @Column(name = "ghid")
    @ApiModelProperty(value = "公海任务ID")
    private Long              ghRestaurantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "olid")
    @ApiModelProperty(value = "餐厅ID")
    private Long    olRestaurantId;

    @Column(name = "store_name")
    @ApiModelProperty(value = "餐厅名称")
    private String  storeName;

    @Column(name = "city")
    @ApiModelProperty(value = "城市")
    private String  city;

    @Column(name = "city_display")
    @ApiModelProperty(value = "城市")
    private String  cityDisplay;

    @Column(name = "area_display")
    @ApiModelProperty(value = "行政区")
    private String  areaDisplay;

    @Column(name = "area")
    @ApiModelProperty(value = "行政区")
    private String  area;

    @Column(name = "address")
    @ApiModelProperty(value = "餐厅地址")
    private String  address;

    @Column(name = "own_companys_id")
    @ApiModelProperty(value = "所属企业ID")
    private String  ownCompanysId;

    @Column(name = "own_companys")
    @ApiModelProperty(value = "所属企业")
    private String  ownCompanys;

    @Column(name = "rest_merchant")
    @ApiModelProperty(value = "所属商户")
    private String  ownMerchants;

    @Column(name = "rest_status")
    @ApiModelProperty(value = "餐厅状态")
    private String  restaurantState;

    @Column(name = "gonghai_status")
    @ApiModelProperty(value = "公海状态")
    private String  gonghaiStatus;
    
    @Column(name = "restaurant_sources")
    @ApiModelProperty(value = "餐厅来源")
    private String  restaurantSources;

    @Column(name = "gh_task_name")
    @ApiModelProperty(value = "公海任务名")
    private String  ghTaskName;

    @Column(name = "recommends_emails")
    @ApiModelProperty(value = "推荐人邮箱")
    private String  recommendsEmails;

    @Column(name = "recommends_company")
    @ApiModelProperty(value = "推荐企业")
    private String  recommendsCompany;

    @Column(name = "priority")
    @ApiModelProperty(value = "开拓优先级")
    private String  priority;

    @Column(name = "prj_names")
    @ApiModelProperty(value = "项目名称")
    private String  projectNames;

    @Column(name = "in_store_at")
    @ApiModelProperty(value = "入库时间")
    private Date    inStoreAt;

    @Column(name = "created_at")
    @ApiModelProperty(value = "创建时间")
    private String  createdAt;

    @Column(name = "online_time")
    @ApiModelProperty(value = "上线时间")
    private String  olTime;

    @Column(name = "shelf_time")
    @ApiModelProperty(value = "下线时间")
    private Date    shelfTime;

    @Column(name = "reason")
    @ApiModelProperty(value = "下线原因")
    private String  reason;

    @Column(name = "consume")
    @ApiModelProperty(value = "餐厅人均")
    private Double  consume;
    /** 实际人均 */
    @Column(name = "avg_actual")
    @ApiModelProperty(value = "实际人均")
    private Double  consumeActual;

    /** 人均偏差=实际人均-餐厅人均 */
    @Column(name = "consume_deviation")
    @ApiModelProperty(value = "人均偏差")
    private Double  consumeDeviation;

    @Column(name = "support_takeout_of_food")
    @ApiModelProperty(value = "是否支持外卖")
    private String  supportTakeoutOfFood;

    @Column(name = "cook_style")
    @ApiModelProperty(value = "菜系")
    private String  cookStyle;

    @Column(name = "support_reserve")
    @ApiModelProperty(value = "是否支持订座")
    private String  supportReserve;

    @Column(name = "category_name")
    @ApiModelProperty(value = "外卖种类")
    private String  categorys;

    @Column(name = "manage_type")
    @ApiModelProperty(value = "结算类型")
    private String  manageType;

    @Column(name = "count_train")
    @ApiModelProperty(value = "累计培训次数")
    private Integer countTrain;

    @Column(name = "count_visit")
    @ApiModelProperty(value = "累计拜访次数")
    private Integer countVisit;

     @ApiModelProperty(value = "返点")
     @Column(name = "rebate")
     private String rebate;

    @Column(name = "count_money")
    @ApiModelProperty(value = "订单总额")
    private String  countMoney;

    @Column(name = "count_order")
    @ApiModelProperty(value = "订单总数")
    private Integer countOrder;

    @Column(name = "err_times")
    @ApiModelProperty(value = "飞检异常次数")
    private Integer errTimes;

    /** 用户集中度=在该餐厅消费金额最高的用户总消费/餐厅总流水 */
    @Column(name = "user_concentration")
    @ApiModelProperty(value = "用户集中度")
    private Double  userConcentration;

    @Column(name = "sales_name")
    @ApiModelProperty(value = "维护销售")
    private String  salesName;
    
    @Column(name = "box_num")
    @ApiModelProperty(value = "包厢数")
    private Integer boxNum;
    public Long getOlRestaurantId() {
        return olRestaurantId;
    }

    public void setOlRestaurantId(Long olRestaurantId) {
        this.olRestaurantId = olRestaurantId;
    }

    public Long getGhRestaurantId() {
        return ghRestaurantId;
    }

    public void setGhRestaurantId(Long ghRestaurantId) {
        this.ghRestaurantId = ghRestaurantId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnCompanys() {
        return ownCompanys;
    }

    public void setOwnCompanys(String ownCompanys) {
        this.ownCompanys = ownCompanys;
    }

    public String getOwnMerchants() {
        return ownMerchants;
    }

    public void setOwnMerchants(String ownMerchants) {
        this.ownMerchants = ownMerchants;
    }

    public String getRestaurantState() {
        return restaurantState;
    }

    public void setRestaurantState(String restaurantState) {
        this.restaurantState = restaurantState;
    }

    public String getRestaurantSources() {
        return restaurantSources;
    }

    public void setRestaurantSources(String restaurantSources) {
        this.restaurantSources = restaurantSources;
    }

    public String getGhTaskName() {
        return ghTaskName;
    }

    public void setGhTaskName(String ghTaskName) {
        this.ghTaskName = ghTaskName;
    }

    public String getRecommendsEmails() {
        return recommendsEmails;
    }

    public void setRecommendsEmails(String recommendsEmails) {
        this.recommendsEmails = recommendsEmails;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }

    public Date getInStoreAt() {
        return inStoreAt;
    }

    public void setInStoreAt(Date inStoreAt) {
        this.inStoreAt = inStoreAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getOlTime() {
        return olTime;
    }

    public void setOlTime(String olTime) {
        this.olTime = olTime;
    }

    public Date getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Double getConsumeActual() {
        return consumeActual;
    }

    public void setConsumeActual(Double consumeActual) {
        this.consumeActual = consumeActual;
    }

    public Double getConsumeDeviation() {
        return consumeDeviation;
    }

    public void setConsumeDeviation(Double consumeDeviation) {
        this.consumeDeviation = consumeDeviation;
    }

    public String getCookStyle() {
        return cookStyle;
    }

    public void setCookStyle(String cookStyle) {
        this.cookStyle = cookStyle;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public Integer getCountTrain() {
        return countTrain;
    }

    public void setCountTrain(Integer countTrain) {
        this.countTrain = countTrain;
    }

    public Integer getCountVisit() {
        return countVisit;
    }

    public void setCountVisit(Integer countVisit) {
        this.countVisit = countVisit;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public Integer getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(Integer countOrder) {
        this.countOrder = countOrder;
    }

    public Integer getErrTimes() {
        return errTimes;
    }

    public void setErrTimes(Integer errTimes) {
        this.errTimes = errTimes;
    }

    public Double getUserConcentration() {
        return userConcentration;
    }

    public void setUserConcentration(Double userConcentration) {
        this.userConcentration = userConcentration;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public RestaurantGonghai(){
        super();
    }

    public String getCityDisplay() {
        return cityDisplay;
    }

    public void setCityDisplay(String cityDisplay) {
        this.cityDisplay = cityDisplay;
    }

    public String getAreaDisplay() {
        return areaDisplay;
    }

    public void setAreaDisplay(String areaDisplay) {
        this.areaDisplay = areaDisplay;
    }

    public String getRecommendsCompany() {
        return recommendsCompany;
    }

    public void setRecommendsCompany(String recommendsCompany) {
        this.recommendsCompany = recommendsCompany;
    }

    public String getSupportTakeoutOfFood() {
        return supportTakeoutOfFood;
    }

    public void setSupportTakeoutOfFood(String supportTakeoutOfFood) {
        this.supportTakeoutOfFood = supportTakeoutOfFood;
    }

    public String getSupportReserve() {
        return supportReserve;
    }

    public void setSupportReserve(String supportReserve) {
        this.supportReserve = supportReserve;
    }

    public String getOwnCompanysId() {
        return ownCompanysId;
    }

    public void setOwnCompanysId(String ownCompanysId) {
        this.ownCompanysId = ownCompanysId;
    }

	public String getGonghaiStatus() {
		return gonghaiStatus;
	}

	public void setGonghaiStatus(String gonghaiStatus) {
		this.gonghaiStatus = gonghaiStatus;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public Integer getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}
}
