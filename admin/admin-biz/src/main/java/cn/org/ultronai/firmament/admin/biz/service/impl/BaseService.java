package cn.org.ultronai.firmament.admin.biz.service.impl;

import javax.annotation.Resource;

import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.UserDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:46
 */
public class BaseService {
    @Resource
    protected UserDAO userDAO;

    protected UserDO getUserDO(String accessToken) {
        UserDO userDO = userDAO.queryByAccessToken(accessToken);
        if (userDO != null) {
            return userDO;
        }
        throw new RuntimeException("用户不存在");
    }
}
