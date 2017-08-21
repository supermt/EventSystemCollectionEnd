/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: Transformer.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.loop 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月17日 上午2:46:52 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Transformer
 * @Description: TODO
 * @author: MT
 */
@Component
public class Transformer
{
    public static final Map<Long, String> regionMap = new HashMap<>();

    public static Long[] ipArray;

    private static final Logger logger = LoggerFactory
        .getLogger(Transformer.class);
    public static final String RegionTableName = "ip2region";

    public static final String ipColumnName = "ipstop";

    public static boolean mapReady = false;

    @Autowired
    JdbcTemplate jdbctemplate;

    public List<String> transIPtoArea(List<Long> sourceIp)
    {
        // List<Long> sourceIp = new ArrayList<>(1000);

        Long start = System.currentTimeMillis();

        List<String> resultarea = new ArrayList<>();

        for (Long ip : sourceIp)
        {
            int location = Transformer.binaryLocation(ip, Transformer.ipArray);
            String targetArea = Transformer.regionMap
                .get(Transformer.ipArray[location]);
            resultarea.add(targetArea);
        }

        Long end = System.currentTimeMillis();
        logger.info("Takes " + (end - start) + " to mapping");
        return resultarea;
    }

    public static int binaryLocation(Long key, Long[] iArr)
    {

        int middle = 0;
        int low = 0;
        int high = iArr.length - 1;

        while (low < high)
        {
            middle = (high + low + 1) / 2;
            if (iArr[middle] == key)
            {
                break;
            }
            else if (iArr[middle] > key)
            {
                high = middle;
            }
            else
            {
                low = middle;
            }
            if (high - low == 1)
            {
                break;
            }
        }
        return low;
    }

}
