package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.prompt.model.AccountInfo;
import cn.org.ultronai.firmament.admin.biz.prompt.model.MarketInfo;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 08:39
 */
public interface PromptAssemblyService {
    /**
     * 获取提示词
     *
     * @param currentMarketInfos 当前市场信息
     * @param account          订单信息
     * @param prompt           提示词
     * @return 提示词
     */
    String getRealPrompt(List<MarketInfo> currentMarketInfos, AccountInfo account, PromptDO prompt);

}
