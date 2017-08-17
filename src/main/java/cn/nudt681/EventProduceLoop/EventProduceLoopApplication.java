package cn.nudt681.EventProduceLoop;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.nudt681.EventProduceLoop.mock.PluginEventIds;

@SpringBootApplication
@EnableScheduling
public class EventProduceLoopApplication implements CommandLineRunner
{
    @Autowired
    private JdbcTemplate jdbctemplate;

    public static final String pluginEventTableName = "pluginevent";

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
    }
}
