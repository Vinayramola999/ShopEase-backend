package com.zosh.service;

import org.springframework.stereotype.Service;

import com.zosh.exception.ProductException;
import com.zosh.model.Cart;
import com.zosh.model.CartItem;
import com.zosh.model.User;
import com.zosh.request.AddItemRequest;


@Service
public interface CartService {
	
	public Cart createCart(User user);
	public CartItem addCartItem(Long userId,AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);

}
