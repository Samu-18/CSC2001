//Samukelile Jama
//10th  April 2023
//Main class (WeConnectApp)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.lang.model.element.Element;
import javax.swing.Action;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class WeConnectApp {
    private static Scanner scanner;
    public static void main(String[] args) {
         scanner = new Scanner(System.in);
         BinarySearchTree<Account> accounts = new BinarySearchTree<Account>();
        

        try {
            String fileName = new File("data/resources/names.txt").getAbsolutePath();
            FileReader readme = new FileReader(fileName);

            try (BufferedReader bufferedReader = new BufferedReader(readme)) {
                String stringline;

                while((stringline = bufferedReader.readLine()) != null){
                    Account account = new Account(stringline);
                    accounts.insert(account);
                }
                while(true){
                    System.out.println("Choose an action from the menu:");
                    System.out.println("1. Find the profile description for a given account");
                    System.out.println("2. List all accounts");
                    System.out.println("3. Create an account");
                    System.out.println("4. Delete an account");
                    System.out.println("5. Display all posts for a single account");
                    System.out.println("6. Add a new post for an account");
                    System.out.println("7. Load a file of actions from disk and process this");
                    System.out.println("8. Quit");
                    
                    System.out.print("Enter your choice: ");                    
                    String choice = scanner.nextLine();
                    System.out.println("");
                   
                    switch (choice) {
                        case "1":
                            findProfileDescription(accounts);
                            break;
                        case "2":
                            listAllAccounts(accounts);
                            break;
                        case "3":
                            createAccount(accounts);
                            break;
                        case "4":
                            deleteAccount(accounts);
                            break;
                        case "5":
                            displayAllPosts(accounts);
                            break;
                        case "6":
                            addNewPostForAccount(accounts,scanner);
                            break;
                        case "7":
                           processFileActions(accounts,scanner);
                            break;
                        case "8":
                            Quit();
                            break;
                        default:
                            System.out.println("Invalid choice, Try again.");
                            break;
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
/**
-takes in a binarysearchtree of Account object and prompt the user to enter the accountname.
-it then searches the binary searchtree for an acount with the accountname and returns its description
-if it exits. If not it returns a statement "Account not found"
**/
    private static void findProfileDescription(BinarySearchTree<Account> accounts) {
        System.out.print("Enter the account name: ");
        String accountName = scanner.nextLine();
        BinaryTreeNode account = accounts.find(new Account(accountName));

        if (account != null) {
            Account account_data = (Account) account.getData();
            if (account_data.getDescription()==null) {
                System.out.println("The profile description is empty.\n");
           }else{
            System.out.println("The profile description is: "+ account_data.getDescription()+"\n");
          }
        } else {
            System.out.println("Account not found.\n");
        }
    }

/**
-takes in a binarysearchtree of Account object and list all of the accpunts in travesal inorder
**/
    private static void listAllAccounts(BinarySearchTree<Account> accounts) {
        System.out.println("\nList of All Accounts\n");
        BinarySearchTree<Account> BST = new BinarySearchTree<Account>();
        BST = accounts;
        BST.inOrder();
     }
/**
-takes in a binarysearchtree of Account object and prompt the user to enter the accountname and 
profile description.
-then it creates a new Account onject with and given name and description and inserts it in the
binarysearchtree.
**/
    private static void createAccount(BinarySearchTree<Account> accounts) {
        System.out.print("Enter the account name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter the profile description: ");
        String Description = scanner.nextLine();
        Account newAccount = new Account(accountName, Description);

        accounts.insert(newAccount);
        System.out.println("Account created successfully.\n");
       }

/**
-prompts the user to enter an accountname, a new account object is created to find the corresponding 
node in the binaryseacg tree which is then deleted. 
-If the account is not found it prints "Account deleted" else its prints "Account not found".
**/
    private static void deleteAccount(BinarySearchTree<Account> accounts) {
       System.out.print("Enter the account name: ");
       String accountName = scanner.nextLine();
       Account acc = (new Account(accountName, ""));
       BinaryTreeNode account = accounts.find(acc);
       accounts.delete(acc);
        if(account != null){
          System.out.println("Account deleted.\n");
       } else {
         System.out.println("Account not found.\n");
       } 
     }
/**
-the user is prompted to enter an account name, a new account object is the created but with null values for the 
description and posts. The object is then used to find the matching node in the binarysearchtree.
-if the account is found it retrives all posts from the account Posts and list them.
**/    
        private static void displayAllPosts(BinarySearchTree<Account> accounts) {
                 Scanner reader = new Scanner(System.in);
                System.out.print("Enter account Name: ");
                String name = reader.nextLine();
                Account acc = new Account(name, null, null);
               
                Object[] posts = accounts.find(acc).data.Posts.toArray();
            
                for (Object post : posts) {                
                    System.out.println(post);                
                }
            }
          
/**
-prompts the user to enter the details of a new post (video description, title, and likes)
-creates a Post object then attempts to add the post to an existing Account in a BinarySearchTree of accounts. 
-If the account exists, the post is added to the account's list of posts; if the account does not exist, an error message is printed and the method returns.
**/  
      private static void addNewPostForAccount(BinarySearchTree<Account> accounts, Scanner reader) {
             System.out.print("Enter account Name: ");
             String name = reader.nextLine();
             System.out.print("Enter Post video description: ");
             String desc = reader.nextLine();
             System.out.print("Enter Post video title: ");
             String title = reader.nextLine();
             int likes= 0;
         
                  while (true) {
                   System.out.print("Enter Post likes: ");
                   //likes = reader.nextInt();
                   if (reader.hasNextInt()) {
                    likes = reader.nextInt();
                    reader.nextLine(); // consume the remaining newline character
                    break;
                   } else {
                    System.out.println("Invalid input, please enter an integer.\n");
                    reader.nextLine(); // consume the invalid input
                   }
                  }
                  
                  Post post = new Post(desc,title, likes);
                  
                  Account acc = new Account(name, null, null);       
                  ArrayList<Post> postList;
                  
                  // Check if account exists in BinarySearchTree before accessing its data
                  BinaryTreeNode<Account> foundNode = accounts.find(acc);
                  if (foundNode != null && foundNode.data.Posts != null){                           
                   ArrayList<Post> posts = foundNode.data.Posts;
                   postList = new ArrayList<Post>(posts);
                   postList.add(post);
                  }
                  else if (foundNode == null) {
                   System.out.println("Sorry, the account " + name + " does not exist.\n");
                   return;
                  }
                  else {
                   postList = new ArrayList<Post>();
                   postList.add(post);
                  }
                  
                  String description = getDescription(accounts, name);         
                  Account foundAcc = accounts.find(acc).data;
                  accounts.delete(foundAcc);
                  acc = new Account(name, description, postList);
                  accounts.insert(acc);
                  System.out.println("Post added successfully to " + name + "'s account.\n");
                  
                  }
         
/**
-reads in a text file of commands "Create", "Add", and "Delete" then processes them. 
- If the command is "Create", a new Account object is created and added to the BinarySearchTree. 
-If the command is "Add", a new Post object is created and added to an existing Account in the BinarySearchTree.
- If the command is "Delete", an existing Account is removed from the BinarySearchTree.
**/                                                  
        private static void processFileActions(BinarySearchTree<Account> accounts, Scanner reader) throws FileNotFoundException {
          Path the_path = Paths.get("");
          File directory = new File("data/resources/dataset.txt");
          String fileName = directory.getAbsolutePath().toString();
          FileReader fileReader = new FileReader(fileName);
      
          try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
              String stringline = "";
              while ((stringline = bufferedReader.readLine()) != null) {
                  System.out.println("Processing line: " + stringline); // print the line being processed
                  String parts[] = stringline.split(" ", 2);
                  if (parts[0].equals("Create")) {
                      String inpt[] = parts[1].split(" ", 2);
                      Account acc = new Account(inpt[0], inpt[1]);
                      accounts.insert(acc);
                  } else if (parts[0].equals("Add")) {
                      String inpt[] = parts[1].split(" ", 4);
                      int NoLikes = 0;
                      try {
                          NoLikes = Integer.parseInt(inpt[3]);
                      } catch (NumberFormatException e) {
                          System.out.println("Invalid input: " + inpt[3] + " is not a valid integer");
                      }
                      Post post = new Post(inpt[1], inpt[2], NoLikes);
                      Account acc = new Account(inpt[0], null, null);
                      ArrayList<Post> postList;
                      if (accounts.find(acc).data.Posts == null) {
                          postList = new ArrayList<Post>();
                          postList.add(post);
                      } else {
                          ArrayList<Post> posts = accounts.find(acc).data.Posts;
                          postList = new ArrayList<Post>(posts);
                          postList.add(post);
                      }
                      String description = getDescription(accounts, inpt[0]);
                      Account foundAcc = accounts.find(acc).data;
                      accounts.delete(foundAcc);
                      acc = new Account(inpt[0], description, postList);
                      accounts.insert(acc);
                  } else if (parts[0].equals("Delete")) {
                      Account acc = new Account(parts[1], null, null);
                      Account foundAcc = accounts.find(acc).data;
                      accounts.delete(foundAcc);
                  }
              }
              System.out.println("\nLoaded successfully\n");
          } catch (IOException e) {
              // Handle the IOException here
              e.printStackTrace();
          }
      }
      
/**
-prompts the user to enter an account name and then retrieves the profile description for that account from the BinarySearchTree.
**/
    private static void getProfileDescription(BinarySearchTree<Account> accounts, Scanner reader) {
        System.out.println("Enter account Name:");
        String name = reader.nextLine();
        Account acc = new Account(name, null, null);
        String description = accounts.find(acc).data.description;
        System.out.println("The profile description is: " + description + "\n");
     }


/**
-prints a message and exits the program
**/
   public static void Quit(){
      System.out.println("Bye!\n");
}
 
/**
etrieves the profile description for a given account name from the BinarySearchTree. 
-It is a helper method used by addNewPostForAccount to update the Account object with the new post
**/  
private static String getDescription(BinarySearchTree<Account> accounts, String name) {
     Account acc = new Account(name, null, null);
    Account foundAcc = accounts.find(acc).data;
    return foundAcc.getDescription();
}
 
}