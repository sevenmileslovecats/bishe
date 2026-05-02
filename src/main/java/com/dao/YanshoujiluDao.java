package com.dao;

import com.entity.YanshoujiluEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.YanshoujiluVO;
import com.entity.view.YanshoujiluView;


/**
 * 验收记录
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface YanshoujiluDao extends BaseMapper<YanshoujiluEntity> {
	
	List<YanshoujiluVO> selectListVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);
	
	YanshoujiluVO selectVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);
	
	List<YanshoujiluView> selectListView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

	List<YanshoujiluView> selectListView(Pagination page,@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

	
	YanshoujiluView selectView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);
	

}
