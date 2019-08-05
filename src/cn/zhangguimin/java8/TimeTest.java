package cn.zhangguimin.java8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;

public class TimeTest {
	// 人读 1.LocalDate LocalTime LocalDateTime
	@Test
	public void test1() {
		System.out.println("---------------------当前日期------------------------");
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		System.out.println("---------------------指定日期------------------------");
		LocalDateTime ldt2 = LocalDateTime.of(2018, 07, 27, 17, 02);
		System.out.println(ldt2);
		System.out.println("---------------------计算日期------------------------");
		LocalDateTime ldt3 = ldt2.plusDays(30); // 加 plusXxx()减 minusXxx()
		System.out.println(ldt3);
		System.out.println("---------------------获取值-------------------------");
		System.out.print(ldt.getYear() + "年");
		System.out.print(ldt.getMonthValue() + "月");
		System.out.print(ldt.getDayOfMonth() + "日 ");
		System.out.print(ldt.getHour() + "时");
		System.out.print(ldt.getMinute() + "分");
		System.out.print(ldt.getSecond() + "秒");
	}

	// 机读 2.Instant:时间戳(以Unix元年:1970-01-01 00:00:00)
	@Test
	public void test2() {
		Instant in = Instant.now(); // 默认为0时区
		System.out.println(in);
		OffsetDateTime odt = in.atOffset(ZoneOffset.ofHours(8)); // 设置时区
		System.out.println(odt);
	}

	/*
	 * Duration:计算时间之间间隔 Period:计算两个日期之间间隔
	 */
	@Test
	public void test3() throws Exception {
		Instant in1 = Instant.now();
		Thread.sleep(3000);
		Instant in2 = Instant.now();
		Duration du = Duration.between(in1, in2);
		System.out.println(du.toMillis());
		
		LocalTime time = LocalTime.now();
		Thread.sleep(2000);
		LocalTime time2 = LocalTime.now();
		Duration du2 = Duration.between(time, time2);
		System.out.println(du2.getSeconds());
		
		LocalDate date = LocalDate.of(2018, 6, 01);
		LocalDate data2 = LocalDate.now();
		Period pe = Period.between(date, data2);
		System.out.println(pe.getDays());
	}

	// TemporalAdjuster:时间校正器
	@Test
	public void test4() {
		LocalDateTime ldt = LocalDateTime.now();
		LocalDateTime ldt2 = ldt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		System.out.println(ldt2);
	}

	// DateTimeFormatter:格式化时间/日期
	@Test
	public void test5() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt.format(dtf));
		System.out.println(LocalDateTime.parse(ldt.format(dtf), dtf));
	}

	// ZoneDate、ZoneTime、ZoneDateTime
	@Test
	public void test6() {
		ZoneId.getAvailableZoneIds().forEach(System.out::println);
		LocalDate ldt=LocalDate.now(ZoneId.of("Asia/Shanghai"));
		System.out.println(ldt.getDayOfWeek());
	}
	/**
	 * 获取类加载器
	 */
	@Test
	public void test7() {
		Object obj=new Object();
		System.out.println(obj.getClass().getClassLoader());
		System.out.println(Employee.class.getClassLoader());
		System.out.println(Employee.class.getClassLoader().getParent());
		System.out.println(Employee.class.getClassLoader().getParent().getParent());
	}
	@Test
	public void test8() {
		int i = (int) Math.pow(2, 38);
		System.out.println(i);
		//2147483647
	}
}
