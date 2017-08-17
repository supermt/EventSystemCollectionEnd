/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: MemoryQueue.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.loop 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月16日 上午9:54:36 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.loop;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.nudt681.EventProduceLoop.model.Event;

/**
 * @ClassName: MemoryQueue
 * @Description: TODO
 * @author: MT
 */
public class MemoryQueue
{
    private static final List<Event> inputList = new ArrayList<>();

    private static final Gson gsontool = new Gson();

    public static void addOneEvent(Event e)
    {
        inputList.add(e);
    }

    public static void remove(Event e)
    {
        inputList.remove(e);
    }

    public static long length()
    {
        return inputList.size();
    }

    public static String toJsonAndClear()
    {
        List<Event> curList = new ArrayList<>();
        curList.addAll(inputList);
        inputList.clear();
        return gsontool.toJson(curList);
    }
    
    public static List<Event> flush(){
        List<Event> curList = new ArrayList<>();
        curList.addAll(inputList);
        inputList.clear();
        return curList;
    }
    
    
}
