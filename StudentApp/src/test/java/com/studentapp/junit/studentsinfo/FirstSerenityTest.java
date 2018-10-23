package com.studentapp.junit.studentsinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Manual;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {
	@BeforeClass
	public static void init(){
		RestAssured.baseURI="http://localhost:8080/student";
	}
	@Test
	public void getAllStudents(){
		SerenityRest.given()
		.when()
		.get("/list")
		.then()
		.statusCode(200);
	}
		@Test
		public void thisIsNotGood(){
			SerenityRest.given()
			.when()
			.get("/list")
			.then()
			.statusCode(500);
			
		}
		@Pending
		@Test
		public void pendingTest(){
			
		}
		@Ignore
		@Test
        public void ignoreSkippedTest(){
			
		}
		
		@Test
        public void thisIsAErrorTest(){
			System.out.println("This is a error"+(5/0));
			
		}
		@Test
		public void fileDoesntExist() throws FileNotFoundException{
			File file = new File("/E:/file.txt");
			FileReader fr= new FileReader(file);
			
		}
		@Manual
		@Test
		public void thisIsAManualTest(){
		
		}
		@Title("This test will get the information of all the students from the Student App")
		@Test
		public void test01(){
			SerenityRest.given()
			.when()
			.get("/list")
			.then()
			.statusCode(200);
		}
	}


