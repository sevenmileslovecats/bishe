package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengrenEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengrenVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengrenView;


/**
 * 捐赠人
 *
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
public interface JuanzengrenService extends IService<JuanzengrenEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JuanzengrenVO> selectListVO(Wrapper<JuanzengrenEntity> wrapper);
   	
   	JuanzengrenVO selectVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);
   	
   	List<JuanzengrenView> selectListView(Wrapper<JuanzengrenEntity> wrapper);
   	
   	JuanzengrenView selectView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengrenEntity> wrapper);

   	

}

