package com.dao;

import com.entity.JuanzengrenEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengrenVO;
import com.entity.view.JuanzengrenView;


/**
 * 捐赠人
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
public interface JuanzengrenDao extends BaseMapper<JuanzengrenEntity> {
	
	List<JuanzengrenVO> selectListVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);
	
	JuanzengrenVO selectVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);
	
	List<JuanzengrenView> selectListView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

	List<JuanzengrenView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

	
	JuanzengrenView selectView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);
	

}
