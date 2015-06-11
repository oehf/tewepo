/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.tewepo.twp.dmp.dmc.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

/**
 * An object of this class represents the recipient data.
 * 
 * @author devmis
 * 
 */
public class EmpfaengerData extends DataSource {

	private static EmpfaengerData instance = null;

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static EmpfaengerData getInstance() {
		if (instance == null) {
			instance = new EmpfaengerData("employeesDS");
		}
		return instance;
	}

	/**
	 * Creates a new recipient data with the given ID.
	 * 
	 * @param id
	 */
	public EmpfaengerData(String id) {

		setID(id);
		setTitleField("Name");
		setRecordXPath("/data");
		DataSourceTextField nameField = new DataSourceTextField("Name", "Name",
				128);

		DataSourceIntegerField employeeIdField = new DataSourceIntegerField(
				"EmployeeId", "Employee ID");
		employeeIdField.setPrimaryKey(true);
		employeeIdField.setRequired(true);

		DataSourceIntegerField reportsToField = new DataSourceIntegerField(
				"ReportsTo", "Manager");
		reportsToField.setRequired(true);
		reportsToField.setForeignKey(id + ".EmployeeId");
		reportsToField.setRootValue("1");

		DataSourceTextField jobField = new DataSourceTextField("Job", "Title",
				128);
		DataSourceTextField emailField = new DataSourceTextField("Email",
				"Email", 128);
		DataSourceTextField statusField = new DataSourceTextField(
				"EmployeeStatus", "Status", 40);
		DataSourceFloatField salaryField = new DataSourceFloatField("Salary",
				"Salary");
		DataSourceTextField orgField = new DataSourceTextField("OrgUnit",
				"Org Unit", 128);
		DataSourceTextField genderField = new DataSourceTextField("Gender",
				"Gender", 7);
		genderField.setValueMap("male", "female");
		DataSourceTextField maritalStatusField = new DataSourceTextField(
				"MaritalStatus", "Marital Status", 10);

		setFields(nameField, employeeIdField, reportsToField, jobField,
				emailField, statusField, salaryField, orgField, genderField,
				maritalStatusField);

		setDataURL("data/employees.data.xml");
		setClientOnly(true);
	}
}