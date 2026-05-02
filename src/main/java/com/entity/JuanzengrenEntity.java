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
 * 捐赠人
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
@TableName("juanzengren")
@Data
public class JuanzengrenEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JuanzengrenEntity() {
		
	}
	
	public JuanzengrenEntity(T t) {
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
	 * 账号
	 */
	private String zhanghao;
	
	/**
	 * 姓名
	 */
	private String xingming;
	
	/**
	 * 密码
	 */
	private String mima;
	
	/**
	 * 性别
	 */
	private String xingbie;
	
	/**
	 * 联系方式
	 */
	private String lianxifangshi;
	
	/**
	 * 头像
	 */
	private String touxiang;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
