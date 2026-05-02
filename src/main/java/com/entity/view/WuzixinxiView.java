package com.entity.view;

import com.entity.WuzixinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 物资信息
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("wuzixinxi")
public class WuzixinxiView  extends WuzixinxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public WuzixinxiView(){
	}
 
 	public WuzixinxiView(WuzixinxiEntity wuzixinxiEntity){
 	try {
			BeanUtils.copyProperties(this, wuzixinxiEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
