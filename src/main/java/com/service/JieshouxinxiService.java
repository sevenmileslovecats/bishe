package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JieshouxinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JieshouxinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JieshouxinxiView;


/**
 * 接收信息
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JieshouxinxiService extends IService<JieshouxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JieshouxinxiVO> selectListVO(Wrapper<JieshouxinxiEntity> wrapper);
   	
   	JieshouxinxiVO selectVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);
   	
   	List<JieshouxinxiView> selectListView(Wrapper<JieshouxinxiEntity> wrapper);
   	
   	JieshouxinxiView selectView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JieshouxinxiEntity> wrapper);

   	

}

