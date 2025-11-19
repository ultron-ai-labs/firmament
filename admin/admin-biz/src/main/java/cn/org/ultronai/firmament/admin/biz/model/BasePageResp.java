package cn.org.ultronai.firmament.admin.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 09:45
 */
@Data
public class BasePageResp implements Serializable {
    /** 数据总数 */
    private Integer total;
    /** 页码 */
    private Integer page;
    /** 每页大小 */
    private Integer pageSize;

    public BasePageResp() {
    }

    public BasePageResp(Integer total, Integer page, Integer pageSize) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }
}
