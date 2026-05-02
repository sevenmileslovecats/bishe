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
 * 物资信息
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("wuzixinxi")
@Data
public class WuzixinxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public WuzixinxiEntity() {
		
	}
	
	public WuzixinxiEntity(T t) {
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
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date baozhiqi;
	
	/**
	 * 存储位置
	 */
	private String cunchuweizhi;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
