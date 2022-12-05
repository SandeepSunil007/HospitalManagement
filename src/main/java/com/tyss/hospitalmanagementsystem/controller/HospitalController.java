package com.tyss.hospitalmanagementsystem.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.hospitalmanagementsystem.entity.Hospital;
import com.tyss.hospitalmanagementsystem.request.HospitalRequest;
import com.tyss.hospitalmanagementsystem.response.HospitalManagementResponseBody;
import com.tyss.hospitalmanagementsystem.response.HospitalResponse;
import com.tyss.hospitalmanagementsystem.service.HospitalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Slf4j
public class HospitalController {

	private static final String FETCHING_ALL_THE_HOSPITAL_DETAILS = "Fetching All the Hospital Details";
	private static final String WE_DELETED_THE_HOPITAL_DATA_BASED_ON_ID = "we deleted the hopital data based on id";
	private static final String WE_GET_HOSPITAL_DETAILS_BY_ID = "we get hospital details by id";
	private static final String HOSPITAL_DATA_UPDATED_SUCCESFULLY = "Hospital Data updated Succesfully";
	private static final String HOSPITAL_REGISTERED_SUCCESSFULLY = "Hospital registered Successfully";
	private final HospitalService hospitalService;

	// private static final Logger logs =
	// LoggerFactory.getLogger(HospitalController.class);

	// storing logs in folder

//	@GetMapping("/test/{name}")
//	public String greeting(@PathVariable String name) {
//		logs.debug("Request {}", name);
//		if(name.equalsIgnoreCase("test")){
//			throw  new RuntimeException("Exception occured....");
//		}
//		String response = "Hi " + name + " Welcome to Test Yantra";
//		logs.debug("Response {}", response);
//		return response;
//		
//	}

	@PostMapping("/hospital-registrations")
	public ResponseEntity<HospitalManagementResponseBody> hospitalRegister(
			@RequestBody HospitalRequest hospitalRequest) {
		hospitalService.registerationHospital(hospitalRequest);
		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message(HOSPITAL_REGISTERED_SUCCESSFULLY).build());

	}

	@PutMapping("/hospital-update")
	public ResponseEntity<HospitalManagementResponseBody> hospitalUpdate(@RequestBody HospitalRequest hospitalRequest) {
		hospitalService.updateHospitalData(hospitalRequest);
		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message(HOSPITAL_DATA_UPDATED_SUCCESFULLY).build());

	}

	@GetMapping("/hospital-get/{hospitalId}")
	public ResponseEntity<HospitalManagementResponseBody> getHospitalDataById(
			@PathVariable("hospitalId") Integer hospitalId) {

		// logs.info("inside class --> HospitalController ---> method ===
		// getHospitalDataById ",hospitalId);
		log.info("inside class --> HospitalController ---> method === getHospitalDataById ", hospitalId);

		Hospital hospital = hospitalService.getHospitalDataById(hospitalId);
		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message(WE_GET_HOSPITAL_DETAILS_BY_ID).data(hospital).build());

	}

	@DeleteMapping("/hospital-delete/{hospitalId}")
	public ResponseEntity<HospitalManagementResponseBody> deleteHospitalDataById(
			@PathVariable("hospitalId") Integer hospitalId) {
		hospitalService.deleteHospitalDetailsBaseOnId(hospitalId);
		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message(WE_DELETED_THE_HOPITAL_DATA_BASED_ON_ID).build());

	}

	@GetMapping("/hospital-getAll")
	public ResponseEntity<HospitalManagementResponseBody> getAllHospitalData() {

		// logs.info("inside class ---> HospitalController --> method ===
		// getAllHospitalData ");
		log.info("inside class --->  HospitalController --> method === getAllHospitalData ");

		List<HospitalResponse> allTheHospitalDetails = hospitalService.getAllTheHospitalDetails();
		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message(FETCHING_ALL_THE_HOSPITAL_DETAILS).data(allTheHospitalDetails).build());
	}

//	@GetMapping("/pagination/{pageSize}/{pageNumber}")
//	Page<Hospital> getHospitalData(@PathVariable int pageSize, @PathVariable int pageNumber) {
//		return hospitalService.getHospitalData(pageSize, pageNumber);
//
//	}

	
	
	@GetMapping("/pagination/{pageSize}/{pageNumber}")
	public ResponseEntity<HospitalManagementResponseBody> getPaginationData(@PathVariable int pageSize,
			@PathVariable int pageNumber) {
		List<Hospital> pagination = hospitalService.getHospitalData(pageSize, pageNumber);

		return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
				.message("successfully we get the data").data(pagination).build());

	}

	
	
//	    @GetMapping("/hospital-get/{hospitalId}")
//		public ResponseEntity<HospitalManagementResponseBody> getHospitalDataById(
//				@PathVariable("hospitalId") Integer hospitalId) {
//			
//		//	logs.info("inside class --> HospitalController ---> method === getHospitalDataById ",hospitalId);
//			log.info("inside class --> HospitalController ---> method === getHospitalDataById ",hospitalId);
//			
//			Hospital hospital = hospitalService.getHospitalDataById(hospitalId);
//			return ResponseEntity.ok(HospitalManagementResponseBody.builder().isError(Boolean.FALSE)
//					.message(WE_GET_HOSPITAL_DETAILS_BY_ID).data(hospital).build());
//
//		}

	@GetMapping("/pagination") // assending order
	Page<Hospital> getHospitalData(Pageable pageable) {
		return hospitalService.getHospitalData(pageable);

	}

	@GetMapping("/paginationDesendingByname")
	Page<Hospital> getHospitalDataDesByHospitalName(
			@PageableDefault(sort = "hospitalName", direction = Direction.DESC, size = 10) Pageable pageable) {
		return hospitalService.getHospitalData(pageable);

	}

	@GetMapping("/paginationDesending") // Desending order
	Page<Hospital> getHospitalDataDes(
			@PageableDefault(sort = "hospitalId", direction = Direction.DESC, size = 5) Pageable pageable) {
		return hospitalService.getHospitalData(pageable);

	}

}
