/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.RepModel;
import Models.UserModel;
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
public class CreateAccountTest {
    
    public CreateAccountTest() {
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
     * Test of fillReps method, of class CreateAccount.
     */
    @Test
    public void testFillReps() {
        System.out.println("fillReps");
        CreateAccount instance = new CreateAccount();
        instance.fillReps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillCombo method, of class CreateAccount.
     */
    @Test
    public void testFillCombo() {
        System.out.println("fillCombo");
        CreateAccount instance = new CreateAccount();
        instance.fillCombo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrderList method, of class CreateAccount.
     */
    @Test
    public void testGetOrderList() {
        System.out.println("getOrderList");
        CreateAccount instance = new CreateAccount();
        ArrayList<UserModel> expResult = null;
        ArrayList<UserModel> result = instance.getOrderList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowUsers method, of class CreateAccount.
     */
    @Test
    public void testShowUsers() {
        System.out.println("ShowUsers");
        CreateAccount instance = new CreateAccount();
        instance.ShowUsers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepList method, of class CreateAccount.
     */
    @Test
    public void testGetRepList() {
        System.out.println("getRepList");
        CreateAccount instance = new CreateAccount();
        ArrayList<RepModel> expResult = null;
        ArrayList<RepModel> result = instance.getRepList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ShowReps method, of class CreateAccount.
     */
    @Test
    public void testShowReps() {
        System.out.println("ShowReps");
        CreateAccount instance = new CreateAccount();
        instance.ShowReps();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepID method, of class CreateAccount.
     */
    @Test
    public void testGetRepID() {
        System.out.println("getRepID");
        String name = "";
        CreateAccount instance = new CreateAccount();
        int expResult = 0;
        int result = instance.getRepID(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getManID method, of class CreateAccount.
     */
    @Test
    public void testGetManID() {
        System.out.println("getManID");
        String name = "";
        CreateAccount instance = new CreateAccount();
        int expResult = 0;
        int result = instance.getManID(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CreateAccount.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CreateAccount.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
