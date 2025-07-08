package com.example.JUNIT_TESTING;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class JunitTestingApplication {

	public static void main(String[] args) {
		//SpringApplication.run(JunitTestingApplication.class, args);
		Scanner scanner = new Scanner(System.in);
System.out.print("Enter your byte");
   byte shortValue=scanner.nextByte();
   System.out.println(shortValue);

   double result=Math.pow(2,3);
   System.out.println(result);

	}

}
