package br.ujr.sandbox.jdk8.methodreferences;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Car {
	
    public static Car create( final Supplier< Car > supplier ) {
        return supplier.get();
    }              
        
    public static void collide( final Car car ) {
        System.out.println( "Collided " + car.toString() );
    }
        
    public void follow( final Car another ) {
        System.out.println( "Following the " + another.toString() );
    }
        
    public void repair() {   
        System.out.println( "Repaired " + this.toString() );
    }
    
    public static void main(String[] args) {
    	final Car car1 = Car.create( Car::new );
    	final Car car2 = Car.create( Car::new );
    	final List< Car > cars = Arrays.asList( car1, car2 );
    	
    	cars.forEach(Car::collide); // or
	}
}