package com.cn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.dao.CategoryMapper;
import com.cn.dao.ProductMapper;
import com.cn.domain.Category;
import com.cn.domain.CategoryExample;
import com.cn.domain.Product;
import com.cn.domain.ProductExample;
import com.cn.dto.PageBean;

@Service
public class ProductService {
	@Autowired
	private ProductMapper productDao;
	@Autowired
	private CategoryMapper categoryDao;
	
	public List<Category> findAllCategory(){
		CategoryExample example = new CategoryExample();
		return categoryDao.selectByExample(example);
	}
	
	public PageBean<Product> getProductListByCid(String cid, int currentPage, int currentCount) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//1.当前页
		pageBean.setCurrentPage(currentPage);
		//2.每页显示条数
		pageBean.setCurrentCount(currentCount);
		//3.总条数
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		int totalCount = productDao.countByExample(example);
		pageBean.setTotalCount(totalCount);
		//4.总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//5.商品列表
		int index = (currentPage-1)*currentCount;
		List<Product> list = productDao.selectProductByCid(cid,index,currentCount);
		pageBean.setList(list);
		
		return pageBean;
	}
	
	public Product getProductInfo(String pid,String cid) {
		return productDao.selectByPrimaryKey(pid);
	}
	
}
