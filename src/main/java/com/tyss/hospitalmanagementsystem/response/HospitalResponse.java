package com.tyss.hospitalmanagementsystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HospitalResponse {
	private String hospitalName;

	private String contactNumber;

	private String emailAddress;

	private String hospitalAddress;

	private String title;

	private String descriptions;

}
