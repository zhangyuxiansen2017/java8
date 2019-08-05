package cn.zhangguimin.java8;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * 方法引用:若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”
 * 		主要为三种语法:
 * 			对象::实例方法
 * 			类::静态方法
 * 			类::实例方法   第一个参数为是Lambda体中方法调用者，第二个参数为该方法调用的参数
 * 		注意:
 * 			①Lambda体中调用的方法参数列表与返回值类型要与函数式接口中抽象方法的参数列表、返回类型相同。
 * 构造器引用:
 * 		语法:
 * 			类::new;
 * 		注意:需要调用的构造器参数列表要与函数式接口中抽象方法的参数列表保持一致。
 * 数组引用:
 * 		语法:
 * 			Type::new;
 * @author 章鱼先森
 *
 * @date 2018年7月26日
 */
public class LambdaTest3 {
	// 对象::实例方法
	@Test
	public void test01() {
		Consumer<String> con = System.out::println;
		con.accept("章贵敏");
	}

	// 类::静态方法
	@Test
	public void test02() {
		Comparator<Integer> com = Integer::compare;
		System.out.println(com.compare(100, 20));
	}

	// 类::实例方法
	@Test
	public void test3() {
		BiPredicate<String, String> com = (x, y) -> x.equals(y);
		System.out.println(com.test("AA", "AA"));
		// 可以使用 方法引用(x必须为实例方法调用者，y必须为实例方法参数)
		BiPredicate<String, String> com2 = String::equals;
		System.out.println(com2.test("BB", "BB"));
	}

	// 构造器引用
	@SuppressWarnings("unused")
	@Test
	public void test4() {
		Supplier<Employee> su = () -> new Employee();
		Supplier<Employee> su2 = Employee::new;

		Function<String, Employee> fu = (x) -> new Employee(x);
		Function<String, Employee> fu2 = Employee::new;

		BiFunction<String, Integer, Employee> bfu1 = (x, y) -> new Employee(x, y);
		BiFunction<String, Integer, Employee> bfu2 = Employee::new;
	}
	//数组引用
	@SuppressWarnings("unused")
	@Test
	public void test5() {
		Function<Integer, String[]> fu=(x)->new String[x];
		Function<Integer, String[]> fu2=String[]::new;
	}
}
