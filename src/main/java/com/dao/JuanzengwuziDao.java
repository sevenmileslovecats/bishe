package com.dao;

import com.entity.JuanzengwuziEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengwuziVO;
import com.entity.view.JuanzengwuziView;


/**
 * 捐赠物资
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JuanzengwuziDao extends BaseMapper<JuanzengwuziEntity> {
	
	List<JuanzengwuziVO> selectListVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);
	
	JuanzengwuziVO selectVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);
	
	List<JuanzengwuziView> selectListView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

	List<JuanzengwuziView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

	
	JuanzengwuziView selectView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);



}
