package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.YanshoujiluEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.YanshoujiluVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.YanshoujiluView;


/**
 * 验收记录
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface YanshoujiluService extends IService<YanshoujiluEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<YanshoujiluVO> selectListVO(Wrapper<YanshoujiluEntity> wrapper);
   	
   	YanshoujiluVO selectVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);
   	
   	List<YanshoujiluView> selectListView(Wrapper<YanshoujiluEntity> wrapper);
   	
   	YanshoujiluView selectView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<YanshoujiluEntity> wrapper);

   	

}

