package com.studentapp.cucumber.serenity;

import java.util.HashMap;
import java.util.List;

import com.studentapp.model.StudentClass;
import com.studentapp.utils.ReuseableSpecifications;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StudentSerenitySteps {
	
	@Step("Creating student with firstname:{0}, lastname:{1}, email{2}, programme: {3}, courses:{4}")
	public ValidatableResponse createStudent(String firstName, String lastName, String email, String programme, List<String> courses){
		
		StudentClass student= new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
		
		return SerenityRest.rest().given()
		.spec(ReuseableSpecifications.getGenericRequestSpec())
		.when()
		.body(student)
		.post()
		.then();
		
	}
	@Step("Getting the student information with firstname:{0}")
	public HashMap<String,Object> getStudentInfoByFirstName(String firstName){
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		return SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(p1+firstName+p2);
		
	
	}
	@Step("Update student with studentid: {0}, firstname:{1}, lastname:{2}, email{3}, programme: {4}, courses:{5}")
	public ValidatableResponse updateStudent(int studentid, String firstName, String lastName, String email, String programme, List<String> courses){
		
		StudentClass student= new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
		
		return SerenityRest.rest().given()
		.spec(ReuseableSpecifications.getGenericRequestSpec())
		.when()
		.body(student)
		.put("/"+ studentid)
		.then();
		
	}
@Step("Delete student with studentid:{0}")	
public ValidatableResponse deleteStudent(int studentid){
	SerenityRest
	.rest()
	.given()
	.when()
	.delete("/"+studentid);
	return null;
	
}
}
