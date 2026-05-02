package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JieshoujigouEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JieshoujigouVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JieshoujigouView;


/**
 * 接收机构
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
public interface JieshoujigouService extends IService<JieshoujigouEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JieshoujigouVO> selectListVO(Wrapper<JieshoujigouEntity> wrapper);
   	
   	JieshoujigouVO selectVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);
   	
   	List<JieshoujigouView> selectListView(Wrapper<JieshoujigouEntity> wrapper);
   	
   	JieshoujigouView selectView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JieshoujigouEntity> wrapper);

   	

}

