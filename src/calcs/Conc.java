package calcs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

import static calcs.ConcurrentUtils.*;

public class Conc {
	private boolean showThreads = false;
	private int minValue = 0;
	
	public Conc(boolean showThreads) {
		this.showThreads = showThreads;
	}
	
	private void printThreadName() {
		if (showThreads) {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	private void checkTopValue(long top) {
		if (top < minValue) {
			throw new IllegalArgumentException(String.format("Parameter \"top\" (%1$d) must be greater or equal than \"minValue\" (%2$d)", top, minValue));
		}
	}
	
	private Callable<Long> stairs(long start, long end) {
		return () -> {
			if (start > end) {
				throw new IllegalArgumentException(String.format("Parameter \"end\" (%1$d) must be greater or equal than parameter \"start\" (%2$d)", end, start));
			}
			
			printThreadName();
	        
			Long result = 0L;
			
			for(long i = start; i <= end; i++) {
				result += i;
			}
			
			return result;
		};
	}

	public List<Callable<Long>> getCallables(long top) throws Exception {
		checkTopValue(top);
		
		List<Callable<Long>> callables =  new ArrayList<Callable<Long>>();
		int maxTreads = ForkJoinPool.getCommonPoolParallelism();
		long start, end = minValue - 1;
		long scope = top - minValue;
		
		for (int i = 1; i <= maxTreads; i++) {
			start = end + 1;
			end = scope * i / maxTreads;
			
			if (end < start) {
				end = start;
			}
			
			if (end <= top) {
				callables.add(stairs(start, end));
			} else {
				break;
			}
		}
		return callables;
	}

	public Long countStairsSingleThread(long top) throws Exception {
		checkTopValue(top);
		
		return stairs(minValue, top).call();
	}
	
	public Long countStairsMultiThread(long top) throws Exception {
		List<Callable<Long>> callables = getCallables(top);
		
		ExecutorService executor = Executors.newWorkStealingPool();
		Long result = executor.invokeAll(callables)
		    .stream()
		    .mapToLong(future -> {
		        try {
		            return future.get();
		        }
		        catch (Exception e) {
		            throw new IllegalStateException(e);
		        }
		    })
		    .sum();
		
		stop(executor);
		return result;
	}

	public Long countStairsAtomicOperation(long top) throws Exception {
		List<Callable<Long>> callables = getCallables(top);

		LongBinaryOperator op = (x, y) -> x + y;
		LongAccumulator accumulator = new LongAccumulator(op, 0L);

		ExecutorService executor = Executors.newWorkStealingPool();
		callables.forEach(callable -> executor.submit(() -> {
			try {
				accumulator.accumulate(callable.call());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));

		stop(executor);
		return accumulator.getThenReset();
	}
	
	public static void main(String[] args) {
		Conc conc = new Conc(true);
		
		try {
			long top = 3000000L;
			
			System.out.println(String.format("Result in single thread = %1$d", conc.countStairsSingleThread(top)));
			System.out.println(String.format("Result in multiply threads = %1$d", conc.countStairsMultiThread(top)));
			System.out.println(String.format("Result using atomic operation = %1$d", conc.countStairsAtomicOperation(top)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
