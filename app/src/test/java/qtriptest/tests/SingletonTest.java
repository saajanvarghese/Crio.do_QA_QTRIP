package qtriptest.tests;

import qtriptest.SingleTon;


public class SingletonTest{

 public static void main(String[] args) {
    SingleTon x = SingleTon.getInstance();
    SingleTon y = SingleTon.getInstance();
    SingleTon z = SingleTon.getInstance();
    System.out.println("Hashcode of x is "
                              + x.hashCode());
    System.out.println("Hashcode of y is "
                              + y.hashCode());
    System.out.println("Hashcode of z is "
                              + z.hashCode());
   
    }
}