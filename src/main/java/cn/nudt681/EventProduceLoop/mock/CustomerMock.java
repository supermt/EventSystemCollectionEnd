/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: RandomMock.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.mock 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月17日 上午11:09:58 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.mock;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.nudt681.EventProduceLoop.config.ChannelScalars;
import cn.nudt681.EventProduceLoop.model.Event;
import cn.nudt681.EventProduceLoop.utils.Transformer;

/**
 * @ClassName: RandomMock
 * @Description: TODO
 * @author: MT
 */
@Component
public class CustomerMock
{

    private static final Logger logger = LoggerFactory
        .getLogger(CustomerMock.class);

    @Value("${task.mock.customerThread}")
    private static int threadNum = 10;

    @Autowired
    private KafkaTemplate<String, String> kafkaChannel;

    @Value("${task.mock.customer}")
    private boolean toggle = false;

    @Value("${task.mock.customerGap}")
    private int gapTime = 10000;//默认每10秒启动进行一次

    @Autowired
    private JdbcTemplate jdbcTool;

    @Scheduled(fixedRate = 1000)
    public void sendLoop()
    {
        if (!Transformer.mapReady || !toggle)
            return;

        logger.debug("Using {} threads to produce records", threadNum);

        for (int i = 0; i < threadNum; i++)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    Event[] payloads = customerMocking();
                    if (payloads == null)
                        return;
                    for (Event payload : payloads)
                    {
                        try
                        {
                            Thread.sleep(gapTime
                                + Math.abs(new Random().nextInt() % gapTime));
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        payload.setTime(
                            Timestamp.from(Calendar.getInstance().toInstant())
                                .toString());
                        //                System.out.println(new Gson().toJson(payload));
                        kafkaChannel.send(ChannelScalars.inputChannel,
                            new Gson().toJson(payload));
                    }

                }
            }.start();
        }
    }

    public Event[] customerMocking()
    {
        if (PluginEventIds.eventList.size() == 0)
            return null;
        Random random = new Random();

        int[] targetsids =
        { 0, 0, 0, 0 };

        targetsids[0] = PluginEventIds.specialIds[0][Math.abs(random.nextInt())
            % 3];
        targetsids[1] = PluginEventIds.specialIds[1][Math.abs(random.nextInt())
            % 2];
        targetsids[2] = PluginEventIds.specialIds[2][Math.abs(random.nextInt())
            % 3];
        targetsids[3] = PluginEventIds.specialIds[3][Math.abs(random.nextInt())
            % 5];

        int srcip = random.nextInt(606437375 - 17498112 + 1) + 17498112;
        int tarip = random.nextInt(606437375 - 17498112 + 1) + 17498112;
        int srcport = random.nextInt(65535);
        int tarport = random.nextInt(65535);

        Event[] results =
        { null, null, null, null };

        for (int i = 0; i < 4; i++)
        {

            //            Map<String, Object> curr = PluginEventIds.specialPairs
            //                .get(targetsids[i]);
            Long sid = Long.valueOf(targetsids[i]);

            Long pluginEventId = PluginEventIds.specialPairs.get(targetsids[i]);

            Event payload = new Event();

            payload.setSrcip((long) srcip);
            payload.setTarip((long) tarip);
            payload.setSrcport(srcport);
            payload.setTarport(tarport);

            payload.setPlugin_collectorid(1l);
            payload.setTime(
                Timestamp.from(Calendar.getInstance().toInstant()).toString());

            payload.setSid(sid);

            payload.setPlugin_id(1001l);
            payload.setPlugin_eventid(pluginEventId);

            results[i] = payload;
        }
        //        System.out.println(new Gson().toJson(results));

        return results;
    }

}
