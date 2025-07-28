import java.util.Scanner;

class MyUserAccount {
    private int pin;
    private double balance;

    public MyUserAccount(int pin, double balance) {
        this.pin = pin;
        this.balance = balance;
    }

    public boolean checkPin(int inputPin) {
        return this.pin == inputPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class MyATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyUserAccount account = new MyUserAccount(718, 50000.0);  // Sample account

        final int MAX_ATTEMPTS = 3;
        int attempts = 0;
        boolean authenticated = false;

        System.out.println("==== Welcome to the ATM ====");

        // Allow up to 3 attempts to enter correct PIN
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Please enter your 4-digit PIN: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a 4-digit number: ");
                sc.next();  // Clear invalid input
            }
            int pinInput = sc.nextInt();

            if (account.checkPin(pinInput)) {
                authenticated = true;
                break;
            } else {
                attempts++;
                int remaining = MAX_ATTEMPTS - attempts;
                if (remaining > 0) {
                    System.out.println("Incorrect PIN. Attempts remaining: " + remaining);
                }
            }
        }

        if (!authenticated) {
            System.out.println("Too many incorrect attempts. Access denied.");
            sc.close();
            return;
        }

        // If authenticated, show the ATM menu
        int choice;
        do {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                sc.next(); // Clear invalid input
            }
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.printf("Your balance: Rs.%.2f\n", account.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: Rs.");
                    double depositAmount = sc.nextDouble();
                    if (depositAmount > 0) {
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Enter a valid amount.");
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: Rs.");
                    double withdrawAmount = sc.nextDouble();
                    if (withdrawAmount > 0) {
                        if (account.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                    } else {
                        System.out.println("Enter a valid amount.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}
