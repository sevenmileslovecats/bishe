package com.dao;

import com.entity.YiyifankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.YiyifankuiVO;
import com.entity.view.YiyifankuiView;


/**
 * 异议反馈
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface YiyifankuiDao extends BaseMapper<YiyifankuiEntity> {
	
	List<YiyifankuiVO> selectListVO(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);
	
	YiyifankuiVO selectVO(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);
	
	List<YiyifankuiView> selectListView(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);

	List<YiyifankuiView> selectListView(Pagination page,@Param("ew") Wrapper<YiyifankuiEntity> wrapper);

	
	YiyifankuiView selectView(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);
	

}
