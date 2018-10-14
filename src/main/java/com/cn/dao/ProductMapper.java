package com.cn.dao;

import com.cn.domain.Product;
import com.cn.domain.ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(String pid);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(String pid);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    /**
     * 根据cid分页查询
     * @param cid
     * @param start
     * @param size
     * @return
     */
    List<Product> selectProductByCid(@Param("cid")String cid,@Param("start")Integer start,@Param("size")Integer size);
}