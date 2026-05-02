package com.entity.vo;

import com.entity.WuzishenlingEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 物资申领
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public class WuzishenlingVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	private String juanzengbianhao;
	
	 			
	/**
	 * 物资名称
	 */
	
	private String wuzimingcheng;
		
	/**
	 * 物资种类
	 */
	
	private String wuzizhonglei;
		
	/**
	 * 物资规格
	 */
	
	private String wuziguige;
		
	/**
	 * 物资图片
	 */
	
	private String wuzitupian;
		
	/**
	 * 申领数量
	 */
	
	private Integer shenlingshuliang;
		
	/**
	 * 物资申领表
	 */
	
	private String wuzishenlingbiao;
		
	/**
	 * 申领时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date shenlingshijian;
		
	/**
	 * 出库状态
	 */
	
	private String chukuzhuangtai;
		
	/**
	 * 机构账号
	 */
	
	private String jigouzhanghao;
		
	/**
	 * 机构名称
	 */
	
	private String jigoumingcheng;
		
	/**
	 * 区域
	 */
	
	private String quyu;
		
	/**
	 * 是否审核
	 */
	
	private String sfsh;
		
	/**
	 * 审核回复
	 */
	
	private String shhf;
				
	
	public void setJuanzengbianhao(String juanzengbianhao) {
		this.juanzengbianhao = juanzengbianhao;
	}
	
	public String getJuanzengbianhao() {
		return juanzengbianhao;
	}
				
	
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
	 * 设置：申领数量
	 */
	 
	public void setShenlingshuliang(Integer shenlingshuliang) {
		this.shenlingshuliang = shenlingshuliang;
	}
	
	/**
	 * 获取：申领数量
	 */
	public Integer getShenlingshuliang() {
		return shenlingshuliang;
	}
				
	
	/**
	 * 设置：物资申领表
	 */
	 
	public void setWuzishenlingbiao(String wuzishenlingbiao) {
		this.wuzishenlingbiao = wuzishenlingbiao;
	}
	
	/**
	 * 获取：物资申领表
	 */
	public String getWuzishenlingbiao() {
		return wuzishenlingbiao;
	}
				
	
	/**
	 * 设置：申领时间
	 */
	 
	public void setShenlingshijian(Date shenlingshijian) {
		this.shenlingshijian = shenlingshijian;
	}
	
	/**
	 * 获取：申领时间
	 */
	public Date getShenlingshijian() {
		return shenlingshijian;
	}
				
	
	/**
	 * 设置：出库状态
	 */
	 
	public void setChukuzhuangtai(String chukuzhuangtai) {
		this.chukuzhuangtai = chukuzhuangtai;
	}
	
	/**
	 * 获取：出库状态
	 */
	public String getChukuzhuangtai() {
		return chukuzhuangtai;
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
			
}
