package com.cn.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.domain.Category;
import com.cn.domain.Product;
import com.cn.dto.Cart;
import com.cn.dto.CartItem;
import com.cn.dto.PageBean;
import com.cn.service.ProductService;
import com.cn.utils.EmptyUtils;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/categoryList.action")
	@ResponseBody
	public List<Category> findAllCategory(){
		return productService.findAllCategory();
	}
	
	@RequestMapping("/productListByCid.action")
	public String productListByCid(@RequestParam("cid")String cid,@RequestParam("currentPage")Integer currentPage,Model model) {
		//int currentPage = 1;//当前页
		int currentCount = 12;//每页显示条数
		PageBean<Product> pageBean = productService.getProductListByCid(cid, currentPage, currentCount);
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("cid", cid);
		
		return "product_list";
	}
	
	@RequestMapping("/productInfo.action")
	public String productInfo(String pid,String cid,Integer currentPage,Model model) {
		Product productInfo = productService.getProductInfo(pid, cid);
		model.addAttribute("product", productInfo);
		model.addAttribute("currentPage", currentPage);
		return "product_info";
	}
	
	@RequestMapping("/addProductToCart.action")
	public String addProductToCart(String pid,Integer buyNum,HttpSession session) {
		Product product = productService.getProductInfo(pid,"");
		
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		double subTotal = product.getShopPrice()*buyNum;
		item.setSubTotal(subTotal);
		
		Cart cart = (Cart) session.getAttribute("cart");
		if(EmptyUtils.isEmpty(cart)){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		//判断是否重复商品
		if(cart.getMap().containsKey(pid)){
			CartItem cartItem = cart.getMap().get(pid);
			int oldbuyNum = cartItem.getBuyNum();
			cartItem.setBuyNum(oldbuyNum + buyNum);
			cartItem.setSubTotal((product.getShopPrice())*(oldbuyNum + buyNum));
			cart.setTotal(cart.getTotal()+subTotal);
		}else{
			cart.getMap().put(pid, item);
			double total = cart.getTotal()+subTotal;
			cart.setTotal(total);
		}
		
		return "cart";
	}
	
	@RequestMapping("/deleteCartItem.action")
	public String deleteCartItem(String pid,HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(EmptyUtils.isNotEmpty(cart) && cart.getMap().containsKey(pid)) {
			CartItem cartItem = cart.getMap().get(pid);
			double subTotal = cartItem.getSubTotal();
			cart.setTotal(cart.getTotal()-subTotal);
			//
			cart.getMap().remove(pid);
		}
		return "cart";
	}
	
	@RequestMapping("/clearCart.action")
	public String clearCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if(EmptyUtils.isNotEmpty(cart) && EmptyUtils.isNotEmpty(cart.getMap())) {
			cart.setTotal(0);
			cart.getMap().clear();
		}
		return "cart";
	}
	
}
