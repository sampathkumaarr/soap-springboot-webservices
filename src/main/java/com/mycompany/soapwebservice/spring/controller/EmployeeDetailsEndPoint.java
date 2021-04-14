package com.mycompany.soapwebservice.spring.controller;

import com.mycompany.xsd.address.Address;
import com.mycompany.xsd.employeedetails.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@ComponentScan
public class EmployeeDetailsEndPoint {

    Logger logger = LogManager.getLogger(EmployeeDetailsEndPoint.class);

    private static final String NAMESPACE_URI = "http://mycompany.com/xsd/EmployeeDetails";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeDetailsRequest")
    @ResponsePayload
    public GetEmployeeDetailsResponse getEmployeeDetailsRequest(@RequestPayload GetEmployeeDetailsRequest getEmployeeDetailsRequest) {
        logger.info("Beginning of the request!!!");
        GetEmployeeDetailsResponse getEmployeeDetailsResponse = new GetEmployeeDetailsResponse();
        GetEmployeeDetailsRes getEmployeeDetailsRes = new GetEmployeeDetailsRes();
        EmployeeDetails employeeDetails = new EmployeeDetails();
            Employee employee = new Employee();
                employee.setId("112233");
                employee.setFirstName("John");
                employee.setLastName("Smith");
                employee.setMobile("0712345678");
                    Address address = new Address();
                        address.setAddressLine1("Flat 123");
                        address.setAddressLine2("Park House");
                        address.setAddressLine3("Victoria Street");
                        address.setPostcode("AB10 7AR");
                        address.setTown("London");
                        address.setCountry("United Kingdom");
                employee.setAddressDetails(address);
        employeeDetails.getEmployee().add(employee);
        getEmployeeDetailsRes.setEmployeeDetails(employeeDetails);
        getEmployeeDetailsResponse.setGetEmployeeDetailsRes(getEmployeeDetailsRes);
        return getEmployeeDetailsResponse;
    }
}
