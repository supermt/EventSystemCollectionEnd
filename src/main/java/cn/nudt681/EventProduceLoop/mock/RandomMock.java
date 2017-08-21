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
public class RandomMock
{
    private static final Logger logger = LoggerFactory
        .getLogger(RandomMock.class);

    @Value("${task.mock.randomLoop}")
    private int looptime = 900;

    @Value("${task.mock.randomThread}")
    private int threadCount = 10;

    @Value("${task.mock.random}")
    private boolean toggle = false;

    @Autowired
    private KafkaTemplate<String, String> kafkaChannel;

    @Scheduled(fixedRate = 1000)
    public void sendLoop()
    {
        if (!Transformer.mapReady || !toggle)
            return;

        logger.debug("Producing {} * {} records", looptime, threadCount);

        for (int i = 0; i < threadCount; i++)
        {
            Thread singleThread = new Thread()
            {

                @Override
                public void run()
                {
                    for (int i = 0; i < looptime; i++)
                    {
                        String payload = randomMocking();
                        if (payload == null)
                            continue;
                        kafkaChannel.send(ChannelScalars.inputChannel, payload);
                    }
                }
            };
            singleThread.start();
        }
    }

    public void singleLoop()
    {
        if (!Transformer.mapReady)
        {
            return;
        }
        for (int i = 0; i < threadCount; i++)
        {
            Thread singleThread = new Thread()
            {

                @Override
                public void run()
                {
                    for (int i = 0; i < looptime; i++)
                        randomMocking();
                }
            };
            singleThread.start();
        }
    }

    public String randomMocking()
    {
        if (PluginEventIds.eventList.size() == 0)
            return null;
        Random random = new Random();
        int pointer = random.nextInt();
        pointer = Math.abs(pointer) % PluginEventIds.eventList.size();
        Map<String, Object> curr = PluginEventIds.eventList.get(pointer);
        Long sid = Long.valueOf(curr.get("id").toString());

        Long pluginEventId = Long.valueOf(curr.get("sid").toString());

        int srcip = random.nextInt(606437375 - 17498112 + 1) + 17498112;
        int tarip = random.nextInt(606437375 - 17498112 + 1) + 17498112;
        int srcport = random.nextInt(65535);
        int tarport = random.nextInt(65535);

        Event payload = new Event();

        payload.setSrcip((long) srcip);
        payload.setTarip((long) tarip);
        payload.setSrcport(srcport);
        payload.setTarport(tarport);

        payload.setSid(sid);

        payload.setPlugin_id(1001l);
        payload.setPlugin_eventid(pluginEventId);
        payload.setPlugin_collectorid(1l);
        payload.setTime(
            Timestamp.from(Calendar.getInstance().toInstant()).toString());

        Gson gson = new Gson();
        String result = gson.toJson(payload);

//        System.out.println(result);

        return result;

    }

}
