import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WeConnectAppGUI {
    private static Scanner scanner;
    private static BinarySearchTree<Account> accounts;
    private static JFrame frame;
    private static JTextArea outputArea;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        accounts = new BinarySearchTree<Account>();

        try {
            String fileName = new File("data/resources/names.txt").getAbsolutePath();
            FileReader fileReader = new FileReader(fileName);

            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String stringline;

                while ((stringline = bufferedReader.readLine()) != null) {
                    Account account = new Account(stringline);
                    accounts.insert(account);
                }

                createGUI();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void createGUI() {
        // Create the main frame
        frame = new JFrame("WeConnectApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        // Create the buttons
        JButton findProfileButton = new JButton("Find Profile Description");
        JButton listAccountsButton = new JButton("List All Accounts");
        JButton createAccountButton = new JButton("Create Account");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton displayPostsButton = new JButton("Display All Posts");
        JButton addPostButton = new JButton("Add Post");
        JButton processFileButton = new JButton("Load Actions from File");
        JButton quitButton = new JButton("Quit");

        // Add the buttons to the panel
        buttonPanel.add(findProfileButton);
        buttonPanel.add(listAccountsButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(deleteAccountButton);
        buttonPanel.add(displayPostsButton);
        buttonPanel.add(addPostButton);
        buttonPanel.add(processFileButton);
        buttonPanel.add(quitButton);

        // Add action listeners to the buttons
        findProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountName = getAccountNameFromUser();
                BinaryTreeNode account = accounts.find(new Account(accountName));

                if (account != null) {
                    Account account_data = (Account) account.getData();
                    if (account_data.getDescription() == null) {
                        displayOutput("The profile description is empty.\n");
                    } else {
                        displayOutput("The profile description is: " + account_data.getDescription() + "\n");
                    }
                } else {
                    displayOutput("Account not found.\n");
                }
            }
        });

     /  listAccountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayOutput("\nList of All Accounts\n");
                BinarySearchTree<Account> BST = new BinarySearchTree<Account>();
                BST = accounts;
                long allAcc =BST.inOrder();
                displayOutput(allAcc);
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountName = getAccountNameFromUser();
                String description = getAccountDescriptionFromUser();
                Account account = new Account(accountName, description);
                accounts.insert(account);
                displayOutput("Account " + accountName + " created.\n");
}
});


    deleteAccountButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accountName = getAccountNameFromUser();
            Account account = new Account(accountName);
            boolean deleted = accounts.delete(account);
            if (deleted) {
                displayOutput("Account " + accountName + " deleted.\n");
            } else {
                displayOutput("Account not found.\n");
            }
        }
    });

    displayPostsButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accountName = getAccountNameFromUser();
            BinaryTreeNode account = accounts.find(new Account(accountName));

            if (account != null) {
                Account account_data = (Account) account.getData();
                ArrayList<String> posts = account_data.getPosts();
                if (posts.isEmpty()) {
                    displayOutput("This account has no posts.\n");
                } else {
                    displayOutput("\nPosts for " + accountName + "\n");
                    for (String post : posts) {
                        displayOutput(post + "\n");
                    }
                }
            } else {
                displayOutput("Account not found.\n");
            }
        }
    });

    addPostButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accountName = getAccountNameFromUser();
            BinaryTreeNode account = accounts.find(new Account(accountName));

            if (account != null) {
                Account account_data = (Account) account.getData();
                String post = getPostFromUser();
                account_data.addPost(post);
                displayOutput("Post added to account " + accountName + ".\n");
            } else {
                displayOutput("Account not found.\n");
            }
        }
    });

    processFileButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String fileName = getFileNameFromUser();
            File file = new File(fileName);

            try {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        String command = parts[0];
                        String accountName = parts[1];
                        BinaryTreeNode account = accounts.find(new Account(accountName));
                        if (account != null) {
                            Account account_data = (Account) account.getData();
                            if (Objects.equals(command, "display")) {
                                ArrayList<String> posts = account_data.getPosts();
                                if (posts.isEmpty()) {
                                    displayOutput("This account has no posts.\n");
                                } else {
                                    displayOutput("\nPosts for " + accountName + "\n");
                                    for (String post : posts) {
                                        displayOutput(post + "\n");
                                    }
                                }
                            } else if (Objects.equals(command, "add")) {
                                String post = getPostFromUser();
                                account_data.addPost(post);
                                displayOutput("Post added to account " + accountName + ".\n");
                            }
                        } else {
                            displayOutput("Account " + accountName + " not found.\n");
                        }
                    } else {
                        displayOutput("Invalid command in file.\n");
                    }
                }
            } catch (IOException ex) {
                displayOutput("Error processing file.\n");
            }
        }
    });

    quitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    // Create a panel to hold the output area
    JPanel outputPanel = new JPanel(new BorderLayout());

    // Create the output area
    outputArea = new JTextArea(15, 50);
    outputArea.setEditable(false);

        // Create the input field and label
JTextField inputField = new JTextField(20);
JLabel inputLabel = new JLabel("Enter a value:");

// Add the input field and label to the panel
inputPanel.add(inputLabel);
inputPanel.add(inputField);

// Create the output area and add it to a scroll pane
outputArea = new JTextArea(20, 40);
JScrollPane scrollPane = new JScrollPane(outputArea);

// Add the button panel, input panel, and scroll pane to the main frame
frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

// Display the frame
frame.pack();
frame.setVisible(true);
}

private static String getAccountNameFromUser() {
return JOptionPane.showInputDialog(frame, "Enter the account name:");
}

private static String getAccountDescriptionFromUser() {
return JOptionPane.showInputDialog(frame, "Enter the profile description:");
}

private static String getPostFromUser() {
return JOptionPane.showInputDialog(frame, "Enter the post:");
}

private static String getFileNameFromUser() {
return JOptionPane.showInputDialog(frame, "Enter the name of the file to load:");
}

private static void displayOutput(String output) {
outputArea.append(output);
}

}
