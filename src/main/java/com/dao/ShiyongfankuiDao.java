package com.dao;

import com.entity.ShiyongfankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ShiyongfankuiVO;
import com.entity.view.ShiyongfankuiView;


/**
 * 使用反馈
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
public interface ShiyongfankuiDao extends BaseMapper<ShiyongfankuiEntity> {
	
	List<ShiyongfankuiVO> selectListVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);
	
	ShiyongfankuiVO selectVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);
	
	List<ShiyongfankuiView> selectListView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

	List<ShiyongfankuiView> selectListView(Pagination page,@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

	
	ShiyongfankuiView selectView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);
	

}
