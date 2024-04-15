import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import components.utilities.Reporter;

/**
 * LaundroSync Class provides a software product to optimize the laundry process
 * for users by tracking a laundry room's use, finding optimal times, and
 * keeping track of users' laundry.
 */
public class LaundroSync {

    /**
     * HashMap containing the username and linked password for all registered
     * users.
     */
    private HashMap<String, String> users = new HashMap<>();

    /**
     * ArrayList containing all laundryMachines in a space and their status.
     */
    private ArrayList<Boolean> laundryMachines = new ArrayList<>();

    /**
     * ArrayList containing all drying machines in a space and their status.
     */
    private ArrayList<Boolean> dryingMachines = new ArrayList<>();

    /**
     * LaundroSync constructor to set the number of laundry and drying machines.
     *
     * @param nLaundryMachines
     *            Integer describing the number of laundry machines in a room
     *
     * @param nDryingMachines
     *            Integer describing the number of drying machines in a room
     *
     * @requires laundryMachines = <> && dryingMachines = <>
     *
     * @ensures |laundryMachines| = nLaundryMachines && |dryingMachines| =
     *          nDryingMachines && laundryMachines and dryingMachines are filled
     *          with boolean values describing a machine's status
     */
    public LaundroSync(int nLaundryMachines, int nDryingMachines) {
        this.setMachines(nLaundryMachines, nDryingMachines);
    }

    /**
     * Method sets the status of all the machines.
     *
     * @param numLaundryMachines
     *            Integer representing the number of laundry machines in the
     *            room
     *
     * @param numDryingMachines
     *            Integer representing the number of drying machines in the room
     *
     * @requires laundryMachines = <> && dryingMachines = <>
     *
     * @ensures laundryMachines =
     */
    private void setMachines(int numLaundryMachines, int numDryingMachines) {

        //Instantiates random object
        Random gen = new Random();

        //For-loop adds the inputed number of random booleans to laundryMachines
        //list to simulate what a typical laundry machine room might look like
        for (int i = 0; i < numLaundryMachines; i++) {
            if (gen.nextInt(2) == 0) {
                this.laundryMachines.add(false);
            } else {
                this.laundryMachines.add(true);
            }
        }

        //For-loop adds the inputed number of random booleans to dryingMachines
        //list to simulate what a typical drying machine room might look like
        for (int j = 0; j < numDryingMachines; j++) {
            if (gen.nextInt(2) == 0) {
                this.dryingMachines.add(false);
            } else {
                this.dryingMachines.add(true);
            }
        }
    }

    /**
     * Method prints out number of specified machines available to be used.
     *
     * @param machineType
     *            The type of machine to be checked, laundry or drying
     */
    private void checkMachines(String machineType) {

        //Asserts an error if machineType isn't laundry or drying
        Reporter.assertElseFatalError(
                machineType.toLowerCase().equals("laundry")
                        || machineType.toLowerCase().equals("drying"),
                "Invalid Machine Type");

        //Initializes machineCount variable to store count of machines
        int machineCount = 0;

        //Checks machine type and iterates through respective list, and counts
        //number of machines with true status
        if (machineType.toLowerCase().equals("laundry")) {
            for (boolean status : this.laundryMachines) {
                if (status) {
                    machineCount++;
                }
            }
        } else {
            for (boolean status : this.dryingMachines) {
                if (status) {
                    machineCount++;
                }
            }
        }

        //Prints the number of machines for the inputed machine type
        JOptionPane.showMessageDialog(null, "There are " + machineCount + " "
                + machineType.toLowerCase() + " machines open");
    }

    /**
     * Method registers a machine with a user by checking if it is open and then
     * turning its condition from true to false.
     *
     * @param machineType
     */
    private void registerMachine(String machineType) {

        //Asserts error if machineType isn't laundry or drying
        Reporter.assertElseFatalError(
                machineType.toLowerCase().equals("laundry")
                        || machineType.toLowerCase().equals("drying"),
                "Invalid Machine Type");

        //Initializes registered boolean to check if a machine was available for
        //user to register
        boolean registered = false;

        //Checks if there is a machine available for inputed machineType and sets
        //its status to false to represent it being taken by user
        if (machineType.toLowerCase().equals("laundry")) {

            for (int i = 0; i < this.laundryMachines.size(); i++) {
                if (this.laundryMachines.get(i) && !registered) {
                    this.laundryMachines.set(i, false);
                    JOptionPane.showMessageDialog(null,
                            "Laundry Machine #" + (i + 1) + " Registered");
                    registered = true;
                }
            }

            //Prints message if no machine for inputed machineType was available
            if (!registered) {
                JOptionPane.showMessageDialog(null,
                        "No Open Laundry Machine Found");
            }

        } else {

            for (int i = 0; i < this.dryingMachines.size(); i++) {
                if (this.dryingMachines.get(i) && !registered) {
                    this.dryingMachines.set(i, false);
                    JOptionPane.showMessageDialog(null,
                            "Drying Machine #" + (i + 1) + " Registered");
                    registered = true;
                }
            }

            //Prints message if no machine for inputed machineType was available
            if (!registered) {
                JOptionPane.showMessageDialog(null,
                        "No Open Drying Machine Found");
            }
        }
    }

