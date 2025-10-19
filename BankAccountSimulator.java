// Madison Crewse; Bank Account Simulator
import java.util.Scanner;

public class BankAccountSimulator {

    public static void main(String[] args) {
        
        Scanner kb = new Scanner(System.in);

        int account_periods = 0;
        double goal_balance = 0;
        char show_table;
        
        // Program predicts the growth of a bank account based off of compounded interest
        System.out.println("\n\nThis program will predict the "
                + "growth of a bank account due to compounded interest.\n\n");

        // Asking the user for their annual processing charge
        double annual_processing_charge;
        do {
            System.out.print("What is your annual processing charge? : $");
            annual_processing_charge = kb.nextDouble();
            if (annual_processing_charge < 0) {
                System.out.println("Processing charge must be positive."
                        + " Please enter a valid amount.");
            }
        } while (annual_processing_charge < 0);

        // Asking the user for their bank threshold amount
        double bank_threshold_amount;
        do {
            System.out.print("\nWhat is your bank threshold amount? "
                    + "(minimum balance necessary before charges"
                    + " are applied) : $");
            bank_threshold_amount = kb.nextDouble();
            if (bank_threshold_amount < 0) {
                System.out.println("Bank threshold amount must"
                        + " be positive. Please enter"
                        + " a valid amount.");
            }
        } while (bank_threshold_amount < 0);

        // Asking the user for their initial account balance
        double initial_account_balance;
        do {
            System.out.print("\nWhat is your initial account balance? : $");
            initial_account_balance = kb.nextDouble();
            if (initial_account_balance < 0) {
                System.out.println("Initial account balance must be"
                        + " positive. Please enter a valid amount.");
            }
        } while (initial_account_balance < 0);

        // Asking the user for their annual interest rate
        double annual_interest_rate;
        do {
            System.out.print("\nWhat is your annual interest rate?"
                    + " (as a percentage) : %");
            annual_interest_rate = kb.nextDouble();
            if (annual_interest_rate < 0) {
                System.out.println("Interest rate must be positive."
                        + " Please enter a valid amount.");
            }
        } while (annual_interest_rate < 0);

        // Asking the user for their periods of compounded interest
        char interest_compound_type;
        do {
            System.out.print("\nAt which period is your interest "
                    + "compounded (d = daily, m = monthly, q = quarterly) : ");
            interest_compound_type = kb.next().toLowerCase().charAt(0);
            if (interest_compound_type != 'd' && interest_compound_type 
                    != 'm' && interest_compound_type != 'q') {
                System.out.println("Please enter one of the following "
                        + "(d = daily, m = monthly, q = quarterly) : ");
            }
        } while (interest_compound_type != 'd' && 
                interest_compound_type != 'm' && 
                interest_compound_type != 'q');

        // Asking if the user wants to find the account balance or set a goal
        char goal_or_balance;
        do {
            System.out.print("\nWould you like to find your "
                    + "final account balance (b) or set an"
                    + " account goal (g) : ");
            goal_or_balance = kb.next().toLowerCase().charAt(0);
            if (goal_or_balance != 'b' && goal_or_balance != 'g') {
                System.out.println("Please enter one of the following"
                        + " (b = find final account balance,"
                        + " g = set an account goal) : ");
            }
        } while (goal_or_balance != 'b' && goal_or_balance != 'g');
 
        // Finding the final account balance
        if (goal_or_balance == 'b') {
            do {
                System.out.print("\nHow many periods are left"
                        + " in your account balance? : ");
                account_periods = kb.nextInt();
                if (account_periods <= 0) {
                    System.out.println("Number of periods must"
                            + " be positive. Please enter a valid amount.");
                }
            } while (account_periods <= 0);
        } 
        // Setting an account goal
        else {
            do {
                System.out.print("\nWhat is your goal balance? : ");
                goal_balance = kb.nextDouble();
                if (goal_balance <= 0) {
                    System.out.println("Goal balance must be positive."
                            + " Please enter a valid amount.");
                }
            } while (goal_balance <= 0);
        }

        do {
            System.out.print("\nWould you like to display a table (y/n)? : ");
            show_table = kb.next().toLowerCase().charAt(0);
            if (show_table != 'y' && show_table != 'n') {
                System.out.println("Please enter y = yes or n = no.");
            }
        } while (show_table != 'y' && show_table != 'n');

        int time = 0;
        double balance = initial_account_balance;

        // Calculating balance over time based on interest compound type
        int compoundingPeriodsPerYear;
        if (interest_compound_type == 'd') {
            // Daily compounding
            compoundingPeriodsPerYear = 365;
        } else if (interest_compound_type == 'm') {
            // Monthly compounding
            compoundingPeriodsPerYear = 12;
        } else {
           // Quarterly compounding
            compoundingPeriodsPerYear = 4;
        }

        // Calculate the periodic interest rate and periodic processing charge
        double periodic_rate = (annual_interest_rate / 100.0) / 
        		compoundingPeriodsPerYear;
        double periodic_charge = annual_processing_charge / 
        		compoundingPeriodsPerYear;

        // Displaying table header using printf for proper alignment.
        if (show_table == 'y') {
            System.out.printf("\n%-8s%12s\n", "Period", "Balance");
            System.out.printf("%-8d%12.2f\n", time, balance);
        }

        // Calculate balance over time while displaying the table if requested
        int maxIterations = 1000; //safety limit
        while (((goal_or_balance == 'b' && time < account_periods) ||
                (goal_or_balance == 'g' && balance < goal_balance))
        		&& time < maxIterations)
        {
        // Calculate interest
            balance += balance * periodic_rate;

        // Apply processing charge if below threshold 
            if (balance < bank_threshold_amount) {
                balance -= periodic_charge;
            }

            time++;

        // Display table if user requested
            if (show_table == 'y') {
                System.out.printf("%-8d%12.2f\n", time, balance);
            	}
            }

        if (goal_or_balance == 'g' && periodic_rate*balance <= periodic_charge)
        {
        	System.out.println ("\nWarning... With current interest and charges,"
        			+ " your goal may not be possible.");
        }
        // Displaying outputs to user
        if (goal_or_balance == 'b') {
            System.out.println("After " + time + " periods, balance is: " 
        + balance);
            
        } else {
        	System.out.printf("It takes %d periods to reach balance:"
        			+ " $%,.2f%n", time, balance);

        }
    }
}
