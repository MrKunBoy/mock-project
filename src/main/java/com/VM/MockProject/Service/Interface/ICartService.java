package com.VM.MockProject.Service.Interface;

import com.VM.MockProject.Entity.Cart;
import com.VM.MockProject.Entity.CartItem;

import java.util.Optional;

public interface ICartService {

    Optional<Cart> getCartByUserId(Integer userId);

    public Cart saveCart(Cart cart);

    public Cart addToCart(Integer userId, CartItem newCartItem);

    public Cart updateCartItem(Integer userId, CartItem updatedCartItem);

    public void removeCartItem(Integer userId, Long cartItemId);

    public void clearCart(Integer userId);
}
