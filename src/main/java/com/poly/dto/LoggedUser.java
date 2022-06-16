package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {

	private Long id;

	private String username;

	private String fullName;

	private String password;

	private String email;

	private String phoneNumber;

	private String address;

	private String imageUrl;

	private String authority;
}
