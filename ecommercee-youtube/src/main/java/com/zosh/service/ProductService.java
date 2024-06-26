package com.zosh.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.zosh.exception.ProductException;
import com.zosh.model.Product;
//import com.zosh.repository.CategoryRepository;
//import com.zosh.repository.ProductRepository;
import com.zosh.request.CreateProductRequest;

public interface ProductService {
	
	
	
	public Product createProduct (CreateProductRequest req);
	public String deleteProduct(Long productId)throws ProductException;
	
	public Product updateProduct(Long productId, Product req)throws ProductException;
	public List<Product> getAllProducts();
	public Product findProductById(Long id)throws ProductException;
	public List<Product> searchProduct(String query);
	
	public List<Product> findProductByCategory(String category);
	public Page<Product>getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice,Integer minDiscount,String sort,String Stock,Integer pageNumber,Integer pageSize);
	public List<Product> recentlyAddedProduct();

}
