package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.WuzishenlingEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.WuzishenlingVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.WuzishenlingView;


/**
 * 物资申领
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface WuzishenlingService extends IService<WuzishenlingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<WuzishenlingVO> selectListVO(Wrapper<WuzishenlingEntity> wrapper);
   	
   	WuzishenlingVO selectVO(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);
   	
   	List<WuzishenlingView> selectListView(Wrapper<WuzishenlingEntity> wrapper);
   	
   	WuzishenlingView selectView(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);



}

