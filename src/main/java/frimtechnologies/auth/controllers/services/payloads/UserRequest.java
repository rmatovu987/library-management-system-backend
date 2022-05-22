/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frimtechnologies.auth.controllers.services.payloads;

import org.eclipse.microprofile.openapi.annotations.media.Schema;


public class UserRequest {

	@Schema(required = true, example = "admin@gmail.com")
	public String email;

	@Schema(required = true, example = "John")
	public String firstName;

	@Schema(required = true, example = "Doe")
	public String lastName;
	@Schema(required = false, example = "***")
	public String password;

	@Schema(required = false, example = "Doe")
	public String phoneNumber;


}
