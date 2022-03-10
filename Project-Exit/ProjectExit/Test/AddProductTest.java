/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.ProductModel;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class AddProductTest {
    
    public AddProductTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOrderList method, of class AddProduct.
     */
    @Test
    public void testGetOrderList() {
        System.out.println("getOrderList");
        AddProduct instance = new AddProduct();
        ArrayList<ProductModel> expResult = null;
        ArrayList<ProductModel> result = instance.getOrderList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowProducts method, of class AddProduct.
     */
    @Test
    public void testShowProducts() {
        System.out.println("ShowProducts");
        AddProduct instance = new AddProduct();
        instance.ShowProducts();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class AddProduct.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        AddProduct.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
