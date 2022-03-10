/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.PurchaseModel;
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
public class PurchaseTest {
    
    public PurchaseTest() {
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
     * Test of getSum method, of class Purchase.
     */
    @Test
    public void testGetSum() {
        System.out.println("getSum");
        Purchase instance = new Purchase();
        double expResult = 0.0;
        double result = instance.getSum();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidation method, of class Purchase.
     */
    @Test
    public void testGetValidation() {
        System.out.println("getValidation");
        int pidVal = 0;
        Purchase instance = new Purchase();
        boolean expResult = false;
        boolean result = instance.getValidation(pidVal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrderList method, of class Purchase.
     */
    @Test
    public void testGetOrderList() {
        System.out.println("getOrderList");
        Purchase instance = new Purchase();
        ArrayList<PurchaseModel> expResult = null;
        ArrayList<PurchaseModel> result = instance.getOrderList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowPurchases method, of class Purchase.
     */
    @Test
    public void testShowPurchases() {
        System.out.println("ShowPurchases");
        Purchase instance = new Purchase();
        instance.ShowPurchases();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowVals method, of class Purchase.
     */
    @Test
    public void testShowVals() {
        System.out.println("ShowVals");
        Purchase instance = new Purchase();
        instance.ShowVals();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Purchase.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Purchase.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
