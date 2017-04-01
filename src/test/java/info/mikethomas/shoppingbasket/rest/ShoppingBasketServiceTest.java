package info.mikethomas.shoppingbasket.rest;

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
        Basket basket = ShoppingBasketService.baskets.get(basketId);
        instance.addItem(basketId, itemId, quantity);
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
        Basket basket = ShoppingBasketService.baskets.get(basketId);
        instance.addItem(basketId, itemId, 2);
        assertEquals(2, basket.getBasket().size());
        instance.replaceItem(basketId, itemId, quantity);
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
        Basket basket = ShoppingBasketService.baskets.get(basketId);
        instance.addItem(basketId, itemId, 1);
        assertEquals(1, basket.getBasket().size());
        instance.removeItem(basketId, itemId);
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
        Response result = instance.getContents(basketId);
        Basket basket = (Basket) result.getEntity();
        assertEquals(new BigDecimal(0.65), basket.getTotal());
    }
}
