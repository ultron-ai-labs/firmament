package cn.org.ultronai.firmament.admin.biz.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.org.ultronai.firmament.support.indicator.model.OHLCV;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/13 15:49
 */
class KlineCacheManager {
    /**
     * 是否初始化完成
     */
    private final AtomicBoolean                initialized = new AtomicBoolean(false);
    // 按 openTime 倒序排序（最新的 openTime 更大，排在前面）
    private final ConcurrentSkipListSet<OHLCV> sortedOHLCVs;

    public KlineCacheManager() {
        // 按 openTime 倒序排序（从大到小）
        this.sortedOHLCVs = new ConcurrentSkipListSet<>((o1, o2) -> {
            // 降序：最新的（更大的 openTime）排在前面
            return Long.compare(o2.getOpenTime(), o1.getOpenTime());
        });
    }

    /**
     * 添加一个新的 OHLCV 数据
     */
    public void add(OHLCV ohlcv) {
        sortedOHLCVs.add(ohlcv);

        // 只保留最新的 1000 条
        if (sortedOHLCVs.size() > 1000) {
            // 移除最旧的一条（即 openTime 最小的，排在最后）
            OHLCV oldest = sortedOHLCVs.last(); // 因为是倒序，last() 是最小的 openTime
            sortedOHLCVs.remove(oldest);
        }
    }

    /**
     * 获取所有缓存数据（已经按 openTime 倒序排列，即最新在前）
     */
    public List<OHLCV> getAll() {
        return new ArrayList<>(sortedOHLCVs);
    }

    /**
     * 获取缓存大小
     */
    public int size() {
        return sortedOHLCVs.size();
    }

    /**
     * 清空缓存
     */
    public void clear() {
        sortedOHLCVs.clear();
        initialized.set(false);
    }

    public boolean isInitialized() {
        return initialized.get();
    }

    public void initialized() {
        initialized.set(true);
    }
}
