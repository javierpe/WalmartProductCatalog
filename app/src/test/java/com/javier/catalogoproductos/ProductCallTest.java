package com.javier.catalogoproductos;

import com.javier.api.ProductCalls;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProductCallTest {
    @Test
    public void testApi() {
        System.out.println("Test started!");
        ProductCalls.getProduct("00750940180662").subscribe(product -> {
            assertNotNull(product);
            System.out.println("Request success!");
            System.out.println(product.toString());
        }, throwable -> {
            System.out.println("Request failed!");
            fail(throwable.getMessage());
        });
    }
}