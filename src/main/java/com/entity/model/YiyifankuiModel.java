package com.entity.model;

import com.entity.YiyifankuiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 异议反馈
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public class YiyifankuiModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 佐证图片
	 */
	
	private String zuozhengtupian;
		
	/**
	 * 佐证材料
	 */
	
	private String zuozhengcailiao;
		
	/**
	 * 提交时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date tijiaoshijian;
		
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
	 * 出库单
	 */
	
	private String chukudan;
		
	/**
	 * 是否审核
	 */
	
	private String sfsh;
		
	/**
	 * 审核回复
	 */
	
	private String shhf;
				
	
	/**
	 * 设置：佐证图片
	 */
	 
	public void setZuozhengtupian(String zuozhengtupian) {
		this.zuozhengtupian = zuozhengtupian;
	}
	
	/**
	 * 获取：佐证图片
	 */
	public String getZuozhengtupian() {
		return zuozhengtupian;
	}
				
	
	/**
	 * 设置：佐证材料
	 */
	 
	public void setZuozhengcailiao(String zuozhengcailiao) {
		this.zuozhengcailiao = zuozhengcailiao;
	}
	
	/**
	 * 获取：佐证材料
	 */
	public String getZuozhengcailiao() {
		return zuozhengcailiao;
	}
				
	
	/**
	 * 设置：提交时间
	 */
	 
	public void setTijiaoshijian(Date tijiaoshijian) {
		this.tijiaoshijian = tijiaoshijian;
	}
	
	/**
	 * 获取：提交时间
	 */
	public Date getTijiaoshijian() {
		return tijiaoshijian;
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
