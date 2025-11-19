package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.PromptDO;

/**
 * AI 模块需要使用的提示词
 * 
 * @author icanci
 * @since 1.0 Created in 2025/11/12 08:38
 */
public interface PromptDAO extends BaseDAO<PromptDO> {
    /**
     * 根据提示词ID查询提示词
     *
     * @param promptId 提示词ID
     * @return 提示词
     */
    PromptDO queryByPromptId(String promptId);

    /**
     * 根据会员ID查询提示词
     *
     * @param memberId 会员ID
     * @return 提示词
     */
    List<PromptDO> queryAllByMemberId(String memberId);

    /**
     * 根据会员ID和名称查询提示词
     *
     * @param memberId 会员ID
     * @param name     提示词名称
     * @return 提示词
     */
    PageList<PromptDO> pageQueryByNameAndMemberId(String memberId, String name, Integer page, Integer pageSize);

    /**
     * 根据名称查询提示词
     *
     * @param name     提示词名称
     * @param memberId 会员ID
     * @return 提示词
     */
    PromptDO queryByName(String name, String memberId);
}
