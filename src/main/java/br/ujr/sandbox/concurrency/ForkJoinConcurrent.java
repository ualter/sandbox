package br.ujr.sandbox.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class ForkJoinConcurrent {
	
	public static class Bank extends RecursiveAction {
		
		private int[] customers;
		private int start;
		private int end;
		
		public Bank(int[] customers) {
			this.customers = customers;
			this.start = 0;
			this.end = customers.length;
		}
		public Bank(int[] customers, int start, int end) {
			this.customers = customers;
			this.start = start;
			this.end = end;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.RecursiveAction#compute()
		 */
		@Override
		protected void compute() {
			int lengthQueue =  end - start;
			if ( lengthQueue <= 5 ) {
				for(int i = this.start; i < this.end; i++) {
					System.out.format("Bank served: %d\n", customers[i]);
				}
			} else {
				int half = customers.length / 2;
				Bank left = new Bank(customers,0,half);
				left.fork();
				Bank right = new Bank(customers,half+1,customers.length);
				right.fork();
				
				
				right.join();
				left.join();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		//forkJoinPool.invoke( new Bank( new int[]{2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30}) );
		forkJoinPool.invoke( new Bank( new int[]{1,2,3,4,5,6,7,8,9,10}) );
		
		
		long end = System.currentTimeMillis();
		long t = end - start;
		
		System.out.println( t );
		
	}

}
