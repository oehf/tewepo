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

function demoTest() {
	var message = 'test';

 	if (message != '') {
 		document.getElementById('message').innerHTML = message;
 		return false;
 	}else
 		return true;
 	
}

function pruefeSysConfPfad() {
	var message = '';
	
	//----------------path configuration 
	
	if (!pruefeString(document.systemadministrationEditPath.tmpFolderPath.value)) {
		message += 'Kein Pfad eingegeben!<br>';
	} 
	
  	if (!pruefeString(document.systemadministrationEditPath.pathRecipientKeys.value)) {
		message += 'Kein Schlüssel eingegeben!';
 	} 
  	
	if (!pruefeString(document.systemadministrationEditPath.filenameXMLFileRecipients.value)) {
		message += 'Kein Dateiname eingegeben!<br>';
	} 
	
  	if (!pruefeString(document.systemadministrationEditPath.pathLogFiles.value)) {
		message += 'Kein Pfad eingegeben!';
 	} 
  	
  	//----------------

 	if (message != '') {
 		document.getElementById('message').innerHTML = message;
 		return false;
 	}else
 		return true;
 	
}

function pruefeSysConfMail() {
	var message = '';
	
  	
  	//----------mail configuration
  	
	if (!pruefeString(document.systemadministrationEditMail.mailUsername.value)) {
		message += 'Kein Pfad eingegeben!<br>';
	} 
	
  	if (!pruefeString(document.systemadministrationEditMail.mailPassword.value)) {
		message += 'Kein Schlüssel eingegeben!';
 	} 
  	
	if (!pruefeString(document.systemadministrationEditMail.mailSenderAddress.value)) {
		message += 'Kein Dateiname eingegeben!<br>';
	} 
	
  	if (!pruefeString(document.systemadministrationEditMail.mailSmtpHost.value)) {
		message += 'Kein Pfad eingegeben!';
 	} 
  	
	if (!pruefeString(document.systemadministrationEditMail.defaultMailRecipient.value)) {
		message += 'Kein Pfad eingegeben!<br>';
	} 
	
  	if (!pruefeString(document.systemadministrationEditMail.countViewLastRecipients.value)) {
		message += 'Kein Schlüssel eingegeben!';
 	} 
  	
	if (!pruefeString(document.systemadministrationEditMail.splitSize.value)) {
		message += 'Kein Dateiname eingegeben!<br>';
	} 
  	
  	//----------------

 	if (message != '') {
 		document.getElementById('message').innerHTML = message;
 		return false;
 	}else
 		return true;
 	
}

function pruefeLogin() {
	var message = '';
	
	if (!pruefeString(document.loginform.name.value)) {
		message += 'Kein Benutzername eingegeben!<br>';
	} else {
	 	if (!pruefeStringHat4(document.loginform.name.value)) {
			message += 'Benutzername zu kurz. Benutzername kleiner als 4!<br>';
 		}
	}
	
  	if (!pruefeString(document.loginform.password.value)) {
		message += 'Kein Passwort eingegeben!';
 	} else {
 		if (!pruefeStringHat4(document.loginform.password.value)) {
			message += 'Passwort zu kurz. Passwort kleiner als 4!';
 		}
 	}

 	if (message != '') {
 		document.getElementById('message').innerHTML = message;
 		return false;
 	}else
 		return true;
 	
}

// dd/MM/YYYY
function pruefeDatum(input){
	var validformat=/^\d{2}\.\d{2}\.\d{4}$/
	var returnval=false
	
	if (!validformat.test(input)){
		//alert("Invalid Date Format. Please correct and submit again.")
	} else { 
		var dayfield=input.split(".")[0]
		var monthfield=input.split(".")[1]
		var yearfield=input.split(".")[2]
		
		var dayobj = new Date(yearfield, monthfield-1, dayfield)
		
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield)) {
			//alert("Invalid Day, Month, or Year range detected. Please correct and submit again.")
		} else {
			returnval = true
		}
	}
	//if (returnval == false) input.select()
	return returnval
}

// good@email.de
function pruefeEmail(email) {
	if ( (email.lastIndexOf(".") > 2) && (email.indexOf("@") > 0) && (email.lastIndexOf(".") > (email.indexOf("@")+1)) ) {
		return true
	} else {
		//alert("Invalid Email")
		return false
	}
}

function pruefeMatrikelnummer(matrikelnr){
	if (matrikelnr.length == 6){
	if(!isNaN(matrikelnr)){
			return true;
	}
	}
	return false;
}

function pruefeString(string){
	if (string.length > 0){
		return true;
	}
	return false;
}

function pruefeStringHat4(string){
	if (string.length >= 4){
		return true;
	}
	return false;
}

function pruefeStringHat10(string){
	if (string.length <= 10 &&  string.length >0){
		return true;
	}
	return false;
}