package com.dao;

import com.entity.JieshouxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JieshouxinxiVO;
import com.entity.view.JieshouxinxiView;


/**
 * 接收信息
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JieshouxinxiDao extends BaseMapper<JieshouxinxiEntity> {
	
	List<JieshouxinxiVO> selectListVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);
	
	JieshouxinxiVO selectVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);
	
	List<JieshouxinxiView> selectListView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

	List<JieshouxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

	
	JieshouxinxiView selectView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);
	

}
