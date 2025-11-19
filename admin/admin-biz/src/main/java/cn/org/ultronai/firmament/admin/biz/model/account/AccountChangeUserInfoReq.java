package cn.org.ultronai.firmament.admin.biz.model.account;

import java.io.Serializable;

import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/05 21:12
 */
@Data
public class AccountChangeUserInfoReq implements Serializable {
    private String phone;
    private String email;
}
