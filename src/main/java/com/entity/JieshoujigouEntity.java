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
 * 接收机构
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
@TableName("jieshoujigou")
@Data
public class JieshoujigouEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JieshoujigouEntity() {
		
	}
	
	public JieshoujigouEntity(T t) {
		try {
			BeanUtils.copyProperties(t, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId
	private Long id;
	/**
	 * 机构账号
	 */
	private String jigouzhanghao;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
