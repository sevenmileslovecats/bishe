package com.entity.vo;

import com.entity.ChukufenboEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 出库分拨
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public class ChukufenboVO  implements Serializable {
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
	 * 机构账号
	 */
	
	private String jigouzhanghao;
		
	/**
	 * 机构名称
	 */
	
	private String jigoumingcheng;
		
	/**
	 * 物资图片
	 */
	
	private String wuzitupian;
		
	/**
	 * 出库数量
	 */
	
	private Integer wuzishuliang;
		
	/**
	 * 出库单
	 */
	
	private String chukudan;
		
	/**
	 * 出库时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date chukushijian;
		
	/**
	 * 物流状态
	 */
	
	private String wuliuzhuangtai;
				
	
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
	 * 设置：机构账号
	 */
	 
	public void setJigouzhanghao(String jigouzhanghao) {
		this.jigouzhanghao = jigouzhanghao;
	}
	
	/**
	 * 获取：机构账号
	 */
	public String getJigouzhanghao() {
		return jigouzhanghao;
	}
				
	
	/**
	 * 设置：机构名称
	 */
	 
	public void setJigoumingcheng(String jigoumingcheng) {
		this.jigoumingcheng = jigoumingcheng;
	}
	
	/**
	 * 获取：机构名称
	 */
	public String getJigoumingcheng() {
		return jigoumingcheng;
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
	 * 设置：出库数量
	 */
	 
	public void setWuzishuliang(Integer wuzishuliang) {
		this.wuzishuliang = wuzishuliang;
	}
	
	/**
	 * 获取：出库数量
	 */
	public Integer getWuzishuliang() {
		return wuzishuliang;
	}
				
	
	/**
	 * 设置：出库单
	 */
	 
	public void setChukudan(String chukudan) {
		this.chukudan = chukudan;
	}
	
	/**
	 * 获取：出库单
	 */
	public String getChukudan() {
		return chukudan;
	}
				
	
	/**
	 * 设置：出库时间
	 */
	 
	public void setChukushijian(Date chukushijian) {
		this.chukushijian = chukushijian;
	}
	
	/**
	 * 获取：出库时间
	 */
	public Date getChukushijian() {
		return chukushijian;
	}
				
	
	/**
	 * 设置：物流状态
	 */
	 
	public void setWuliuzhuangtai(String wuliuzhuangtai) {
		this.wuliuzhuangtai = wuliuzhuangtai;
	}
	
	/**
	 * 获取：物流状态
	 */
	public String getWuliuzhuangtai() {
		return wuliuzhuangtai;
	}
			
}
