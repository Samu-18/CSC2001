//Samukelile Jama
//10 April 2023
//main class (WeConnectApp)

//Samukelile Jama
//12 April 2023
//Account class
//Account, profile description and list of posts
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class Account implements Comparable<Account> {
    String name;
    String description;
    ArrayList Posts;

    //constructor
    public Account(String name, String description, ArrayList posts) {
        this.name = name;
        this.description = description;
        this.Posts = posts;
    }
   //Overloading the constructor
    public Account(String stringline) {
        this.name = stringline;
        this.description ="";
        this.Posts =new ArrayList<Post>();
   }

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
        this.Posts =new ArrayList<Post>();
    }

   //accessor methods
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }

    public void setName(String name){
        name = name;
    }
    public ArrayList getPosts() {
        return Posts;
    }
    public int getValue(){
          int outcome = 0;
          if (name != null) {
              outcome += name.length() * 100;
          }
          if (description != null) {
              outcome += description.length() * 10;
          }
          if (Posts != null) {
              outcome += Posts.size();
          }
          return outcome;
      }


   /** mutator methods
   **/
    public void setDescription(String description){
        description = description;
    }

    public void setPosts(ArrayList posts){
        Posts = posts;
    }

   /**
    Returns a string representation of all posts in the Posts collection.
 **/
      private String print() {
      // Create a new ArrayList<Post> to store the posts
      ArrayList<Post> Posts = new ArrayList<>();
     // Use a Stream to map each post to its string representation, and concatenate the resulting strings
     return Posts.stream().map(post -> post.toString()).collect(Collectors.joining());
   }

    @Override
    public int compareTo(Account another) {        
        return name.compareTo(another.name);
    }
               

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (Posts == null) {
            if (other.Posts != null)
                return false;
        } else if (!Posts.equals(other.Posts))
            return false;
        return true;
    }

    @Override
    public String toString() {
       // return "Name: " + name + " Description: " + description +"\n" +print();
       return "Name: "+name+"\n";
    }
    

}
