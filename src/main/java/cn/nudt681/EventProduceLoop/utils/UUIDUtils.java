package cn.nudt681.EventProduceLoop.utils;

import java.util.UUID;

/**
 * UUID生成大小写
 * Created by xuliugen on 15/10/18.
 */
public class UUIDUtils {

    /**
     * 产生一个不含有‘-’的32为UUID大写字符串
     */
    public static String get32UUIDAsUpperCase() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 32位大写
     */
    public static String get32UUIDAsLowerCase() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
