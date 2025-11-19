package cn.org.ultronai.firmament.admin.biz.model.account;

import lombok.Data;

import java.io.Serializable;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/05 21:12
 */
@Data
public class AccountChangePasswordReq implements Serializable {
    private String oldPassword;
    private String newPassword;
}
