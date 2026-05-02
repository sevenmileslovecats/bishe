package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengzhengshuEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengzhengshuVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengzhengshuView;


/**
 * 捐赠证书
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JuanzengzhengshuService extends IService<JuanzengzhengshuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JuanzengzhengshuVO> selectListVO(Wrapper<JuanzengzhengshuEntity> wrapper);
   	
   	JuanzengzhengshuVO selectVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);
   	
   	List<JuanzengzhengshuView> selectListView(Wrapper<JuanzengzhengshuEntity> wrapper);
   	
   	JuanzengzhengshuView selectView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengzhengshuEntity> wrapper);

   	

}

