package com.dao;

import com.entity.JuanzengzhengshuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengzhengshuVO;
import com.entity.view.JuanzengzhengshuView;


/**
 * 捐赠证书
 * 
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
public interface JuanzengzhengshuDao extends BaseMapper<JuanzengzhengshuEntity> {
	
	List<JuanzengzhengshuVO> selectListVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);
	
	JuanzengzhengshuVO selectVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);
	
	List<JuanzengzhengshuView> selectListView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

	List<JuanzengzhengshuView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

	
	JuanzengzhengshuView selectView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);
	

}
