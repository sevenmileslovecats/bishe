package com.dao;

import com.entity.ChukufenboEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChukufenboVO;
import com.entity.view.ChukufenboView;


/**
 * 出库分拨
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface ChukufenboDao extends BaseMapper<ChukufenboEntity> {
	
	List<ChukufenboVO> selectListVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);
	
	ChukufenboVO selectVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);
	
	List<ChukufenboView> selectListView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

	List<ChukufenboView> selectListView(Pagination page,@Param("ew") Wrapper<ChukufenboEntity> wrapper);

	
	ChukufenboView selectView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);
	

}