    /**
     * Method simulates a person logging into LaundroSync.
     *
     */
    private void login() {

        //Initializes variable to store the number of attempts user has to login
        final int attemptsGiven = 3;

        //Gives user 3 attempts to login before it all fails
        int i = 0;
        boolean userValid = false;
        while (i < attemptsGiven && !userValid) {
            userValid = this.checkUsername();
            i++;
        }

        //Checks if user was able to login and prints respective message
        if (userValid) {
            JOptionPane.showMessageDialog(null, "Logged in successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Login failed");
        }

    }

    /**
     * Method checks if username is contained the user database.
     *
     * @return inside Boolean representing whether the entered username is in
     *         the user database or not
     *
     */
    private boolean checkUsername() {

        //Gets and stores user inputed username
        String username = JOptionPane.showInputDialog("Enter username:");

        //Checks if user database contains inputed username and if not it prints
        //respective message or goes through password check
        if (this.users.containsKey(username)) {
            return this.checkPassword(username);
        } else {
            JOptionPane.showMessageDialog(null, "Username not found");
            return false;
        }
    }

    /**
     * Method checks if password entered links to username previously entered.
     *
     * @param username
     *            String representing the previously entered username
     *
     * @return passed Boolean representing whether user was able to log in or
     *         not
     */
    private boolean checkPassword(String username) {

        //Gets and stores user inputed password
        String pword = JOptionPane.showInputDialog("Enter your password:");

        //Checks if password for inputed matches up with username and returns
        //respective answer
        if (pword.equals(this.users.get(username))) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password");
            return false;
        }
    }

    /**
     * Method allows user to enter their username and password into the login
     * map.
     *
     * @param username
     *            String being the username of the user
     *
     * @param password
     *            String being the password of the user
     *
     * @requires users.hasKey(username) == false
     *
     * @ensures users.hasKey(username) == true && users.hasValue(password) ==
     *          true
     */
    private void registerLogin(String username, String password) {

        //Adds username and password to login HashMap
        this.users.put(username, password);

    }

    /**
     * Method simulates waiting for a timer to be done and then receiving a
     * notification that laundry is done.
     *
     * @throws AWTException
     *             Condition to be thrown when exceptional condition occurs
     */
    private void notificationSimulation() throws AWTException {

        //Initializes final long to store iteration time in seconds
        final long time = 10L;

        //Initialize final long to store second to nanosecond multiplier
        final long multiplier = 1000000000L;

        //Stores start time
        long start = System.nanoTime();

        //While-loop keeps iterating until specified time is reached
        while (System.nanoTime() - start < time * multiplier) {
            //Doing nothing to pass time
        }

        //Displays notification with specified message
        this.displayNotification("Your Laundry is done");
    }

    /**
     * DisplayNotification method displays the inputed message with no sub text.
     *
     * @param message
     *            The string message to be displayed in a notification
     *
     * @requires message is a string
     *
     * @ensures Notification returned will contain the message inputed
     */
    private void displayNotification(String message) throws AWTException {

        //Instantiate systemTray object for use later
        SystemTray tray = SystemTray.getSystemTray();

        //Creates image object for notification icon
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        //Instantiates trayIcon object to build notification
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");

        //Allows the system to resize the image to fit in the notification if needed
        trayIcon.setImageAutoSize(true);

        //Sets up the toolTip for the next trayIcon demo
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        //Finally displays the full notification with no sub text
        trayIcon.displayMessage(message, "", MessageType.INFO);

    }

    /**
     * DisplayNotification method displays the inputed message with sub text.
     *
     * @param msg
     *            The string message to be displayed in a notification
     *
     * @param subMsg
     *            The string sub message to be displayed underneath the main
     *            message in the notification
     *
     * @requires message is a string
     *
     * @ensures Notification returned will contain the message inputed
     */
    private void displayNotification(String msg, String subMsg)
            throws AWTException {

        //Instantiate systemTray object for use later
        SystemTray tray = SystemTray.getSystemTray();

        //Creates image object for notification icon
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        //Instantiates trayIcon object to build notification
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");

        //Allows the system to resize the image to fit in the notification if needed
        trayIcon.setImageAutoSize(true);

        //Sets up the toolTip for the next trayIcon demo
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        //Finally displays the full notification with no sub text
        trayIcon.displayMessage(msg, subMsg, MessageType.INFO);
    }

    /**
     * Main method.
     *
     * @param args
     *            Command line arguments
     *
     * @throws AWTException
     *             Condition to be thrown when exceptional condition occurs
     */
    public static void main(String[] args) throws AWTException {

        //Instantiates object to be used for demonstration
        LaundroSync test = new LaundroSync(10, 10);
        LaundroSync test2 = new LaundroSync(10, 10);

        //Demonstrates login feature
        test.registerLogin("anand.212", "1234");
        test.login();

        //Shows off ability to allows user to check if a specific machine is open
        test.checkMachines(
                JOptionPane.showInputDialog("Enter Machine Type To Check:"));
        test.registerMachine(
                JOptionPane.showInputDialog("Enter Machine Type To Register:"));

        //Demonstrates ability to simulate timer and notification
        test.notificationSimulation();

    }
}
