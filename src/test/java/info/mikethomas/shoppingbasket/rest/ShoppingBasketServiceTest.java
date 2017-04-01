package info.mikethomas.shoppingbasket.rest;

/*-
 * #%L
 * shoppingbasket
 * %%
 * Copyright (C) 2017 Mike Thomas <mikepthomas@outlook.com>
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import info.mikethomas.shoppingbasket.model.Basket;
import info.mikethomas.shoppingbasket.model.Item;
import info.mikethomas.shoppingbasket.model.Items;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Mike
 */
public class ShoppingBasketServiceTest {

    @Before
    public void resetBaskets() {
        ShoppingBasketService.baskets = new HashMap<>();
    }

    /**
     * Test of getItems method, of class ShoppingBasketService.
     */
    @Test
    public void testGetItems() {
        System.out.println("getItems");
        ShoppingBasketService instance = new ShoppingBasketService();
        Response result = instance.getItems();
        assertEquals(true, result.hasEntity());

        Items items = (Items) result.getEntity();
        Item item = items.getItem().get(0);
        assertEquals("Soup", item.getSku());
        item = items.getItem().get(1);
        assertEquals("Bread", item.getSku());
        item = items.getItem().get(2);
        assertEquals("Milk", item.getSku());
        item = items.getItem().get(3);
        assertEquals("Apples", item.getSku());
    }

    /**
     * Test of addItem method, of class ShoppingBasketService.
     */
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        int basketId = 0;
        int itemId = 0;
        int quantity = 1;
        ShoppingBasketService instance = new ShoppingBasketService();
        instance.addItem(basketId, itemId, quantity);
        Basket basket = instance.baskets.get(basketId);
        assertEquals(quantity, basket.getBasket().size());
    }

    /**
     * Test of replaceItem method, of class ShoppingBasketService.
     */
    @Test
    public void testReplaceItem() {
        System.out.println("replaceItem");
        int basketId = 0;
        int itemId = 0;
        int quantity = 1;
        ShoppingBasketService instance = new ShoppingBasketService();
        instance.addItem(basketId, itemId, 2);
        Basket basket = instance.baskets.get(basketId);
        Item item = instance.list.getItem().get(itemId);
        assertEquals(2, basket.getBasket().get(item).intValue());
        instance.replaceItem(basketId, itemId, quantity);
        basket = instance.baskets.get(basketId);
        assertEquals(quantity, basket.getBasket().size());
    }

    /**
     * Test of removeItem method, of class ShoppingBasketService.
     */
    @Test
    public void testRemoveItem() {
        System.out.println("removeItem");
        int basketId = 0;
        int itemId = 0;
        ShoppingBasketService instance = new ShoppingBasketService();
        instance.addItem(basketId, itemId, 1);
        Basket basket = instance.baskets.get(basketId);
        assertEquals(1, basket.getBasket().size());
        instance.removeItem(basketId, itemId);
        basket = instance.baskets.get(basketId);
        assertEquals(0, basket.getBasket().size());
    }

    /**
     * Test of getContents method, of class ShoppingBasketService.
     */
    @Test
    public void testGetContents() {
        System.out.println("getContents");
        int basketId = 0;
        ShoppingBasketService instance = new ShoppingBasketService();
        instance.addItem(basketId, 0, 1);
        Response result = instance.getContents(basketId);
        Basket basket = (Basket) result.getEntity();
        assertEquals(1, basket.getBasket().size());
    }

    /**
     * Test of getTotal method, of class ShoppingBasketService.
     */
    @Test
    public void testGetTotal() {
        System.out.println("getTotal");
        int basketId = 0;
        ShoppingBasketService instance = new ShoppingBasketService();
        instance.addItem(basketId, 0, 1);
        Response result = instance.getTotal(basketId);
        Basket basket = (Basket) result.getEntity();
        assertEquals("0.65", String.valueOf(basket.getTotal()));
    }
}
