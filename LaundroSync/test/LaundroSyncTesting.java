import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

/**
 * Testing all main methods and components in the LaundroSync Class.
 */
public class LaundroSyncTesting {

    /**
     * Testing setMachines method.
     */

    @Test
    public void testsetMachines_easy() {
        LaundroSync test = new LaundroSync(1, 1);
        int[] expectedLengths = { 1, 1 };

        int[] returnedLengths = test.setMachines(1, 1);

        assertEquals(expectedLengths[0], returnedLengths[0]);
        assertEquals(expectedLengths[1], returnedLengths[1]);
    }

    @Test
    public void testsetMachines_medium() {
        LaundroSync test = new LaundroSync(10, 9);
        int[] expectedLengths = { 10, 9 };

        int[] returnedLengths = test.setMachines(10, 9);

        assertEquals(expectedLengths[0], returnedLengths[0]);
        assertEquals(expectedLengths[1], returnedLengths[1]);
    }

    @Test
    public void testsetMachines_hard() {
        LaundroSync test = new LaundroSync(100, 88);
        int[] expectedLengths = { 100, 88 };

        int[] returnedLengths = test.setMachines(100, 88);

        assertEquals(expectedLengths[0], returnedLengths[0]);
        assertEquals(expectedLengths[1], returnedLengths[1]);
    }

    /**
     * Testing checkMachines method
     */

    @Test
    public void testcheckMachines_easy() {
        LaundroSync test = new LaundroSync(1, 1);
        test.laundryMachines.set(0, true);
        test.dryingMachines.set(0, true);

        int expected1 = 1;
        int returned1 = test.checkMachines("laundry");

        int expected2 = 1;
        int returned2 = test.checkMachines(("drying"));

        assertEquals(expected1, returned1);
        assertEquals(expected2, returned2);
    }

    @Test
    public void testcheckMachines_medium() {
        LaundroSync test = new LaundroSync(10, 9);

        int expected1 = 0;
        for (boolean status : test.laundryMachines) {
            if (status) {
                expected1++;
            }
        }

        int expected2 = 0;
        for (boolean status : test.dryingMachines) {
            if (status) {
                expected2++;
            }
        }

        int returned1 = test.checkMachines("laundry");
        int returned2 = test.checkMachines("drying");

        assertEquals(expected1, returned1);
        assertEquals(expected2, returned2);
    }

    @Test
    public void testcheckMachines_hard() {
        LaundroSync test = new LaundroSync(100, 98);

        int expected1 = 0;
        for (boolean status : test.laundryMachines) {
            if (status) {
                expected1++;
            }
        }

        int expected2 = 0;
        for (boolean status : test.dryingMachines) {
            if (status) {
                expected2++;
            }
        }

        int returned1 = test.checkMachines("laundry");
        int returned2 = test.checkMachines("drying");

        assertEquals(expected1, returned1);
        assertEquals(expected2, returned2);
    }

    /**
     * registerMachine method not tested as it utilizes similar components to
     * checkMachines which has been tested
     */

    /**
     * Tests checkPassword method only two test cases are needed since the
     * process to check is independent of the size of the string.
     */

    @Test
    public void testcheckPassword_true() {
        LaundroSync test = new LaundroSync(1, 1);

        test.users.put("a", "b");

        boolean returned = test.checkPassword("a");

        assertEquals(true, returned);
    }

    @Test
    public void testcheckPassword_false() {
        LaundroSync test = new LaundroSync(1, 1);

        test.users.put("a", "b");

        boolean returned = test.checkPassword("a");

        assertEquals(false, returned);
    }

    /**
     * Tests register login method only 1 test case needed since process of
     * adding user information to map is independent of the size of the string
     */

    @Test
    public void testregisterLogin() {
        LaundroSync test = new LaundroSync(1, 1);

        test.registerLogin("a", "b");

        HashMap<String, String> expected = new HashMap<>();

        expected.put("a", "b");

        assertEquals(expected, test.users);
    }

    /**
     * Notification method not tested as there is a throw catch system in place
     * already in the method
     */

}
