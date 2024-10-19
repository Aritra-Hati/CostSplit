# 💸 **CostSplit**  
_A Group Expense Sharing and Debt Settling System_

## 📝 **Overview**

**CostSplit** is a Java-based application that simplifies group expense management. Whether you are on a trip with friends or sharing costs in a household, this tool helps you manage expenses, split costs fairly, and settle debts seamlessly.

## ✨ **Features**

- 👥 **Create and Manage Groups**: Add users to your group.
- 💵 **Expense Tracking**: Record expenses with flexible split options.
- ⚖️ **Split Expenses**:
  1. Equal division among users.
  2. Custom percentages for some users; the rest is split equally.
  3. Percentage-based split for all users.
  4. Exact amounts for some users, rest equally divided.
  5. Exact amounts for all users.
- 📊 **Balance Calculation**: Automatically calculate how much each user owes or is owed.
- 🔄 **Debt Settlement**: Simplifies complex debts by minimizing the number of transactions between users.
- 🛠 **Interactive Console**: Use a user-friendly CLI to enter users, expenses, and settle balances.

## 🛠️ **Usage**

1. **Group Creation**:  
   Start by entering the group title and adding users.

2. **Adding Expenses**:  
   Record an expense by specifying:
   - Title
   - Amount
   - Who paid
   - How to split the expense (choose from 5 flexible split options)

3. **View Balances**:  
   Check how much each user owes or is owed in the group.

4. **Settle Debts**:  
   Minimize transactions and easily settle up between users.

## 🖥 **Code Example**

Here’s a snippet from the program:
```java
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
        // Additional split options follow...
    }
    Expense expense = new Expense(title, amount, payer, splitMap);
    expenses.add(expense);
    System.out.println("Expense " + title + " added to the group " + this.title);
}
```
## 🚀 **Getting Started**

### Prerequisites
- **Java Development Kit (JDK)** 8 or above
- A terminal or IDE with support for Java

### Running the Application
1. Clone the repository:  
   ```bash
   git clone https://github.com/yourusername/CostSplit.git
   ```
2. Compile the Java program:  
   ```bash
   javac CostSplit.java
   ```
3. Run the application:  
   ```bash
   java CostSplit
   ```

## 🤝 **Contributing**

Feel free to fork this repository and submit pull requests. Contributions are welcome!