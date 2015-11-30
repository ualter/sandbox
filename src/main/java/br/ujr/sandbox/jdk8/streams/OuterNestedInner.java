package br.ujr.sandbox.jdk8.streams;

import java.util.Optional;

public class OuterNestedInner {
	
	
	static class Outer {
		public Outer() {
		}
		public Outer(String s) {
			this.nested = new Nested(s);
		}
	    Nested nested;
	}

	static class Nested {
		public Nested() {
			
		}
		public Nested(String s) {
			this.inner = new Inner(s);
		}
	    Inner inner;
	}

	static class Inner {
		public Inner() {
			
		}
		public Inner(String s) {
			this.foo = s;
		}
	    String foo;
	}
	
	public static void main(String[] args) {
		Optional.of(new Outer())
		    .flatMap(o -> Optional.ofNullable(o.nested))
		    .flatMap(n -> Optional.ofNullable(n.inner))
		    .flatMap(i -> Optional.ofNullable(i.foo))
		    .ifPresent(System.out::println);
		
		Optional.of(new Outer("Ualter"))
		    .flatMap(o -> Optional.ofNullable(o.nested))
		    .flatMap(n -> Optional.ofNullable(n.inner))
		    .flatMap(i -> Optional.ofNullable(i.foo))
		    .ifPresent(System.out::println);
	}

}
