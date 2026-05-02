package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussshiyongfankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussshiyongfankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussshiyongfankuiView;


/**
 * 使用反馈评论
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface DiscussshiyongfankuiService extends IService<DiscussshiyongfankuiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussshiyongfankuiVO> selectListVO(Wrapper<DiscussshiyongfankuiEntity> wrapper);
   	
   	DiscussshiyongfankuiVO selectVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);
   	
   	List<DiscussshiyongfankuiView> selectListView(Wrapper<DiscussshiyongfankuiEntity> wrapper);
   	
   	DiscussshiyongfankuiView selectView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussshiyongfankuiEntity> wrapper);

   	

}

