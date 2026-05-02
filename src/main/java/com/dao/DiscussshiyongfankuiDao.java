package com.dao;

import com.entity.DiscussshiyongfankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussshiyongfankuiVO;
import com.entity.view.DiscussshiyongfankuiView;


/**
 * 使用反馈评论
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface DiscussshiyongfankuiDao extends BaseMapper<DiscussshiyongfankuiEntity> {
	
	List<DiscussshiyongfankuiVO> selectListVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);
	
	DiscussshiyongfankuiVO selectVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);
	
	List<DiscussshiyongfankuiView> selectListView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

	List<DiscussshiyongfankuiView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

	
	DiscussshiyongfankuiView selectView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);
	

}
