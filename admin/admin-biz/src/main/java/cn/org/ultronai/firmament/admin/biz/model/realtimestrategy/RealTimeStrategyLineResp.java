package cn.org.ultronai.firmament.admin.biz.model.realtimestrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 21:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeStrategyLineResp {
    private List<String> x;
    private List<Double> y;
}
