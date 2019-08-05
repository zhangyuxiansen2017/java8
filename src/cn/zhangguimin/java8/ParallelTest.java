package cn.zhangguimin.java8;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

import org.junit.Test;

/**
 * 并行流，多线程
 * 
 * @author 章鱼先森
 *
 * @date 2018年7月27日
 */
public class ParallelTest {
	@Test
	public void test1() {
		Instant start=Instant.now();
		LongStream.rangeClosed(0, 100000000000L)
					.parallel()
					.reduce(0, Long::sum);
		Instant end=Instant.now();
		System.out.println(Duration.between(start, end).toMillis());
		
//		Integer[] i=new Integer[]{1,2,3};
//		Arrays.stream(i)
//					.parallel()
//					.reduce(0, Integer::sum);
	}
}
