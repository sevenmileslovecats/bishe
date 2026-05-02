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
 * 捐赠物资
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("juanzengwuzi")
@Data
public class JuanzengwuziEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JuanzengwuziEntity() {
		
	}
	
	public JuanzengwuziEntity(T t) {
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
	 * 捐赠编号
	 */
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
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date youxiaoqi;
	
	/**
	 * 捐赠时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date juanzengshijian;
	
	/**
	 * 验收状态
	 */
	private String yanshouzhuangtai;
	
	/**
	 * 账号
	 */
	private String zhanghao;
	
	/**
	 * 姓名
	 */
	private String xingming;
	
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
