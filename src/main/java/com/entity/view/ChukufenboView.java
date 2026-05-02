package com.entity.view;

import com.entity.ChukufenboEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 出库分拨
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@TableName("chukufenbo")
public class ChukufenboView  extends ChukufenboEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ChukufenboView(){
	}
 
 	public ChukufenboView(ChukufenboEntity chukufenboEntity){
 	try {
			BeanUtils.copyProperties(this, chukufenboEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
