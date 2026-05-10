package com.dao;

import com.entity.WuzizhongleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.WuzizhongleiVO;
import com.entity.view.WuzizhongleiView;


/**
 * 物资种类 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface WuzizhongleiDao extends BaseMapper<WuzizhongleiEntity> {

	/**
	 * 查询物资种类VO 列表，给需要精简字段的业务场景使用。
	 */
	List<WuzizhongleiVO> selectListVO(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);

	/**
	 * 查询单条物资种类VO 数据。
	 */
	WuzizhongleiVO selectVO(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);

	/**
	 * 查询物资种类视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzizhongleiView> selectListView(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);

	/**
	 * 查询物资种类视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzizhongleiView> selectListView(Pagination page,@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);


	/**
	 * 查询单条物资种类视图数据。
	 */
	WuzizhongleiView selectView(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);


}
