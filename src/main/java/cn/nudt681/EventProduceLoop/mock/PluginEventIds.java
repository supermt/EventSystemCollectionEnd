/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: PluginEventIds.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.mock 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月17日 上午11:13:26 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PluginEventIds
 * @Description: TODO
 * @author: MT
 */
public class PluginEventIds
{
    public static final List<Map<String, Object>> eventList = new ArrayList<>();

    public static final int[][] specialIds =
    {
        { 19522, 19912, 1961 },
        { 8944, 9589 },
        { 370, 513, 520 },
        { 195, 194, 246, 217, 222 } };

    public static final Map<Long, Long> specialPairs = new HashMap<Long, Long>();
}
