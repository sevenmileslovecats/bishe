package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChukufenboEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ChukufenboVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ChukufenboView;


/**
 * 出库分拨
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface ChukufenboService extends IService<ChukufenboEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ChukufenboVO> selectListVO(Wrapper<ChukufenboEntity> wrapper);
   	
   	ChukufenboVO selectVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);
   	
   	List<ChukufenboView> selectListView(Wrapper<ChukufenboEntity> wrapper);
   	
   	ChukufenboView selectView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ChukufenboEntity> wrapper);

   	

}

