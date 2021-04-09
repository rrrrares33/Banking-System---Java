# Banking System - Java
 
Banking system implemented in Java for my Advanced Object Oriented Programming University class.

The Main class only calls the Service class for an infinite number of times, until user decides to exit.

The following classes are implemented: 
<ul>
 <li><b>Account</b> : Credit_Acc, Debit_Acc, Saving_Acc </li>
 <li><b>Transaction</b> : Tran_Customers, Tran_Bank_Credit </li>
 <li>Customer</li>
 <li>Bank</li>
 <li>Service</li>
 <li>Main</li>
</ul>

The Service class contains the following atributes:
<ol>
 <li>Bank Bank  -->   Contains bank information and locations of branches.</li>
 <li>List<Customer> customers  -->  Contains all of the customers that are registered inside the bank system.</li>
 <li>Set<Transaction> transaction_history  -->  Contains all of the transactions ever made between two customers or any big credit request ever made.</li>
</ol>

The Service class implements the following methods (which can be accesed though a menu that appears when the application starts):
<ul>
 <li>Close system.</li>
 <li>Create a new customer. </li>
 <li>Delete a customer.</li>
 <li>Show bank details.</li>
 <li>Add a new location or address for a bank branch.</li>
 <li>Add an account to a customer.</li>
 <li>Delete an account from an customer.</li>
 <li>Add money to an account.</li>
 <li>Withdraw money from an account.</li>
 <li>Make a transaction between two customers.</li>
 <li>Make a credit.</li>
 <li>Display all account of a customer.</li>
 <li>Display all customers.</li>
 <li>Display all transactions ever made.</li>
</ul>
