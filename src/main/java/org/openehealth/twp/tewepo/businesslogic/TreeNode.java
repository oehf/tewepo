/*
 * Copyright 2012 Benjamin Schneider
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
package org.openehealth.twp.tewepo.businesslogic;

/**
 * This class represents a node in a LogFile display tree
 * 
 */
public class TreeNode {

	// Muster String: "1|0|2009|#" --> nodeId | parentNodeId | nodeName |
	// nodeUrl
	/** contains the node ID  */
	private int nodeId = 1;
	/** contains the ID of the parent node */
	private int parentNodeId = 0;
	/** contains the name of the node, this is also displayed */
	private String nodeName = "root";
	/**
	 * can contain an url, if a link is to be opened when clicking on the node
	 */
	private String nodeUrl = "#";
	/** contains the month of a node element, is used to compare */
	private String month = "";
	/** contains the day of a node element, is used to compare */
	private String day = "";
	/** contains the year of a node element, is used to compare */
	private String year = "";


	/**
	 * 
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * 
	 * @param year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * 
	 * @param month
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * 
	 * @return
	 */
	public String getDay() {
		return day;
	}

	/**
	 * 
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * 
	 * @return
	 */
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * 
	 * @param nodeId
	 */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * 
	 * @return
	 */
	public int getParentNodeId() {
		return parentNodeId;
	}

	/**
	 * 
	 * @param parentNodeId
	 */
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	/**
	 * 
	 * @return
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 
	 * @param nodeName
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 
	 * @return
	 */
	public String getNodeUrl() {
		return nodeUrl;
	}

	/**
	 * 
	 */
	public void setEmptyNodeUrl() {
		this.nodeUrl = "#";
	}

	/**
	 * 
	 * @param nodeUrl
	 */
	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = "Dispatcher?identity=openLogFile&logfilename=" + nodeUrl;
	}

	/**
	 * 
	 */
	public String toString() {
		return nodeId + "|" + parentNodeId + "|" + nodeName + "|" + nodeUrl;
	}
}
