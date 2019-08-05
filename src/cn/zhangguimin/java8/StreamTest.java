package cn.zhangguimin.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import cn.zhangguimin.java8.Employee.Status;

/**
 * 流式计算
 * 	1.创建Stream
 * 	2.中间操作
 * 	3.终止操作
 * 
 * @author 章鱼先森
 *
 * @date 2018年7月26日
 */
public class StreamTest {
	
	//创建Stream
	@SuppressWarnings("unused")
	@Test
	public void test1() {
		// 1、通过Collection系列集合提供的Stream()或parallelStream()
		List<String> list = new ArrayList<>();
		Stream<String> stream = list.stream();

		// 2、通过Arrays.stream()获取
		String[] str = new String[10];
		Stream<String> stream2 = Arrays.stream(str);
		
		// 3、通过Stream类中的静态方法 of()获取
		Stream<String> stream3 = Stream.of("aa","bb","cc");
		
		// 4、创建无限流
		//迭代
		Stream<Integer> stream4=Stream.iterate(0, (x)->x+2);
		//生成
		Stream<Double> stream5=Stream.generate(()->Math.random());
	}
	
	//-----------------------中间操作--------------------------
	List<Employee> emps=Arrays.asList(
			new Employee("zhangsan",19,9999.9),
			new Employee("lisi",19,8888.8),
			new Employee("wangwu",37,7777.7),
			new Employee("zhaoliu",46,66666.6),
			new Employee("tianqi",55,5555.5),
			new Employee("tianqi",55,5555.5),
			new Employee("tianqi",55,5555.5));
	/**
	 * 筛选与切片
	 * filter--接收Lambda,从流中排除某些元素
	 * limit--截断流，使其元素不超过给定数量
	 * skip(n)--跳过元素，返回一个扔掉前n个元素流。若流元素不足n个，则返回一个空流，与limit(n)互补
	 * distinct--筛选，通过流生成元素的hashCode()和equals()去掉重复的元素
	 * 
	 * 内部迭代操作:由Stream迭代操作
	 * 
	 */
	@Test
	public void test2() {
		//中间操作filter
		Stream<Employee> stream=emps.stream().filter((x)->x.getAge()>19);
		//终止操作，如果没有终止操作，则中间操作不会执行，叫"惰性求值"或叫"延迟加载"
		stream.forEach(System.out::println);
		
		//中间操作limit
		System.out.println("----------------------limit-----------");
		emps.stream().limit(2).forEach(System.out::println);
		
		//中间操作skip
		System.out.println("-----------------------skip----------");
		emps.stream().skip(2).forEach(System.out::println);
		
		//中间操作distinct,需要重写equals()和hashCode()
		System.out.println("----------------------distinct-----------");
		emps.stream().distinct().forEach(System.out::println);
	}
	/**
	 * 映射
	 * map--接收Lambda将元素转换成其它形式或提取信息，接收一个函数作为参数，该函数会被用到每个元素上，并将其映射成一个新的元素。
	 * flatMap--接收一个函数作为参数，将流的每一个值都换成另一个流，然后把所有流连接成一个新流
	 */
	@Test
	public void test3() {
		emps.stream().map(Employee::getName).map((x)->x.toUpperCase()).forEach(System.out::println);
		Stream<Character> ch=emps.stream().map(Employee::getName).flatMap(StreamTest::flatMapMethd);
		ch.forEach(System.out::println);
	}
	public static Stream<Character> flatMapMethd(String str) {
		List<Character> ch=new ArrayList<>();
		for (Character character : str.toCharArray()) {
			ch.add(character);
		}
		return ch.stream();
	}
	
	/**
	 * 排序
	 * sorted()--自然排序(Comparable)
	 * sorted(Comparator com)--定制排序(Comparable)
	 */
	@Test
	public void test4() {
		List<String> list = Arrays.asList("ddd","ccc","aaa","bbb","fff","eee");
		
		//自然排序，按照字典排序
		list.stream().sorted().forEach(System.out::println);//aaa bbb ccc ddd eee fff
		//倒序
		list.stream().sorted((x,y)->-x.compareTo(y)).forEach(System.out::println);
		//定制排序
		emps.stream().sorted((x,y)->{
			if (x.getAge()==y.getAge()) {
				return x.getName().compareTo(y.getName());
			}else {
				return Integer.compare(x.getAge(), y.getAge());
			}
		}).forEach(System.out::println);
		
	}
	
	
	//--------------------------------------------终止操作---------------------------------------
	
