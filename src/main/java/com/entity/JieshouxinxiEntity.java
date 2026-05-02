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
 * 接收信息
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("jieshouxinxi")
@Data
public class JieshouxinxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JieshouxinxiEntity() {
		
	}
	
	public JieshouxinxiEntity(T t) {
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
	 * 签收图片
	 */
	private String qianshoutupian;
	
	/**
	 * 签收时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date qianshoushijian;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
