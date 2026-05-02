package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShiyongfankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ShiyongfankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ShiyongfankuiView;


/**
 * 使用反馈
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface ShiyongfankuiService extends IService<ShiyongfankuiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ShiyongfankuiVO> selectListVO(Wrapper<ShiyongfankuiEntity> wrapper);
   	
   	ShiyongfankuiVO selectVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);
   	
   	List<ShiyongfankuiView> selectListView(Wrapper<ShiyongfankuiEntity> wrapper);
   	
   	ShiyongfankuiView selectView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ShiyongfankuiEntity> wrapper);

   	

}

