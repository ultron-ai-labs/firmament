package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptPageResp;
import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptReq;
import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptResp;
import cn.org.ultronai.firmament.admin.biz.service.PromptService;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.common.Paginator;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.PromptDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/12 21:30
 */
@Service("promptService")
public class PromptServiceImpl extends BaseService implements PromptService {
    @Resource
    private PromptDAO promptDAO;

    /**
     * 获取提示词模板
     *
     * @param accessToken accessToken
     * @param req req
     * @return 提示词 提示词
     */
    @Override
    public PromptPageResp getPromptList(String accessToken, PromptReq req) {
        UserDO userDO = getUserDO(accessToken);
        PageList<PromptDO> promptList = promptDAO.pageQueryByNameAndMemberId(userDO.getMemberId(), req.getName(), req.getPage(), req.getPageSize());
        Paginator paginator = promptList.getPaginator();
        PromptPageResp promptPageResp = new PromptPageResp();
        promptPageResp.setTotal(paginator.getTotalCount());
        promptPageResp.setPage(paginator.getCurrentPage());
        promptPageResp.setPageSize(paginator.getPageSize());
        promptPageResp.setRecords(buildRecords(promptList.getData()));
        return promptPageResp;
    }

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    @Override
    public boolean addPrompt(String accessToken, PromptReq req) {
        UserDO userDO = getUserDO(accessToken);
        // 根据name+会员id查询，如果存在了不能保存
        PromptDO dbRecord = promptDAO.queryByName(req.getName(), userDO.getMemberId());
        if (dbRecord != null) {
            throw new RuntimeException("提示词已存在");
        }
        PromptDO promptDO = new PromptDO();
        promptDO.setName(req.getName());
        promptDO.setPromptId(PrefixUtils.genPromptId(userDO.getMemberId()));
        promptDO.setLanguage(req.getLanguage());
        promptDO.setSystemPrompt(req.getSystemPrompt());
        promptDO.setMarketPrompt(req.getMarketPrompt());
        promptDO.setIndicatorPrompt(req.getIndicatorPrompt());
        promptDO.setAccountPrompt(req.getAccountPrompt());
        promptDO.setRunningOrderPrompt(req.getRunningOrderPrompt());
        promptDO.setRiskManagementPrompt(req.getRiskManagementPrompt());
        promptDO.setTradingRulePrompt(req.getTradingRulePrompt());
        promptDO.setExitStrategyPrompt(req.getExitStrategyPrompt());
        promptDO.setSignalPrompt(req.getSignalPrompt());
        promptDO.setOutputFormatPrompt(req.getOutputFormatPrompt());
        promptDO.setMemberId(userDO.getMemberId());
        promptDAO.insert(promptDO);
        return true;
    }

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    @Override
    public boolean updatePrompt(String accessToken, PromptReq req) {
        UserDO userDO = getUserDO(accessToken);
        PromptDO promptDO = promptDAO.queryByPromptId(req.getPromptId());
        // 如果判断数据库中的name是否有重复的
        PromptDO dbRecord = promptDAO.queryByName(req.getName(), userDO.getMemberId());
        if (dbRecord != null && !dbRecord.getPromptId().equals(req.getPromptId())) {
            throw new RuntimeException("提示词名字已存在");
        }
        if (promptDO != null) {
            promptDO.setName(req.getName());
            promptDO.setLanguage(req.getLanguage());
            promptDO.setSystemPrompt(req.getSystemPrompt());
            promptDO.setMarketPrompt(req.getMarketPrompt());
            promptDO.setIndicatorPrompt(req.getIndicatorPrompt());
            promptDO.setAccountPrompt(req.getAccountPrompt());
            promptDO.setRunningOrderPrompt(req.getRunningOrderPrompt());
            promptDO.setRiskManagementPrompt(req.getRiskManagementPrompt());
            promptDO.setTradingRulePrompt(req.getTradingRulePrompt());
            promptDO.setExitStrategyPrompt(req.getExitStrategyPrompt());
            promptDO.setSignalPrompt(req.getSignalPrompt());
            promptDO.setOutputFormatPrompt(req.getOutputFormatPrompt());
            promptDO.setMemberId(userDO.getMemberId());
            promptDAO.update(promptDO);
            return true;
        }
        throw new RuntimeException("提示词不存在");
    }

    /**
     * 获取提示词
     *
     * @return 提示词
     */
    @Override
    public List<PromptResp> getAllPromptList(String accessToken) {
        UserDO userDO = getUserDO(accessToken);
        List<PromptDO> promptDOList = promptDAO.queryAllByMemberId(userDO.getMemberId());
        List<PromptResp> ret = Lists.newArrayList();
        for (PromptDO promptDO : promptDOList) {
            PromptResp promptResp = mapper(promptDO);
            ret.add(promptResp);
        }
        return ret;
    }

    private List<PromptResp> buildRecords(Collection<PromptDO> data) {
        List<PromptResp> records = Lists.newArrayList();
        for (PromptDO datum : data) {
            records.add(mapper(datum));
        }
        return records;
    }

    private static PromptResp mapper(PromptDO promptDO) {
        PromptResp promptResp = new PromptResp();
        promptResp.setName(promptDO.getName());
        promptResp.setPromptId(promptDO.getPromptId());
        promptResp.setLanguage(promptDO.getLanguage());
        promptResp.setSystemPrompt(promptDO.getSystemPrompt());
        promptResp.setMarketPrompt(promptDO.getMarketPrompt());
        promptResp.setIndicatorPrompt(promptDO.getIndicatorPrompt());
        promptResp.setAccountPrompt(promptDO.getAccountPrompt());
        promptResp.setRunningOrderPrompt(promptDO.getRunningOrderPrompt());
        promptResp.setRiskManagementPrompt(promptDO.getRiskManagementPrompt());
        promptResp.setTradingRulePrompt(promptDO.getTradingRulePrompt());
        promptResp.setExitStrategyPrompt(promptDO.getExitStrategyPrompt());
        promptResp.setSignalPrompt(promptDO.getSignalPrompt());
        promptResp.setOutputFormatPrompt(promptDO.getOutputFormatPrompt());
        promptResp.setCreateTime(promptDO.getCreateTime());
        return promptResp;
    }

}
