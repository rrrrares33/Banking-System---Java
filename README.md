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

![System](https://user-images.githubusercontent.com/61795553/114236046-b4ee0880-9989-11eb-8df4-5e901c431623.png)


The Service class contains the following atributes:
<ol>
 <li>Bank Bank  -->   Contains bank information and locations of branches.</li>
 <li>List<Customer> customers  -->  Contains all of the customers that are registered inside the bank system.</li>
 <li>Set<Transaction> transaction_history  -->  Contains all of the transactions ever made between two customers or any big credit request ever made.</li>
</ol>

The Service class implements the following methods (which can be accesed though a menu that appears when the application starts):

![image](https://user-images.githubusercontent.com/61795553/114236146-dcdd6c00-9989-11eb-9698-da096b0c7184.png)

