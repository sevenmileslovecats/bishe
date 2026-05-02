package com.entity.model;

import com.entity.YanshoujiluEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 验收记录
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public class YanshoujiluModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 物资名称
	 */
	
	private String wuzimingcheng;
		
	/**
	 * 物资种类
	 */
	
	private String wuzizhonglei;
		
	/**
	 * 物资说明
	 */
	
	private String wuzishuoming;
		
	/**
	 * 物资图片
	 */
	
	private String wuzitupian;
		
	/**
	 * 新旧程度
	 */
	
	private String xinjiuchengdu;
		
	/**
	 * 物资数量
	 */
	
	private Integer wuzishuliang;
		
	/**
	 * 物资重量
	 */
	
	private Double wuzizhongliang;
		
	/**
	 * 有效期
	 */
	
	private String youxiaoqi;
		
	/**
	 * 验收结果
	 */
	
	private String yanshoujieguo;
		
	/**
	 * 验收记录
	 */
	
	private String yanshoujilu;
		
	/**
	 * 验收人
	 */
	
	private String yanshouren;
		
	/**
	 * 验收时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date yanshoushijian;
		
	/**
	 * 账号
	 */
	
	private String zhanghao;
		
	/**
	 * 姓名
	 */
	
	private String xingming;
				
	
	/**
	 * 设置：物资名称
	 */
	 
	public void setWuzimingcheng(String wuzimingcheng) {
		this.wuzimingcheng = wuzimingcheng;
	}
	
	/**
	 * 获取：物资名称
	 */
	public String getWuzimingcheng() {
		return wuzimingcheng;
	}
				
	
	/**
	 * 设置：物资种类
	 */
	 
	public void setWuzizhonglei(String wuzizhonglei) {
		this.wuzizhonglei = wuzizhonglei;
	}
	
	/**
	 * 获取：物资种类
	 */
	public String getWuzizhonglei() {
		return wuzizhonglei;
	}
				
	
	/**
	 * 设置：物资说明
	 */
	 
	public void setWuzishuoming(String wuzishuoming) {
		this.wuzishuoming = wuzishuoming;
	}
	
	/**
	 * 获取：物资说明
	 */
	public String getWuzishuoming() {
		return wuzishuoming;
	}
				
	
	/**
	 * 设置：物资图片
	 */
	 
	public void setWuzitupian(String wuzitupian) {
		this.wuzitupian = wuzitupian;
	}
	
	/**
	 * 获取：物资图片
	 */
	public String getWuzitupian() {
		return wuzitupian;
	}
				
	
	/**
	 * 设置：新旧程度
	 */
	 
	public void setXinjiuchengdu(String xinjiuchengdu) {
		this.xinjiuchengdu = xinjiuchengdu;
	}
	
	/**
	 * 获取：新旧程度
	 */
	public String getXinjiuchengdu() {
		return xinjiuchengdu;
	}
				
	
	/**
	 * 设置：物资数量
	 */
	 
	public void setWuzishuliang(Integer wuzishuliang) {
		this.wuzishuliang = wuzishuliang;
	}
	
	/**
	 * 获取：物资数量
	 */
	public Integer getWuzishuliang() {
		return wuzishuliang;
	}
				
	
	/**
	 * 设置：物资重量
	 */
	 
	public void setWuzizhongliang(Double wuzizhongliang) {
		this.wuzizhongliang = wuzizhongliang;
	}
	
	/**
	 * 获取：物资重量
	 */
	public Double getWuzizhongliang() {
		return wuzizhongliang;
	}
				
	
	/**
	 * 设置：有效期
	 */
	 
	public void setYouxiaoqi(String youxiaoqi) {
		this.youxiaoqi = youxiaoqi;
	}
	
	/**
	 * 获取：有效期
	 */
	public String getYouxiaoqi() {
		return youxiaoqi;
	}
				
	
	/**
	 * 设置：验收结果
	 */
	 
	public void setYanshoujieguo(String yanshoujieguo) {
		this.yanshoujieguo = yanshoujieguo;
	}
	
	/**
	 * 获取：验收结果
	 */
	public String getYanshoujieguo() {
		return yanshoujieguo;
	}
				
	
	/**
	 * 设置：验收记录
	 */
	 
	public void setYanshoujilu(String yanshoujilu) {
		this.yanshoujilu = yanshoujilu;
	}
	
	/**
	 * 获取：验收记录
	 */
	public String getYanshoujilu() {
		return yanshoujilu;
	}
				
	
	/**
	 * 设置：验收人
	 */
	 
	public void setYanshouren(String yanshouren) {
		this.yanshouren = yanshouren;
	}
	
	/**
	 * 获取：验收人
	 */
	public String getYanshouren() {
		return yanshouren;
	}
				
	
	/**
	 * 设置：验收时间
	 */
	 
	public void setYanshoushijian(Date yanshoushijian) {
		this.yanshoushijian = yanshoushijian;
	}
	
	/**
	 * 获取：验收时间
	 */
	public Date getYanshoushijian() {
		return yanshoushijian;
	}
				
	
	/**
	 * 设置：账号
	 */
	 
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	
	/**
	 * 获取：账号
	 */
	public String getZhanghao() {
		return zhanghao;
	}
				
	
	/**
	 * 设置：姓名
	 */
	 
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	
	/**
	 * 获取：姓名
	 */
	public String getXingming() {
		return xingming;
	}
			
}
