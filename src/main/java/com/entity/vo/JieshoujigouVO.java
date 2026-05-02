package com.entity.vo;

import com.entity.JieshoujigouEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 接收机构
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
public class JieshoujigouVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 机构名称
	 */
	
	private String jigoumingcheng;
		
	/**
	 * 密码
	 */
	
	private String mima;
		
	/**
	 * 区域
	 */
	
	private String quyu;
		
	/**
	 * 机构简介
	 */
	
	private String jigoujianjie;
		
	/**
	 * 机构地址
	 */
	
	private String jigoudizhi;
		
	/**
	 * 营业执照
	 */
	
	private String yingyezhizhao;
		
	/**
	 * 机构登记证书
	 */
	
	private String jigoudengjizhengshu;
		
	/**
	 * 联系方式
	 */
	
	private String lianxifangshi;
		
	/**
	 * 头像
	 */
	
	private String touxiang;
		
	/**
	 * 是否审核
	 */
	
	private String sfsh;
		
	/**
	 * 审核回复
	 */
	
	private String shhf;
		
	/**
	 * 状态
	 */
	
	private Integer status;
				
	
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
	 * 设置：密码
	 */
	 
	public void setMima(String mima) {
		this.mima = mima;
	}
	
	/**
	 * 获取：密码
	 */
	public String getMima() {
		return mima;
	}
				
	
	/**
	 * 设置：区域
	 */
	 
	public void setQuyu(String quyu) {
		this.quyu = quyu;
	}
	
	/**
	 * 获取：区域
	 */
	public String getQuyu() {
		return quyu;
	}
				
	
	/**
	 * 设置：机构简介
	 */
	 
	public void setJigoujianjie(String jigoujianjie) {
		this.jigoujianjie = jigoujianjie;
	}
	
	/**
	 * 获取：机构简介
	 */
	public String getJigoujianjie() {
		return jigoujianjie;
	}
				
	
	/**
	 * 设置：机构地址
	 */
	 
	public void setJigoudizhi(String jigoudizhi) {
		this.jigoudizhi = jigoudizhi;
	}
	
	/**
	 * 获取：机构地址
	 */
	public String getJigoudizhi() {
		return jigoudizhi;
	}
				
	
	/**
	 * 设置：营业执照
	 */
	 
	public void setYingyezhizhao(String yingyezhizhao) {
		this.yingyezhizhao = yingyezhizhao;
	}
	
	/**
	 * 获取：营业执照
	 */
	public String getYingyezhizhao() {
		return yingyezhizhao;
	}
				
	
	/**
	 * 设置：机构登记证书
	 */
	 
	public void setJigoudengjizhengshu(String jigoudengjizhengshu) {
		this.jigoudengjizhengshu = jigoudengjizhengshu;
	}
	
	/**
	 * 获取：机构登记证书
	 */
	public String getJigoudengjizhengshu() {
		return jigoudengjizhengshu;
	}
				
	
	/**
	 * 设置：联系方式
	 */
	 
	public void setLianxifangshi(String lianxifangshi) {
		this.lianxifangshi = lianxifangshi;
	}
	
	/**
	 * 获取：联系方式
	 */
	public String getLianxifangshi() {
		return lianxifangshi;
	}
				
	
	/**
	 * 设置：头像
	 */
	 
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	
	/**
	 * 获取：头像
	 */
	public String getTouxiang() {
		return touxiang;
	}
				
	
	/**
	 * 设置：是否审核
	 */
	 
	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}
	
	/**
	 * 获取：是否审核
	 */
	public String getSfsh() {
		return sfsh;
	}
				
	
	/**
	 * 设置：审核回复
	 */
	 
	public void setShhf(String shhf) {
		this.shhf = shhf;
	}
	
	/**
	 * 获取：审核回复
	 */
	public String getShhf() {
		return shhf;
	}
				
	
	/**
	 * 设置：状态
	 */
	 
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
			
}
