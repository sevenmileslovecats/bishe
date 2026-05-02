package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.YiyifankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.YiyifankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.YiyifankuiView;


/**
 * 异议反馈
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface YiyifankuiService extends IService<YiyifankuiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<YiyifankuiVO> selectListVO(Wrapper<YiyifankuiEntity> wrapper);
   	
   	YiyifankuiVO selectVO(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);
   	
   	List<YiyifankuiView> selectListView(Wrapper<YiyifankuiEntity> wrapper);
   	
   	YiyifankuiView selectView(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<YiyifankuiEntity> wrapper);

   	

}

