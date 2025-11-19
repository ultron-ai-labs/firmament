package cn.org.ultronai.firmament.admin.dal.mongo.dateobject;

import java.util.List;

import lombok.Data;

/**
 * 系统指标：只能管理员维护，然后公布
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/12 08:15
 */
@Data
public class SystemIndicatorDO extends BaseDO {
    /**
     * 指标名称
     */
    private String                     name;
    /**
     * 指标分类大类: trend、volume
     */
    private String                     category;
    /**
     * 指标子类，对应指标库的指标
     */
    private String                     subCategory;
    /**
     * 描述
     */
    private String                     fullDescription;
    /**
     * 指标公式
     */
    private String                     formula;
    /**
     * 参数: 不同指标适用于不同的参数
     */
    private List<IndicatorParameterDO> parameters;
}
