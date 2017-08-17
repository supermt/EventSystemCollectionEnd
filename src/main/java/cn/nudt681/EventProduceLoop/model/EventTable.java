/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: Event.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.model 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月15日 下午3:49:25 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: Event
 * @Description: TODO
 * @author: MT
 */
@Entity
@Table(name = "event")
public class EventTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;

    private Long plugin_id;

    private Long plugin_sid;

    private Long collect_id;

    private Long src_id;

    //    @Transient
    private String src_area;

    private Long dst_id;

    //    @Transient
    private String dst_area;

    private Integer src_port;

    private Integer dst_port;

    private Timestamp time_s;

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getPlugin_id()
    {
        return plugin_id;
    }

    public void setPlugin_id(Long plugin_id)
    {
        this.plugin_id = plugin_id;
    }

    public Long getPlugin_sid()
    {
        return plugin_sid;
    }

    public void setPlugin_sid(Long plugin_sid)
    {
        this.plugin_sid = plugin_sid;
    }

    public Long getCollect_id()
    {
        return collect_id;
    }

    public void setCollect_id(Long collect_id)
    {
        this.collect_id = collect_id;
    }

    public Long getSrc_id()
    {
        return src_id;
    }

    public void setSrc_id(Long src_id)
    {
        this.src_id = src_id;
    }

    public String getSrc_area()
    {
        return src_area;
    }

    public void setSrc_area(String src_area)
    {
        this.src_area = src_area;
    }

    public Long getDst_id()
    {
        return dst_id;
    }

    public void setDst_id(Long dst_id)
    {
        this.dst_id = dst_id;
    }

    public String getDst_area()
    {
        return dst_area;
    }

    public void setDst_area(String dst_area)
    {
        this.dst_area = dst_area;
    }

    public Integer getSrc_port()
    {
        return src_port;
    }

    public void setSrc_port(Integer src_port)
    {
        this.src_port = src_port;
    }

    public Integer getDst_port()
    {
        return dst_port;
    }

    public void setDst_port(Integer dst_port)
    {
        this.dst_port = dst_port;
    }

    public Timestamp getTime_s()
    {
        return time_s;
    }

    public void setTime_s(Timestamp time_s)
    {
        this.time_s = time_s;
    }

}
