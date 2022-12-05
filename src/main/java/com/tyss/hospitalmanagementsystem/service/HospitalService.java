package com.tyss.hospitalmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tyss.hospitalmanagementsystem.entity.Hospital;
import com.tyss.hospitalmanagementsystem.request.HospitalRequest;
import com.tyss.hospitalmanagementsystem.response.HospitalResponse;

public interface HospitalService {

	String registerationHospital(HospitalRequest hotelRequest);

	String updateHospitalData(HospitalRequest updateRequest);

	Hospital getHospitalDataById(Integer hospitalId);

	List<HospitalResponse> getAllTheHospitalDetails();

	String deleteHospitalDetailsBaseOnId(Integer hospitalId);
	

	List<Hospital>  getHospitalData(int pageSize, int pageNumber);
	
	public Page<Hospital> getHospitalData(Pageable pageable);
	

}
