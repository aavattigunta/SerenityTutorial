package com.studentapp.junit.studentsinfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReuseableSpecifications;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {
	
	static String firstName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String lastName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue()+"xyz@gmail.com";
	static int studentId;
	
	@Steps
	StudentSerenitySteps steps;
	@Title("This will create a new student ")
	@Test
	public void test001(){
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReuseableSpecifications.getGenericResponseSpec());
		
	}
	
	@Title("Verify if the student was added to the application")
	@Test
	public void test002(){
		
		HashMap<String, Object> value = steps.getStudentInfoByFirstName(firstName);
		System.out.println("The value is"+value);
		assertThat(value,hasValue(firstName));
		studentId = (int) value.get("id");
	}
	@Title("Update the user information and verify the upodated information!")
	@Test
	public void test003(){
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		firstName = firstName+"_Updated";
		
		steps.updateStudent(studentId, firstName, lastName, email, programme, courses)
		.log()
		.all();
		HashMap<String, Object> value = SerenityRest.rest().given()
				.when()
				.get("/list")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract()
				.path(p1+firstName+p2);
				System.out.println("The value is"+value);
				assertThat(value,hasValue(firstName));
	}
	@Title("Delete the student and verify wheather the student is deleted")
	@Test
	public void test004(){
		
		steps.deleteStudent(studentId);
		SerenityRest
		.rest()
		.given()
		.when()
		.get("/"+ studentId).then()
		.log()
		.all()
		.statusCode(404);
		
	}

}
