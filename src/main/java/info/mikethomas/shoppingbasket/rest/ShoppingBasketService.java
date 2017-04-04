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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Mike
 */
@Path("/shoppingbasket")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Api(tags = "Shopping Basket")
public class ShoppingBasketService {

    protected static Map<Integer, Basket> baskets = new HashMap<>();
    protected final Items list = getFromXml(Items.class, "Items.xml");

    @GET
    @Path("/items")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of items", response = Items.class)
    })
    public Response getItems() {
        return Response.status(200).entity(list).build();
    }

    @POST
    @Path("/add/{basket_id}")
    public Response addItem(
            @PathParam("basket_id") int basketId,
            @QueryParam("item") int itemId,
            @QueryParam("quantity") BigInteger quantity) {

        baskets.putIfAbsent(basketId, new Basket());
        Basket basket = baskets.get(basketId);
        Item item = list.getItem().get(itemId);
        item.setQuantity(quantity);
        basket.getItems().add(item);
        return Response.status(200).entity(basket).build();
    }

    @PUT
    @Path("/update/{basket_id}")
    public Response replaceItem(
            @PathParam("basket_id") int basketId,
            @QueryParam("item") int itemId,
            @QueryParam("quantity") BigInteger quantity) {

        baskets.putIfAbsent(basketId, new Basket());
        Basket basket = baskets.get(basketId);
        Item item = list.getItem().get(itemId);
        item.setQuantity(quantity);
        return Response.status(200).entity(basket).build();
    }

    @DELETE
    @Path("/remove/{basket_id}")
    public Response removeItem(
            @PathParam("basket_id") int basketId,
            @QueryParam("item") int itemId) {

        baskets.putIfAbsent(basketId, new Basket());
        Basket basket = baskets.get(basketId);
        Item item = list.getItem().get(itemId);
        basket.getItems().remove(item);
        return Response.status(200).entity(basket).build();
    }

    @GET
    @Path("/contents/{basket_id}")
    public Response getContents(
            @PathParam("basket_id") int basketId) {

        baskets.putIfAbsent(basketId, new Basket());
        Basket basket = baskets.get(basketId);
        return Response.status(200).entity(basket).build();
    }

    @GET
    @Path("/total/{basket_id}")
    public Response getTotal(
            @PathParam("basket_id") int basketId) {

        baskets.putIfAbsent(basketId, new Basket());
        Basket basket = baskets.get(basketId);
        return Response.status(200).entity(basket.getTotal()).build();
    }

    /**
     * <p>Helper method to construct the service classes from XML files.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param file a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T getFromXml(Class<T> type, String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            URL xml = type.getResource(file);
            return type.cast(unmarshaller.unmarshal(xml));
        } catch (JAXBException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
