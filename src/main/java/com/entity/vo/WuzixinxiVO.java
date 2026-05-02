package com.entity.vo;

import com.entity.WuzixinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 物资信息
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public class WuzixinxiVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	private String juanzengbianhao;
	
	 			
	/**
	 * 物资种类
	 */
	
	private String wuzizhonglei;
		
	/**
	 * 物资规格
	 */
	
	private String wuziguige;
		
	/**
	 * 物资详情
	 */
	
	private String wuzixiangqing;
		
	/**
	 * 物资图片
	 */
	
	private String wuzitupian;
		
	/**
	 * 物资数量
	 */
	
	private Integer wuzishuliang;
		
	/**
	 * 保质期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date baozhiqi;
		
	/**
	 * 存储位置
	 */
	
	private String cunchuweizhi;
				
	
	public void setJuanzengbianhao(String juanzengbianhao) {
		this.juanzengbianhao = juanzengbianhao;
	}
	
	public String getJuanzengbianhao() {
		return juanzengbianhao;
	}
				
	
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
	 * 设置：物资规格
	 */
	 
	public void setWuziguige(String wuziguige) {
		this.wuziguige = wuziguige;
	}
	
	/**
	 * 获取：物资规格
	 */
	public String getWuziguige() {
		return wuziguige;
	}
				
	
	/**
	 * 设置：物资详情
	 */
	 
	public void setWuzixiangqing(String wuzixiangqing) {
		this.wuzixiangqing = wuzixiangqing;
	}
	
	/**
	 * 获取：物资详情
	 */
	public String getWuzixiangqing() {
		return wuzixiangqing;
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
	 * 设置：保质期
	 */
	 
	public void setBaozhiqi(Date baozhiqi) {
		this.baozhiqi = baozhiqi;
	}
	
	/**
	 * 获取：保质期
	 */
	public Date getBaozhiqi() {
		return baozhiqi;
	}
				
	
	/**
	 * 设置：存储位置
	 */
	 
	public void setCunchuweizhi(String cunchuweizhi) {
		this.cunchuweizhi = cunchuweizhi;
	}
	
	/**
	 * 获取：存储位置
	 */
	public String getCunchuweizhi() {
		return cunchuweizhi;
	}
			
}
