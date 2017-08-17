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
    private static final Logger logger = LoggerFactory
        .getLogger(Transformer.class);
    private static final String RegionTableName = "ip2regionuseint";

    private static final String ipColumnName = "ipstop";

    @Autowired
    JdbcTemplate jdbctemplate;

    public List<String> transIPtoArea(List<Long> sourceIp)
    {
        // List<Long> sourceIp = new ArrayList<>(1000);

        Long start = System.currentTimeMillis();
        int i = 0;
        /**
         * for (i = 0; i < 1000; i++) { sourceIp.add(774162663l + i); }
         */
        StringBuilder sqlbuilder = new StringBuilder();
        for (i = 0; i < sourceIp.size() - 1; i++)
        {
            sqlbuilder.append("(SELECT * FROM " + RegionTableName + " where "
                + ipColumnName + " >= " + sourceIp.get(i) + " order by "
                + ipColumnName + " limit 1)");
            sqlbuilder.append("union all");
        }
        sqlbuilder.append("(SELECT * FROM " + RegionTableName + " where "
            + ipColumnName + " >= " + sourceIp.get(i) + " order by "
            + ipColumnName + " limit 1)");
        Long end = System.currentTimeMillis();
        logger.info("Takes " + (end - start) + " to build sql");

        List<Map<String, Object>> result = jdbctemplate
            .queryForList(sqlbuilder.toString());

        start = System.currentTimeMillis();
        logger.info("Takes " + (start - end) + " to mapping");

        List<String> resultarea = new ArrayList<>();
        for (Map<String, Object> row : result)
        {
            String province = row.get("province").toString();
            String area = ("0").equals(province) ? row.get("country").toString()
                : province;
            resultarea.add(area);
        }
        end = System.currentTimeMillis();
        logger.info("Takes " + (end - start) + " to handle result");
        return resultarea;
    }
}
