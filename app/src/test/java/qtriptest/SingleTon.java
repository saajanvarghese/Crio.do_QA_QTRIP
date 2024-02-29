package qtriptest;

public class SingleTon{

    public static SingleTon st = null;

    private SingleTon(){

    }

    public static SingleTon getInstance(){
        
        if(st == null){
            st = new SingleTon();
        }
        
        
        return st;
       
    }
    
}