package com.quancheng.achilles.service.constants;

/***
 * 作废 从tmp_restauran_sources表查询
 * @author Administrator
 *
 */
public enum InnConstantsRecommandRs {
    REST_RECOMMAND_RS_1("1","辉瑞官方推荐"),
    REST_RECOMMAND_RS_4("4","阿斯利康官方推荐"),
    REST_RECOMMAND_RS_5("5","惠氏官方推荐"),
    REST_RECOMMAND_RS_9("9","强生官方推荐"),
    REST_RECOMMAND_RS_24("24","罗氏官方推荐"),
    REST_RECOMMAND_RS_42("42","优时比官方推荐"),
    REST_RECOMMAND_RS_43("43","艾尔建官方推荐"),
    REST_RECOMMAND_RS_124("124","默沙东官方推荐"),
    REST_RECOMMAND_RS_125("125","雅培官方推荐"),
    REST_RECOMMAND_RS_126("126","住友官方推荐"),
    REST_RECOMMAND_RS_1000("1000","历史数据"),
    REST_RECOMMAND_RS_1005("1005","自行拓展"),
    REST_RECOMMAND_RS_1010("1010","渠道KA餐厅"),
    REST_RECOMMAND_RS_1030("1030","云纵拓展"),
    REST_RECOMMAND_RS_2001("2001","辉瑞APP推荐"),
    REST_RECOMMAND_RS_2004("2004","阿斯利康APP推荐"),
    REST_RECOMMAND_RS_2005("2005","惠氏APP推荐"),
    REST_RECOMMAND_RS_2009("2009","强生APP推荐"),
    REST_RECOMMAND_RS_2024("2024","罗氏APP推荐"),
    REST_RECOMMAND_RS_2042("2042","优时比APP推荐"),
    REST_RECOMMAND_RS_2043("2043","艾尔建APP推荐"),
    REST_RECOMMAND_RS_2124("2124","默沙东APP推荐"),
    REST_RECOMMAND_RS_2125("2125","雅培APP推荐"),
    REST_RECOMMAND_RS_2126("2126","住友APP推荐");
    public final String key,value;
    private InnConstantsRecommandRs(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
