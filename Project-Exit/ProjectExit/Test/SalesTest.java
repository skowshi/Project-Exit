/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.SalesModel;
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
public class SalesTest {
    
    public SalesTest() {
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
     * Test of getOrderList method, of class Sales.
     */
    @Test
    public void testGetOrderList() {
        System.out.println("getOrderList");
        Sales instance = new Sales();
        ArrayList<SalesModel> expResult = null;
        ArrayList<SalesModel> result = instance.getOrderList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowReviewSales method, of class Sales.
     */
    @Test
    public void testShowReviewSales() {
        System.out.println("ShowReviewSales");
        Sales instance = new Sales();
        instance.ShowReviewSales();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowSales method, of class Sales.
     */
    @Test
    public void testShowSales() {
        System.out.println("ShowSales");
        Sales instance = new Sales();
        instance.ShowSales();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidation method, of class Sales.
     */
    @Test
    public void testGetValidation() {
        System.out.println("getValidation");
        int soNum = 0;
        Sales instance = new Sales();
        boolean expResult = false;
        boolean result = instance.getValidation(soNum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalQuantity method, of class Sales.
     */
    @Test
    public void testGetTotalQuantity() {
        System.out.println("getTotalQuantity");
        Sales instance = new Sales();
        int expResult = 0;
        int result = instance.getTotalQuantity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalAmount method, of class Sales.
     */
    @Test
    public void testGetTotalAmount() {
        System.out.println("getTotalAmount");
        Sales instance = new Sales();
        int expResult = 0;
        int result = instance.getTotalAmount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillreps method, of class Sales.
     */
    @Test
    public void testFillreps() {
        System.out.println("fillreps");
        Sales instance = new Sales();
        instance.fillreps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillReviewreps method, of class Sales.
     */
    @Test
    public void testFillReviewreps() {
        System.out.println("fillReviewreps");
        Sales instance = new Sales();
        instance.fillReviewreps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillViewreps method, of class Sales.
     */
    @Test
    public void testFillViewreps() {
        System.out.println("fillViewreps");
        Sales instance = new Sales();
        instance.fillViewreps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillViewmans method, of class Sales.
     */
    @Test
    public void testFillViewmans() {
        System.out.println("fillViewmans");
        Sales instance = new Sales();
        instance.fillViewmans();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Sales.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Sales.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
