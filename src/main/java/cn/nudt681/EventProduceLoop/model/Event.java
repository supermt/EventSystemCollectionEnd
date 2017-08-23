/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: Event.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.model 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月16日 下午12:58:00 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.model;

/**
 * @ClassName: Event
 * @Description: TODO
 * @author: MT
 */
public class Event
{
    private String eventid;

    private Long plugin_id;// 确定 1001

    private Long plugin_eventid;

    private Long plugin_collectorid;//所有的都一样

    private Long sid;//类别 ID,
    //每一组中选一个，构成组合，其 ip 对相同，time 相差 10s 左右
    //19522 19912 1961
    //8944 9589
    //370 513 520
    //195 194 246 217 222

    private Long srcip;

    private String srcArea;

    private Long tarip;

    private String tarArea;

    private Integer srcport;

    private Integer tarport;

    private String time;

    public String getSrcarea()
    {
        return srcArea;
    }

    public void setSrcarea(String srcarea)
    {
        this.srcArea = srcarea;
    }

    public String getTararea()
    {
        return tarArea;
    }

    public void setTararea(String tararea)
    {
        this.tarArea = tararea;
    }

    public String getEventid()
    {
        return eventid;
    }

    public void setEventid(String eventid)
    {
        this.eventid = eventid;
    }

    public Long getPlugin_id()
    {
        return plugin_id;
    }

    public void setPlugin_id(Long plugin_id)
    {
        this.plugin_id = plugin_id;
    }

    public Long getPlugin_eventid()
    {
        return plugin_eventid;
    }

    public void setPlugin_eventid(Long plugin_eventid)
    {
        this.plugin_eventid = plugin_eventid;
    }

    public Long getPlugin_collectorid()
    {
        return plugin_collectorid;
    }

    public void setPlugin_collectorid(Long plugin_collectorid)
    {
        this.plugin_collectorid = plugin_collectorid;
    }

    public Long getSid()
    {
        return sid;
    }

    public void setSid(Long sid)
    {
        this.sid = sid;
    }

    public Long getSrcip()
    {
        return srcip;
    }

    public void setSrcip(Long srcip)
    {
        this.srcip = srcip;
    }

    public Long getTarip()
    {
        return tarip;
    }

    public void setTarip(Long tarip)
    {
        this.tarip = tarip;
    }

    public Integer getSrcport()
    {
        return srcport;
    }

    public void setSrcport(Integer srcport)
    {
        this.srcport = srcport;
    }

    public Integer getTarport()
    {
        return tarport;
    }

    public void setTarport(Integer tarport)
    {
        this.tarport = tarport;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

}
