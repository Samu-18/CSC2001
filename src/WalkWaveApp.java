import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class WalkWaveApp {
   
     public static void main(String[] args) {
        BinarySearchTree accounts = new BinarySearchTree();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
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
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter the account name: ");
                    String accountName = scanner.nextLine();
                    BinaryTreeNode account = accounts.find(new Account(accountName));

                    if (account != null) {
                        Account account_data = (Account) account.getData();
                        System.out.println("The profile description is: " + account_data.getName()
                                + account_data.getDescription());
                    } else {
                        System.out.println("Account not found.");
                    } break;
                 
                 case 2:
                          BinarySearchTree<Account> BST = new BinarySearchTree<Account>();
                          BST = accounts;
                          BST.inOrder();
                    break;
                
                case 3:
                        System.out.print("Enter the account name: ");
                        accountName = scanner.nextLine();
                        System.out.print("Enter the profile description: ");
                        String description = scanner.nextLine();
                        
                        Account newAccount = new Account(accountName, description);
                        accounts.insert(newAccount);
                        
                        System.out.println("Account created successfully.");
                    break;
                    
               case 4:
                         System.out.print("Enter the account name: ");
                         accountName = scanner.nextLine();
                         
                         account = accounts.find(accountName);
                      if (account != null) {
                         accounts.delete(accountName);
                        System.out.println("Account deleted.");
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                    
             case 5:
                    System.out.println("Enter account Name:");
                    accountName = scanner.nextLine();
                    Account acc = new Account(accountName, null, null);
           
                    Object [] posts = accounts.find(acc).data.Posts.toArray();
                    for (Object post : posts) {                
                       System.out.println(post);
                     }    
                    break;
              case 6:
                     System.out.print("Enter the account name: ");
                    accountName = scanner.nextLine();
                    account = accounts.find(new Account(accountName));

                    // account = accounts.find(accountName);
                    if (account != null) {
                           System.out.print("Enter the title: ");
                           String title = scanner.nextLine();
                           System.out.print("Enter the video filename: ");
                           String filename = scanner.nextLine();
                           System.out.print("Enter the number of likes: ");
                           int likes = scanner.nextInt();
                           
                           Post post = new Post(title, filename, likes);

                           acc = new Account(accountName, null, null);       
                           ArrayList postList = new ArrayList<Post>();

                        if(accounts.find(acc).data.Posts == null){                           
                            postList.add(post);
                        }
                        else{
                            ArrayList posts = accounts.find(acc).data.Posts;
                            postList = posts;
                            postList.add(post);
                     }
                  }
                   break;
                   
                   
               case 7:
                 //getting the data from the csv file using its location that is in a directory of its own
               try(Scanner scanner = new Scanner(System.in)){
                 try
                 {
                 File directory = new File("data/input/names.txt");
                 //System.out.println("Enter fileName: \n");
                 String fileName = directory.getAbsolutePath().toString();
                 FileReader fileReader = new FileReader(fileName);
         
                 try(BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                     String line="";
                     accounts = new BinarySearchTree<Account>(); 
                     while((line = bufferedReader.readLine()) != null){
                         Account theaccount = new Account(line);
                         accounts.insert(theaccount);
                         //System.out.println(line);
                     }
         
                 String action = scanner.nextLine();

                choice = ProcessAction(accounts,action,scanner);

            }
        }
       
        catch(IOException e) {
            e.printStackTrace();
            //TODO handle error
            return;
        }
        //break;
   // default:
        Path currentRelativePath = Paths.get("");
        File directory = new File("data/input/dataset.txt");
        String fileName = directory.getAbsolutePath().toString();
                   
        FileReader fileReader = new FileReader(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line="";
            
            while((line = bufferedReader.readLine()) != null){ 
                String arguments[] = line.split(" ", 2);
                if(arguments[0].equals("Create")){
                    String inputs[] = arguments[1].split(" ", 2);
                    acc = new Account(inputs[0],inputs[1]);
                    accounts.insert(acc);
        
                }
                else if(arguments[0].equals("Add")){
                    String inputs[] = arguments[1].split(" ",4);
                    //System.out.println(arguments[1]);
                    //System.out.println(inputs[3]);
                    int numLikes = Integer.parseInt(inputs[2]);
                    Post post = new Post(inputs[3], inputs[1], numLikes);
                    Account acc = new Account(inputs[0], null, null);
                    String  desc = accounts.find(acc).data.description;

                    ArrayList postList = new ArrayList<Post>();

                    if(accounts.find(acc).data.Posts == null){                           
                        postList.add(post);
                    }
                    else{
                        ArrayList posts = accounts.find(acc).data.Posts;
                        postList = posts;
                        postList.add(post);

                    }
                
                    Account newAcc = new Account(inputs[0], desc, postList);
                    accounts.delete(acc);
                    accounts.insert(newAcc);
                }
                else{
                    System.out.println(arguments[0]);
                }
            }               
            
        }
        catch(Exception e){
            
            System.out.println(e);
        }
        break;
      }
    }
  }   
        
    }                     
                                     
                public static ArrayList GetPosts(BinarySearchTree<Account> accounts, String accountName) {
                    Account acc = new Account(accountName, null, null);
            
                 ArrayList posts = accounts.find(acc).data.Posts;
                 return posts;
    
               
                             

 
                 
                 
                 }
                 
               }
            // }
             
      //      }

