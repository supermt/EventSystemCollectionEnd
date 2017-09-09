/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: KafkaListenerBehaviour.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.config 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月16日 上午2:43:23 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.loop;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.nudt681.EventProduceLoop.config.ChannelScalars;
import cn.nudt681.EventProduceLoop.model.Event;

/**
 * @ClassName: KafkaListenerBehaviour
 * @Description: TODO
 * @author: MT
 */
@Component
public class KafkaListenerBehaviour
{
    private static final Logger logger = LoggerFactory
        .getLogger(KafkaListenerBehaviour.class);

    private Gson gson = new Gson();
//
    @KafkaListener(topics =
    { ChannelScalars.inputChannel })
    public void listen(ConsumerRecord<?, String> cr) throws Exception
    {
        try{
            logger.debug(cr.value());
            MemoryQueue.addOneEvent(gson.fromJson(cr.value(), Event.class));
            logger.debug("received data in size of {}", cr.serializedValueSize());
            logger.debug("received data {}", cr.value());
        }catch(Exception e){
            logger.error("Error Occurred");
        }
    }
}
