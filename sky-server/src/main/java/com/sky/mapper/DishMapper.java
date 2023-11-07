package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品
     * @Param
     * @Return
     **/
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询菜品
     * @Param
     * @Return
     **/
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @Param
     * @Return
     **/
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);


    /**
     * 根据id删除菜品
     * @Param
     * @Return
     **/
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据ids列表删除菜品
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
