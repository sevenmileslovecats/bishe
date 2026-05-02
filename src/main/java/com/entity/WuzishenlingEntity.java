package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 物资申领
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("wuzishenling")
@Data
public class WuzishenlingEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public WuzishenlingEntity() {
		
	}
	
	public WuzishenlingEntity(T t) {
		try {
			BeanUtils.copyProperties(t, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
	private Long id;
	private String juanzengbianhao;
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
