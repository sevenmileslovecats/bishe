package com.dao;

import com.entity.JieshoujigouEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JieshoujigouVO;
import com.entity.view.JieshoujigouView;


/**
 * 接收机构
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
public interface JieshoujigouDao extends BaseMapper<JieshoujigouEntity> {
	
	List<JieshoujigouVO> selectListVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);
	
	JieshoujigouVO selectVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);
	
	List<JieshoujigouView> selectListView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

	List<JieshoujigouView> selectListView(Pagination page,@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

	
	JieshoujigouView selectView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);
	

}
