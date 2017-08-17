/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: IP2Religon.java 
 * @Prject: EventProduceLoop
 * @Package: cn.nudt681.EventProduceLoop.model 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年8月15日 下午2:46:23 
 * @version: V1.0   
 */
package cn.nudt681.EventProduceLoop.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName: Region
 * @Description: 记录一个地区固定的 IP 起始位置与终止位置，也是数据库中的对应表结构
 * @author: MT
 */
//@Entity
//@Table(name = "ip2regionuseint")
public class Region
{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ipstart")
    private Long startIP;

    @Column(name = "ipstop")
    private Long endIP;

    @Column(name = "country")
    private String regionName;

    @Column(name = "province")
    private String provinceName;

    public Long getStartIP()
    {
        return startIP;
    }

    public void setStartIP(Long startIP)
    {
        this.startIP = startIP;
    }

    public Long getEndIP()
    {
        return endIP;
    }

    public void setEndIP(Long endIP)
    {
        this.endIP = endIP;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

}
