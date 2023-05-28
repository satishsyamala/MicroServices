/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.pojos;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Shrehan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS", schema = "mycart")
public class Users {

	@Id
	@Column(name = "USER_ID")
	private long userId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "MOBLIE_NO")
	private String mobileNo;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "USER_TYPE")
	private String userType;
	@Transient
	private String token;

}
