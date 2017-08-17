/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: EventFaker.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.loop 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月16日 上午10:06:07 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.test.faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.nudt681.EventProduceLoop.model.Event;

/**
 * @ClassName: EventFaker
 * @Description: TODO
 * @author: MT
 */
@Component
public class EventFaker
{

    @Autowired
    private Environment env;

    private static final Logger logger = LoggerFactory
        .getLogger(EventFaker.class);
    private static Gson gsontool = new Gson();

    @Autowired
    private KafkaTemplate<String, String> template;

    //    @Scheduled(fixedRate = 20)
    public void sendEmptyEvent()
    {
        String payload = gsontool.toJson(new Event());
        logger.debug("Sending payload {}", payload);
        this.template.send(env.getProperty("kafka.channel.output"), payload);
    }

}
