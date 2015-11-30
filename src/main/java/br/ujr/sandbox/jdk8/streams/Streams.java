package br.ujr.sandbox.jdk8.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
	private enum Status {
		OPEN, CLOSED
	};

	private static final class Task {
		private final Status	status;
		private final Integer	points;

		Task(final Status status, final Integer points) {
			this.status = status;
			this.points = points;
		}

		public Integer getPoints() {
			return points;
		}

		public Status getStatus() {
			return status;
		}

		@Override
		public String toString() {
			return String.format("[%s, %d]", status, points);
		}
	}

	public static void main(String[] args) {
		
		System.out.println("*** 1");
		final Collection<Task> tasks = Arrays.asList(
				new Task(Status.OPEN, 1), 
				new Task(Status.OPEN, 2), 
				new Task(Status.CLOSED, 3));
		// Calculate total points of all active tasks using sum()
		final long totalPointsOfOpenTasks = tasks.stream()
				.filter(task -> task.getStatus() == Status.OPEN)
				.mapToInt(Task::getPoints)
				.sum();
		System.out.println("Total points: " + totalPointsOfOpenTasks);
		

		// Calculate total points of all tasks
		final double totalPoints = tasks.stream()
				.parallel()
				.map(task -> task.getPoints()) 
				.reduce(1, Integer::sum);
		System.out.println("Total points (all tasks): " + totalPoints);

		System.out.println("\n*** 2");
		Stream.of("a1", "a4", "a3", "a4")
				.map(s -> s.substring(1))
				.mapToInt(Integer::parseInt)
				.max()
				.ifPresent(System.out::println);
		
		System.out.println("\n*** 3");
		Arrays.asList("a1", "a2", "a3")
			    .stream()
			    .findFirst()
			    .ifPresent(System.out::println);

		System.out.println("\n*** 4");
		Stream.of("a1", "a2", "a3")
			    .findFirst()
			    .ifPresent(System.out::println);

		System.out.println("\n*** 5");
		IntStream.range(1, 4)
	    		.forEach(System.out::println);

		System.out.println("\n*** 6");
		Stream.of(1.0, 2.0, 3.0)
			    .mapToInt(Double::intValue)
			    .mapToObj(i -> "a" + i)
			    .forEach(System.out::println);
		
		System.out.println("\n*** 7");
		Stream.of("d2", "a2", "b1", "b3", "c")
			    .map(s -> {
			        System.out.println("map: " + s);
			        return s.toUpperCase();
			    })
			    .anyMatch(s -> {
			        System.out.println("anyMatch: " + s);
			        return s.startsWith("A");
			    });

	}
}