	List<Employee> emps2=Arrays.asList(
			new Employee("zhangsan",19,9999.9,Status.FREE),
			new Employee("lisi",19,8888.8,Status.BUSY),
			new Employee("wangwu",37,7777.7,Status.VOCATION),
			new Employee("zhaoliu",46,6666.6,Status.BUSY),
			new Employee("tianqi",55,5555.5,Status.FREE),
			new Employee("tianqi",55,5555.5,Status.BUSY),
			new Employee("tianqi",55,5555.5,Status.VOCATION));
	/**
	 * 查找与匹配
	 * allMatch--检查是否匹配所有元素
	 * anyMatch--检查是否至少匹配一个元素
	 * noneMatch--检查是否没有匹配所有元素
	 * findFist--返回第一个元素
	 * findAny--返回当前流中任意元素
	 * count--返回流中元素总个数
	 * max--返回流中最大值
	 * min--返回流中最小值
	 */
	@Test
	public void test5() {
		//检查是否匹配所有元素
		boolean b=emps2.stream().allMatch((x)->x.getStatus().equals(Status.FREE));
		System.out.println(b);
		
		//检查是否至少匹配一个元素
		boolean b2=emps2.stream().anyMatch((x)->x.getStatus().equals(Status.FREE));
		System.out.println(b2);
		
		//检查是否没有匹配所有元素
		boolean b3=emps2.stream().noneMatch((x)->x.getStatus().equals(Status.UNKNOW));
		System.out.println(b3);
		
		//返回第一个元素
		Optional<Employee> ot=emps2.stream().findFirst();
		System.out.println(ot.get());
		
		//返回当前流中任意元素 parallelStream()变成并行流后取出所有 Status.BUSY状态的流，然后才可以获取任意一个Status.BUSY状态的Employee
		Optional<Employee> opt=emps2.parallelStream().filter((x)->x.getStatus().equals(Status.BUSY)).findAny();
		System.out.println(opt.get());
		
		//返回流中元素总个数
		long l=emps2.stream().count();
		System.out.println(l);
		
		// max--返回流中最大值
		Optional<Employee> opt2=emps2.stream().max((x,y)->Double.compare(x.getSalary(), y.getSalary()));
		System.out.println(opt2.get());
		
		//min--返回流中最小值 只返回salary最小值
		Optional<Double> opt3=emps2.stream().map(Employee::getSalary).min(Double::compare);
		System.out.println(opt3.get());
	}
	/**
	 * 规约
	 * reduce(BinaryOperator<T> accumulator)
	 * reduce(T identity, BinaryOperator<T> accumulator)
	 * 可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test6() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Integer i = list.stream().reduce(0, (x,y)->x+y);
		System.out.println(i);
		
		//计算工资总和
		Optional<Double> d=emps2.stream().map(Employee::getSalary).reduce(Double::sum);
		System.out.println(d.get());
	}
	/**
	 * 收集
	 * Collect--将流转换为其它形式的流，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
	 */
	@Test
	public void test7() {
		
		Set<Employee> set = new HashSet<>();
		set.add(new Employee("zhangsan",19,9999.9,Status.FREE));
		set.add(new Employee("wangwu",37,7777.7,Status.VOCATION));
		set.add(new Employee("lisi",19,8888.8,Status.BUSY));
		set.add(new Employee("zhaoliu",46,6666.6,Status.BUSY));
		set.add(new Employee("tianqi",55,5555.5,Status.FREE));
		set.add(new Employee("tianqi",55,5555.5,Status.FREE));
		set.add(new Employee("tianqi",55,5555.5,Status.VOCATION));
		
		Map<Integer, Long> collect = 
				emps2
				.parallelStream()
				.collect(Collectors.groupingBy(Employee::getAge,Collectors.counting()));
		Set<Integer> collect2 = collect.entrySet().parallelStream().filter(m->m.getValue()>=2).map(m->m.getKey()).collect(Collectors.toSet());
		collect2.forEach(System.out::println);
//		Set<String> names=set.stream().map(Employee::getName).collect(Collectors.toSet());
//		
//		
//		
//		
//		names.forEach(System.out::println);
//		System.out.println("-------------------去重重复姓名---------------------------");
//		List<String> names2=emps2.stream().map(Employee::getName).distinct().collect(Collectors.toList());
//		names2.forEach(System.out::println);
//		System.out.println("-------------------求人员总数---------------------------");
//		long total=emps2.stream().collect(Collectors.counting());
//		System.out.println(total);
//		System.out.println("-------------------求工资平均---------------------------");
//		Double db=emps2.stream().collect(Collectors.averagingDouble(Employee::getSalary));
//		System.out.println(db);
//		System.out.println("-------------------求工资和---------------------------");
//		Double sum=emps2.stream().collect(Collectors.summingDouble(Employee::getSalary));
//		System.out.println(sum);
//		System.out.println("-------------------分组，状态分组---------------------------");
//		Map<Status, List<Employee>> empGroup=emps2.stream().collect(Collectors.groupingBy(Employee::getStatus));
//		System.out.println(empGroup);
//		System.out.println("-------------------分区,薪资大于7000---------------------------");
//		Map<Boolean, List<Employee>> empPartition=emps2.stream().collect(Collectors.partitioningBy((x)->x.getSalary()>7000));
//		System.out.println(empPartition);
//		System.out.println("-------------------功能集合 求和、平均。。---------------------------");
//		DoubleSummaryStatistics summarizing=emps2.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
//		System.out.println(summarizing.getAverage());
//		System.out.println(summarizing.getCount());
//		System.out.println(summarizing.getSum());
//		System.out.println(summarizing.getMax());
//		System.out.println("-------------------将结果连接---------------------------");
//		String joining=emps2.stream().map(Employee::getName).collect(Collectors.joining(","));
//		System.out.println(joining);
		
	}
}
