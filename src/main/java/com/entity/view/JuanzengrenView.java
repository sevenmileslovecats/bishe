package com.entity.view;

import com.entity.JuanzengrenEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 捐赠人
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
@TableName("juanzengren")
public class JuanzengrenView  extends JuanzengrenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public JuanzengrenView(){
	}
 
 	public JuanzengrenView(JuanzengrenEntity juanzengrenEntity){
 	try {
			BeanUtils.copyProperties(this, juanzengrenEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
