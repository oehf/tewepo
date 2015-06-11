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

import java.io.Serializable;

/**
 * An object of this class represents a reference portal data set, consisting of
 * those attributes: clinicalIssue; clinicalIndication; comments; basicdataSet
 * {@link BasicDataSet}
 * 
 * @author devmis
 * 
 */
@SuppressWarnings("serial")
public class ReferencePortalDataSet implements Serializable {

	private String clinicalIssue = "";
	private String clinicalIndication = "";
	private String comments = "";
	private BasicDataSet basicDataSet = null;

	/**
	 * Default constructor.
	 */
	public ReferencePortalDataSet() {
	}

	/**
	 * Creates a new reference portal data set with all given attributes.
	 * 
	 * @param clinicalIssue
	 *            clinical issue in the treatment of the patient
	 * @param clinicalIndication
	 *            clinical indication of the treatment
	 * @param comments
	 *            additional comments of the sender (doctor or patient)
	 * @param basicDataSet
	 *            {@link BasicDataSet}
	 */
	public ReferencePortalDataSet(String clinicalIssue,
			String clinicalIndication, String comments,
			BasicDataSet basicDataSet) {
		super();
		this.clinicalIssue = clinicalIssue;
		this.clinicalIndication = clinicalIndication;
		this.comments = comments;
		this.basicDataSet = basicDataSet;
	}

	/**
	 * Gets the given clinical issue.
	 * 
	 * @return clinicalIssue
	 */
	public String getClinicalIssue() {
		return clinicalIssue;
	}

	/**
	 * Sets the clinical issue.
	 * 
	 * @param clinicalIssue
	 */
	public void setClinicalIssue(String clinicalIssue) {
		this.clinicalIssue = clinicalIssue;
	}

	/**
	 * Gets the given clinical indication.
	 * 
	 * @return clinicalIndication
	 */
	public String getClinicalIndication() {
		return clinicalIndication;
	}

	/**
	 * Sets the clinical indication.
	 * 
	 * @param clinicalIndication
	 */
	public void setClinicalIndication(String clinicalIndication) {
		this.clinicalIndication = clinicalIndication;
	}

	/**
	 * Gets the comments.
	 * 
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets comments.
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the associated basic data set.
	 * 
	 * @return basicDataSet
	 */
	public BasicDataSet getBasicDataSet() {
		return basicDataSet;
	}

	/**
	 * Sets the associated basic data set.
	 * 
	 * @return basicDataSet
	 */
	public void setBasicDataSet(BasicDataSet basicDataSet) {
		this.basicDataSet = basicDataSet;
	}
}
