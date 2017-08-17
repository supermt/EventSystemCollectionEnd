/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: KafkaConsumer.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.loop 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月16日 上午12:11:06 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.loop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.nudt681.EventProduceLoop.config.ChannelScalars;
import cn.nudt681.EventProduceLoop.model.Event;
import cn.nudt681.EventProduceLoop.utils.Transformer;

/**
 * @ClassName: KafkaConsumer
 * @Description: TODO
 * @author: MT
 * @see http://docs.spring.io/spring-kafka/docs/1.0.0.M1/reference/htmlsingle/
 * @see http://www.cnblogs.com/xiaojf/p/6613559.html
 */
@Component
public class Looper
{
    @Autowired
    private Environment env;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
        "HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(Looper.class);

    private static final int excuteRound = 50;//设置时钟周期，单位为毫秒

    private static final int threshold = 100;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private Transformer transtool;

    //    @Value("${spring.kafka.template.default-topic}")
    //    private String TargetChannel;

    //    @Scheduled(fixedRate = excuteRound)
    public void singleLoop()
    {

        if (MemoryQueue.length() < threshold)
        {
            logger.debug("Haven't reach the threshold");
            return;
        }

        if (!Transformer.mapReady)
        {
            logger.debug("IP Region map is not ready");
            return;
        }

        String payload = MemoryQueue.toJsonAndClear();
        this.template.send(env.getProperty("kafka.channel.output"), //发送到 flume.output 队列中
            String.valueOf(System.currentTimeMillis()), //键值为时间戳
            payload //传送值为时间周期内收集到的数据列表
        );
        logger.info("{} length of payload has been sent at {}",
            payload.length(), dateFormat.format((new Date())));
    }

    @Scheduled(fixedRate = excuteRound)
    public void singleLoopAddArea()
    {

        if (MemoryQueue.length() < threshold)
        {
            logger.debug("Haven't reach the threshold");
            return;
        }
        if (!Transformer.mapReady)
        {
            logger.debug("IP Region map is not ready");
            return;
        }

        List<Event> curList = MemoryQueue.flush();

        List<Long> srcIpList = new ArrayList<>();
        List<Long> tarIpList = new ArrayList<>();
        List<String> srcAreaList = new ArrayList<>();
        List<String> tarAreaList = new ArrayList<>();

        for (Event e : curList)
        {
            srcIpList.add(e.getSrcip());
            tarIpList.add(e.getTarip());
        }
        srcAreaList.addAll(transtool.transIPtoArea(srcIpList));
        tarAreaList.addAll(transtool.transIPtoArea(tarIpList));
        for (int i = 0; i < curList.size() - 1; i++)
        {
            curList.get(i).setSrcarea(srcAreaList.get(i));
            curList.get(i).setTararea(tarAreaList.get(i));
        }

        Gson gsontool = new Gson();

        String payload = gsontool.toJson(curList);
        this.template.send(ChannelScalars.outputChannel, //发送到 flume.output 队列中
            String.valueOf(System.currentTimeMillis()), //键值为时间戳
            payload //传送值为时间周期内收集到的数据列表
        );
        logger.info("{} length of payload has been sent at {}",
            payload.length(), dateFormat.format((new Date())));
    }
}
