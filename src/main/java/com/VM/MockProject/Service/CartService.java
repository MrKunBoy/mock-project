package com.VM.MockProject.Service;

import com.VM.MockProject.Entity.Cart;
import com.VM.MockProject.Entity.CartItem;
import com.VM.MockProject.Repository.IBookRepository;
import com.VM.MockProject.Repository.ICartItemRepository;
import com.VM.MockProject.Repository.ICartRepository;
import com.VM.MockProject.Repository.IUserRepository;
import com.VM.MockProject.Service.Interface.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IUserRepository userRepository;


    @Override
    @Transactional
    public Optional<Cart> getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addToCart(Integer userId, CartItem newCartItem) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
        }
        newCartItem.setCart(cart);
        cart.getItems().add(newCartItem);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateCartItem(Integer userId, CartItem updatedCartItem) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            for (CartItem item : cart.getItems()) {
                if (item.getId().equals(updatedCartItem.getId())) {
                    item.setQuantity(updatedCartItem.getQuantity());
                    item.setPrice(updatedCartItem.getPrice());
                    return cartRepository.save(cart);
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void removeCartItem(Integer userId, Long cartItemId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void clearCart(Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }
}
