package cn.zhangguimin.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * java8内置四大核心式函数接口
 * 		Consumer<T> :消费型接口，一个参数，无返回值
 * 				void accept(T t);
 * 		Supplier<T> :供给型接口，无参数，有返回值
 * 				T get();
 * 		Function<T,R> :函数型接口,一个参数，有返回值
 * 			R apply(T t);
 * 		Predicate<T> :断言型接口,有一个参数，返回boolean
 * 			boolean test(T t);
 * @author 章鱼先森
 *
 * @date 2018年7月26日
 */
public class LambdaTest2 {
	
	@Test
	public void test01() {
		consumer(10000.0, x->System.out.println("AAAAA"));
	}
	public void consumer(double money,Consumer<Double> con) {
		con.accept(money);
	}
//-----------------------------------------------------------------
	@Test
	public void test02() {
		System.out.println(supplier(()-> 100+100));
	}
	public Integer supplier(Supplier<Integer> sup) {
		return sup.get();
	}
//-----------------------------------------------------------------
	@Test
	public void test03() {
		System.out.println(function("ZHANGGUIMIN", (x) -> x.toLowerCase()));
	}
	public String function(String str,Function<String,String> fun) {
		return fun.apply(str);
	}
//-----------------------------------------------------------------
	@Test
	public void test04() {
		List<String> list=Arrays.asList("aa","bbb","cccc","ddddd","ffffff");
		List<String> st=toBoole(list, (x) -> x.length()>3);
		for (String string : st) {
			System.out.println(string);
		}
		
	}
	public List<String> toBoole(List<String> list,Predicate<String> pre) {
		List<String> li=new ArrayList<>();
		for (String str : list) {
			if (pre.test(str)) {
				li.add(str);
			}
		}
		return li;
	}
}
