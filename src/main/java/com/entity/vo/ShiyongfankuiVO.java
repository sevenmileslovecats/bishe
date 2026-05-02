package com.entity.vo;

import com.entity.ShiyongfankuiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 使用反馈
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public class ShiyongfankuiVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 具体用途
	 */
	
	private String jutiyongtu;
		
	/**
	 * 使用效果
	 */
	
	private String shiyongxiaoguo;
		
	/**
	 * 场景照片
	 */
	
	private String changjingzhaopian;
		
	/**
	 * 反馈时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date fankuishijian;
		
	/**
	 * 申领编号
	 */
	
	private String shenlingbianhao;
		
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
	 * 出库数量
	 */
	
	private Integer wuzishuliang;
		
	/**
	 * 评论数
	 */
	
	private Integer discussnum;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：具体用途
	 */
	 
	public void setJutiyongtu(String jutiyongtu) {
		this.jutiyongtu = jutiyongtu;
	}
	
	/**
	 * 获取：具体用途
	 */
	public String getJutiyongtu() {
		return jutiyongtu;
	}
				
	
	/**
	 * 设置：使用效果
	 */
	 
	public void setShiyongxiaoguo(String shiyongxiaoguo) {
		this.shiyongxiaoguo = shiyongxiaoguo;
	}
	
	/**
	 * 获取：使用效果
	 */
	public String getShiyongxiaoguo() {
		return shiyongxiaoguo;
	}
				
	
	/**
	 * 设置：场景照片
	 */
	 
	public void setChangjingzhaopian(String changjingzhaopian) {
		this.changjingzhaopian = changjingzhaopian;
	}
	
	/**
	 * 获取：场景照片
	 */
	public String getChangjingzhaopian() {
		return changjingzhaopian;
	}
				
	
	/**
	 * 设置：反馈时间
	 */
	 
	public void setFankuishijian(Date fankuishijian) {
		this.fankuishijian = fankuishijian;
	}
	
	/**
	 * 获取：反馈时间
	 */
	public Date getFankuishijian() {
		return fankuishijian;
	}
				
	
	/**
	 * 设置：申领编号
	 */
	 
	public void setShenlingbianhao(String shenlingbianhao) {
		this.shenlingbianhao = shenlingbianhao;
	}
	
	/**
	 * 获取：申领编号
	 */
	public String getShenlingbianhao() {
		return shenlingbianhao;
	}
				
	
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
	 * 设置：评论数
	 */
	 
	public void setDiscussnum(Integer discussnum) {
		this.discussnum = discussnum;
	}
	
	/**
	 * 获取：评论数
	 */
	public Integer getDiscussnum() {
		return discussnum;
	}
				
	
	/**
	 * 设置：收藏数
	 */
	 
	public void setStoreupnum(Integer storeupnum) {
		this.storeupnum = storeupnum;
	}
	
	/**
	 * 获取：收藏数
	 */
	public Integer getStoreupnum() {
		return storeupnum;
	}
			
}
