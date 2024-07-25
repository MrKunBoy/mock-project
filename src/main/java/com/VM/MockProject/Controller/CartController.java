package com.VM.MockProject.Controller;

import com.VM.MockProject.Entity.Cart;
import com.VM.MockProject.Entity.CartItem;
import com.VM.MockProject.Service.Interface.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value="/api/v1/carts")
@Validated
@CrossOrigin
public class CartController {
    @Autowired
    private ICartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        return cart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Integer userId, @RequestBody CartItem cartItem) {
        Cart cart = cartService.addToCart(userId, cartItem);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItem(@RequestParam Integer userId, @RequestBody CartItem cartItem) {
        Cart cart = cartService.updateCartItem(userId, cartItem);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@RequestParam Integer userId, @PathVariable Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam Integer userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
