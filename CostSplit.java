import java.util.*;

class User {
    String name;
    double balance;

    User(String name) {
        this.name = name;
        this.balance = 0.0;
    }
}

class Expense {
    String title;
    double amount;
    User payer;
    Map<User, Double> split;

    Expense(String title, double amount, User payer, Map<User, Double> split) {
        this.title = title;
        this.amount = amount;
        this.payer = payer;
        this.split = split;
    }
}

class Group {
    String title;
    List<User> users;
    List<Expense> expenses;

    Group(String title) {
        this.title = title;
        this.users = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    void addUser(User user) {
        users.add(user);
        System.out.println("User " + user.name + " added to the group " + this.title);
    }

    void addExpense(String title, double amount, User payer, int splitOption, Scanner scanner) {
        Map<User, Double> splitMap = new HashMap<>();
        switch (splitOption) {
            case 1: // equal split
                double equalShare = amount / users.size();
                for (User user : users) {
                    splitMap.put(user, equalShare);
                    user.balance -= equalShare;
                }
                payer.balance += amount;
                break;

            case 2: // some percentages, rest equal
                double totalPercent = 0.0;
                Map<User, Double> percentages = new HashMap<>();
                System.out.println("Enter percentages for each user (enter -1 if not specified):");
                for (User user : users) {
                    System.out.print(user.name + ": ");
                    double percentage = scanner.nextDouble();
                    if (percentage != -1) {
                        percentages.put(user, percentage);
                        totalPercent += percentage;
                    }
                }
                double remainingAmount = amount * (1 - totalPercent / 100);
                double equalRemainingShare = remainingAmount / (users.size() - percentages.size());

                for (User user : users) {
                    if (percentages.containsKey(user)) {
                        double userAmount = (percentages.get(user) / 100) * amount;
                        splitMap.put(user, userAmount);
                        user.balance -= userAmount;
                    } else {
                        splitMap.put(user, equalRemainingShare);
                        user.balance -= equalRemainingShare;
                    }
                }
                payer.balance += amount;
                break;

            case 3: // all percentages
                System.out.println("Enter percentages for each user:");
                for (User user : users) {
                    System.out.print(user.name + ": ");
                    double percentage = scanner.nextDouble();
                    double userAmount = (percentage / 100) * amount;
                    splitMap.put(user, userAmount);
                    user.balance -= userAmount;
                }
                payer.balance += amount;
                break;

            case 4: // some exact amounts, rest equal
                double totalExact = 0.0;
                Map<User, Double> exactAmounts = new HashMap<>();
                System.out.println("Enter exact amounts for each user (enter -1 if not specified):");
                for (User user : users) {
                    System.out.print(user.name + ": ");
                    double exactAmount = scanner.nextDouble();
                    if (exactAmount != -1) {
                        exactAmounts.put(user, exactAmount);
                        totalExact += exactAmount;
                    }
                }
                remainingAmount = amount - totalExact;
                equalRemainingShare = remainingAmount / (users.size() - exactAmounts.size());

                for (User user : users) {
                    if (exactAmounts.containsKey(user)) {
                        splitMap.put(user, exactAmounts.get(user));
                        user.balance -= exactAmounts.get(user);
                    } else {
                        splitMap.put(user, equalRemainingShare);
                        user.balance -= equalRemainingShare;
                    }
                }
                payer.balance += amount;
                break;

            case 5: // all exact amounts
                System.out.println("Enter exact amounts for each user:");
                for (User user : users) {
                    System.out.print(user.name + ": ");
                    double exactAmount = scanner.nextDouble();
                    splitMap.put(user, exactAmount);
                    user.balance -= exactAmount;
                }
                payer.balance += amount;
                break;

            default:
                System.out.println("Invalid option.");
                return;
        }

        Expense expense = new Expense(title, amount, payer, splitMap);
        expenses.add(expense);
        System.out.println("Expense " + title + " added to the group " + this.title);
    }

    void showBalances() {
        System.out.println("Balances for group " + this.title + ":");
        for (User user : users) {
            System.out.println(user.name + ": " + user.balance);
        }
    }

    void settleDebts() {
        Map<User, Double> oweMap = new HashMap<>();
        Map<User, Double> lendMap = new HashMap<>();

        for (User user : users) {
            if (user.balance < 0) {
                oweMap.put(user, -user.balance);
            } else if (user.balance > 0) {
                lendMap.put(user, user.balance);
            }
        }

        // minimize transactions
        System.out.println("Settling debts for group " + this.title + ":");
        for (User lender : lendMap.keySet()) {
            double amountToSettle = lendMap.get(lender);
            Iterator<Map.Entry<User, Double>> iterator = oweMap.entrySet().iterator();

            while (iterator.hasNext() && amountToSettle > 0) {
                Map.Entry<User, Double> entry = iterator.next();
                User ower = entry.getKey();
                double amountOwed = entry.getValue();

                if (amountOwed <= amountToSettle) {
                    System.out.println(ower.name + " owes " + lender.name + ": " + amountOwed);
                    amountToSettle -= amountOwed;
                    iterator.remove();
                } else {
                    System.out.println(ower.name + " owes " + lender.name + ": " + amountToSettle);
                    oweMap.put(ower, amountOwed - amountToSettle);
                    amountToSettle = 0;
                }
            }
        }
    }
}

public class CostSplit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter group title: ");
        String groupTitle = scanner.nextLine();
        Group group = new Group(groupTitle);

        System.out.print("Enter number of users: ");
        int userCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            System.out.print("Enter name of user " + (i + 1) + ": ");
            String userName = scanner.nextLine();
            User user = new User(userName);
            group.addUser(user);
        }

        System.out.print("Enter number of expenses: ");
        int expenseCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < expenseCount; i++) {
            System.out.print("Enter title of expense " + (i + 1) + ": ");
            String expenseTitle = scanner.nextLine();
            System.out.print("Enter amount for " + expenseTitle + ": ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Who paid this expense?");
            for (int j = 0; j < userCount; j++) {
                System.out.println((j + 1) + ". " + group.users.get(j).name);
            }
            int payerIndex = scanner.nextInt() - 1;
            User payer = group.users.get(payerIndex);
            scanner.nextLine();

            System.out.println("Choose split option:\n1. Equal\n2. Percentages for some, rest equal\n3. Percentages for all\n4. Exact amounts for some, rest equal\n5. Exact amounts for all");
            int splitOption = scanner.nextInt();
            scanner.nextLine();

            group.addExpense(expenseTitle, amount, payer, splitOption, scanner);
        }

        group.showBalances();
        group.settleDebts();
    }
}