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
 * 异议反馈
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
@TableName("yiyifankui")
@Data
public class YiyifankuiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public YiyifankuiEntity() {
		
	}
	
	public YiyifankuiEntity(T t) {
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
	 * 异议反馈
	 */
	private String yiyifankui;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
