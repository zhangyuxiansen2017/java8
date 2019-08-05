package cn.zhangguimin.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * lambda表达式 
 * 		-> 左边为参数列表，右边为Lambda体
 * 		①无参数无返回值 Runnable eg:test01();
 * 		②有参数无返回值 Consumer eg:test02();
 * 			一个参数，小括号可以不写 (i)->：i->
 * 		③多个参数，Lambda体中多条语句  eg:test03();
 * 			多个参数，Lambda体中只有一个语句可以省略{}，包括return
 * 		④Lambda参数列表的数据类型可以省略不写，因为虚拟机可以根据上下文推断出数据类型。如果要写需要都写
 * 	需要函数式接口
 * 		接口中只有一个抽象方法的接口，称为函数式接口。可以使用注释@FunctionInterface修饰。
 * @author 章鱼先森
 *
 * @date 2018年7月26日
 */
public class LambdaTest {

	@Test
	public void test01() {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				System.out.println("JAVA8以前");
			}
		};
		
		run.run();

		System.out.println("-----------------------");
		Runnable run8 = () -> System.out.println("JAVA8以后 Lambada表达式:无参数无返回值");
		run8.run();
	}

	@Test
	public void test02() {
		Consumer<String> con = (i) -> System.out.println(i);
		con.accept("有参数无返回值");

		System.out.println("-------------------------------");
		Consumer<String> con2 = i -> System.out.println(i);
		con2.accept("一个参数，小括号可以不写 (i)->：i->");
	}

	@Test
	public void test03() {
		Comparator<Integer> com = (x, y) -> {
			System.out.println("两个参数,Lamdba体有多条语句");
			return Integer.compare(x, y);
		};
		System.out.println(com.compare(11, 10));
		
		System.out.println("多个参数，Lambda体中只有一个语句可以省略{}，包括return");
		Comparator<Integer> com2 = (x, y) -> Integer.compare(x, y);
		System.out.println(com2.compare(11, 10));
	}
	
//-------------------------------------------------------------
	List<Employee> list=Arrays.asList(
			new Employee("zhangsan",19,9999.9),
			new Employee("lisi",19,8888.8),
			new Employee("wangwu",37,7777.7),
			new Employee("zhaoliu",46,66666.6),
			new Employee("tianqi",55,5555.5));
	
	@Test
	public void test04() {
		//排序，根据年龄排序，如果年龄相同按姓名排。
		/*Collections.sort(list, (x,y)->{
			if (x.getAge()==y.getAge()) {
				return x.getName().compareTo(y.getName());
			}else {
				return Integer.compare(x.getAge(), y.getAge());
			}
		});
		for (Employee employee : list) {
			System.out.println(employee);
		}*/
		Collections.sort(list,Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getName));
		list.forEach(System.out::println);
		//List<Employee> emps=list.stream().sorted((x,y)->-Integer.compare(x.getAge(), y.getAge())).collect(Collectors.toList());
		//emps.forEach(System.out::println);
	}
	
//------------------------------------------------------------------------------------	
	@Test
	public void test05() {
		//创建函数式接口，创建doString(String str,LambdaInterface li)方法，对字符串进行处理
		String str=doString("zhangguimin", x->{
			return x.toUpperCase();
		});
		System.out.println(str);
	}
	
	public String doString(String str,LambdaInterface li) {
		return li.getValue(str);
	}
	
//---------------------------------------------------------------
	@Test
	public void test06() {
		//创建带泛型的函数式接口，R为返回值，T为参数的函数式接口，创建op(Long l1,Long l2,LambdaInterface2<Long, Long> li)方法，long处理 
		op(100l, 150l, (x,y)->x+y);
	}
	public void op(Long l1,Long l2,LambdaInterface2<Long, Long> li) {
		System.out.println(li.getValue(l1, l2));
	}
}
