//Samukelile Jama
// 10 April 2023
//Post clas
//The post has at least a title, name of video clip file and number of likes.

public class Post {

    String title;
    String fileName;
    int likes;
    
    //constructor
    Post(String title, String fileName, int Likes){
        this.title=title;
        this.fileName =fileName;
        this.likes =likes;
    }
    //accessor and mutator methods
   public String getTitle(){
      return title;
   }
   
   public void setTitle(String title){
      this.title=title;
   }
   
   public String getFileName(){
      return fileName;
   }
   
   public void setFileName(String fileName){
      this.fileName=fileName;
   }
   
   public int getLikes(){
      return likes;
   }
   
   public void setLikes(int likes){
      this.likes=likes;
   }
 

    @Override
    public String toString() {
        return "Title: " + title +"\n"+ "Video: " + fileName +"\n"+"Number of Likes: " + likes;
    }

}