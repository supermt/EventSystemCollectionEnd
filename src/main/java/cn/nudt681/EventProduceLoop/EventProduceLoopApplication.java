package cn.nudt681.EventProduceLoop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.nudt681.EventProduceLoop.mock.PluginEventIds;
import cn.nudt681.EventProduceLoop.utils.Transformer;

@SpringBootApplication
@EnableScheduling
public class EventProduceLoopApplication implements CommandLineRunner
{
    @Autowired
    private JdbcTemplate jdbctemplate;

    public static final String pluginEventTableName = "pluginevent";

    public static final String oriIpRegionTableName = "ip2country";

    private static final Logger logger = LoggerFactory
        .getLogger(EventProduceLoopApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(EventProduceLoopApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception
    {

        List<Map<String, Object>> result = jdbctemplate
            .queryForList("select * from " + pluginEventTableName);

        PluginEventIds.eventList.addAll(result);

        List<Map<String, Object>> recordCountries = jdbctemplate
            .queryForList("select * from " + oriIpRegionTableName);

        Map<Long, String> recordedCountry = new HashMap<Long, String>();

        for (Map<String, Object> row : recordCountries)
        {
            recordedCountry.put(Long.valueOf(row.get("end").toString()),
                row.get("country").toString());
        }

        List<Map<String, Object>> ipRegions = jdbctemplate
            .queryForList("select * from " + Transformer.RegionTableName
                + " order by " + Transformer.ipColumnName);
        for (Map<String, Object> ipRegionPair : ipRegions)
        {
            Long key = Long
                .valueOf(ipRegionPair.get(Transformer.ipColumnName).toString());
            if (!recordedCountry.containsKey(key))
                continue;
            String province = ipRegionPair.get("province").toString();
            String area = ("0").equals(province) ? recordedCountry.get(key)
                : province;
            Transformer.regionMap.put(key, area);
        }

        Set<Long> endIpSet = Transformer.regionMap.keySet();
        List<Long> endIpArray = new ArrayList<Long>(endIpSet);
        endIpArray.sort(new Comparator<Long>()
        {

            @Override
            public int compare(Long o1, Long o2)
            {
                return o1.compareTo(o2);
            }
        });
        Transformer.ipArray = new Long[endIpArray.size()];
        int pointer = 0;
        for (Long ip : endIpArray)
        {
            Transformer.ipArray[pointer] = ip;
            pointer++;
        }

        Transformer.mapReady = true;
        logger.info("Finish Basic Info Mapping");
    }
}
