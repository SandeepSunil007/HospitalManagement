package com.tyss.hospitalmanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tyss.hospitalmanagementsystem.controller.HospitalController;
import com.tyss.hospitalmanagementsystem.entity.Hospital;
import com.tyss.hospitalmanagementsystem.exception.HospitalNotFoundException;
import com.tyss.hospitalmanagementsystem.repository.HospitalRepository;
import com.tyss.hospitalmanagementsystem.request.HospitalRequest;
import com.tyss.hospitalmanagementsystem.response.HospitalResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Scope("singleton")
@Slf4j
public class HospitalServiceImpl implements HospitalService {

	private static final String HOSPITAL_DETAILS_DELETED_SUCCESSFULLY = "Hospital Details Deleted Successfully";
	private static final String HOSPITAL_DETAILS_UPDATED_SUCCESSFULLY = "Hospital Details Updated Successfully";
	private static final String HOSPITAL_REGISTERED_SUCCESSFULLY = "Hospital Registered Successfully";
	private static final String HOSPITAL_IS_NOT_FOUND = "Hospital is not found";

	private final HospitalRepository hospitalRepository;

//	private static final Logger logsimp = LoggerFactory.getLogger(HospitalServiceImpl.class);

	public @Override String registerationHospital(HospitalRequest hotelRequest) {
		Hospital hospital = Hospital.builder().hospitalName(hotelRequest.getHospitalName())
				.contactNumber(hotelRequest.getContactNumber()).descriptions(hotelRequest.getDescriptions())
				.emailAddress(hotelRequest.getEmailAddress()).hospitalAddress(hotelRequest.getHospitalAddress())
				.title(hotelRequest.getTitle()).build();
		hospitalRepository.save(hospital);
		return HOSPITAL_REGISTERED_SUCCESSFULLY;
	}

	public @Override String updateHospitalData(HospitalRequest updateRequest) {
		Hospital hospital = hospitalRepository.findById(updateRequest.getHospitalId())
				.orElseThrow(() -> new HospitalNotFoundException(HOSPITAL_IS_NOT_FOUND));
		BeanUtils.copyProperties(updateRequest, hospital);
		hospitalRepository.save(hospital);
		return HOSPITAL_DETAILS_UPDATED_SUCCESSFULLY;
	}

	public @Override Hospital getHospitalDataById(Integer hospitalId) {
	
		//	logsimp.info("inside class --->  HospitalServiceImpl -->method === getHospitalDataById ", hospitalId);
		log.info("inside class --->  HospitalServiceImpl -->method === getHospitalDataById ", hospitalId);
		
		return hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new HospitalNotFoundException(HOSPITAL_IS_NOT_FOUND));
	}

	public @Override List<HospitalResponse> getAllTheHospitalDetails() {
	
		//	logsimp.info("inside class --->  HospitalServiceImpl --> method === getAllTheHospitalDetails ");
		log.info("inside class --->  HospitalServiceImpl --> method === getAllTheHospitalDetails ");
		
		return hospitalRepository.findAll().stream().map(hospital -> {
			HospitalResponse hospitalResponse = HospitalResponse.builder().build();
			BeanUtils.copyProperties(hospital, hospitalResponse);
			return hospitalResponse;
		}).collect(Collectors.toList());

	}

	public @Override String deleteHospitalDetailsBaseOnId(Integer hospitalId) {
		hospitalRepository.deleteById(hospitalId);
		return HOSPITAL_DETAILS_DELETED_SUCCESSFULLY;
	}

	@Override
	public List<Hospital> getHospitalData(int pageSize , int  pageNumber) {
		Pageable page = PageRequest.of(pageSize, pageNumber);
		return hospitalRepository.findAll(page).getContent();
//		return hospitalRepository.findAll(page);
	}

//	public Page<Hospital> getHospitalData(int pageSize , int  pageNumber) {
//		return hospitalRepository.findAll().stream().map(hospitaldata ->{
//			HospitalResponse hospitalResponse = HospitalResponse.builder().build();
//			BeanUtils.copyProperties(hospitaldata, hospitalResponse);
//			
//			
//		});
		
	
	
	
	@Override
	public Page<Hospital> getHospitalData(Pageable pageable) {
		return hospitalRepository.findAll(pageable);
	}

}
