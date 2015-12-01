package br.ujr.sandbox.jdk8.type.inference;

public class TypeInference {

	public static void main(String[] args) {

		final Value<String> value = new Value<>();

		System.out.println( value.getOrDefault("22", Value.defaultValue()) );
		// Not necessary anymore
		//System.out.println( value.getOrDefault("22", Value.<String>defaultValue()) );

	}

}
