package com.zosh.service;

import java.util.List;

import com.zosh.exception.ProductException;
import com.zosh.model.Rating;
import com.zosh.model.User;
import com.zosh.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
}
