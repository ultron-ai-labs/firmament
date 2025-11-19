package cn.org.ultronai.firmament.admin.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:59
 */
@Data
public class BasePageReq implements Serializable {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页大小
     */
    private Integer pageSize;
}
