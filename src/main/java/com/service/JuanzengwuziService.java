package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengwuziEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengwuziVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengwuziView;


/**
 * 捐赠物资
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JuanzengwuziService extends IService<JuanzengwuziEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JuanzengwuziVO> selectListVO(Wrapper<JuanzengwuziEntity> wrapper);
   	
   	JuanzengwuziVO selectVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);
   	
   	List<JuanzengwuziView> selectListView(Wrapper<JuanzengwuziEntity> wrapper);
   	
   	JuanzengwuziView selectView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);



}

