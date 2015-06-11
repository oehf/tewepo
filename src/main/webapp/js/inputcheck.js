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
// Standard error
strMsg = 'Fehler';

arrFields = new Array();
// arrFields = new Array(required field?0:1, condition, error message);
// register new user

// Backup: password checking with 1 large and 1 no letter and a digit:
// /^((?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{6,20})$/

arrFields['username'] = new Array(
		1,
		/^[a-zA-Z0-9]{6,20}/,
		unescape('Bitte geben Sie einen gültigen Bentuzernamen ein!\nDer Benutzername muss mindestens 6 Zeichen lang sein und darf keine Umlaute oder Sonderzeichen enthalten. '));

//(?=.*[A-Z])
arrFields['password_1'] = new Array(
		1,
		/^((?=.*\d)(?=.*[a-z])(?=.+[@#!?*$%^&§+=.,])(?=.+[0-9]).{6,20})$/,
		unescape('Bitte geben Sie ein gültiges Passwort ein!\nDas Passwort muss mindestens 6 Zeichen lang sein und eine Zahl und ein Sonderzeichen (@#!?*$%^&§+=.,) enthalten!'));

		///^((?=.*[^a-zA-Z])(?=.*[a-z])(?!.*\s).{6,20})$/ (?=.*[A-Z])
arrFields['password_2'] = new Array(
		1,
		/^((?=.*\d)(?=.*[a-z])(?=.+[@#!?*$%^&§+=.,])(?=.+[0-9]).{6,20})$/,
		unescape('Passworteingabe fehlerhaft! Bitte geben Sie ein gültiges Passwort ein!\nDie Passwortbest%E4tigung muss mit dem Passwort %FCbereinstimmen!'));

arrFields['title'] = new Array(1, /^([a-zA-ZÄÖÜäöüß\- ().]{2,30})?$/i,
'Bitte geben Sie Ihren Titel ein!');

arrFields['forename'] = new Array(1, /^[a-zA-ZÄÖÜäöüß\- ]{2,30}/,
'Bitte geben Sie Ihren Vornamen ein!');

arrFields['surname'] = new Array(1, /^[a-zA-ZÄÖÜäöüß\- ]{2,30}/,
		unescape('Bitte geben Sie Ihren Nachnamen ein!'));

arrFields['occupationgroup'] = new Array(1, /^[a-zA-ZÄÖÜäöüß0-9\- .]{2,60}/,
		'Bitte geben Sie Ihre Berufsgruppe ein!');

arrFields['email'] = new Array(
		1,
		/^[a-z\d][a-z\d\._-]*@([a-z\d][a-z\d\.-]*[a-z\d]\.|)[a-z\d][a-z\d-]{1,}[a-z\d]\.[a-z]{2,5}$/i,
		unescape('Bitte geben Sie eine gültige E-Mail Adresse in der Form xxx@yyy.zz ein!'));

arrFields['emailORnot'] = new Array(
		1,
		/^([a-z\d][a-z\d\._-]*@([a-z\d][a-z\d\.-]*[a-z\d]\.|)[a-z\d][a-z\d-]{1,}[a-z\d]\.[a-z]{2,5})?$/i,
		unescape('Bitte geben Sie eine gültige E-Mail Adresse in der Form xxx@yyy.zz ein!'));

arrFields['organisation'] = new Array(1, /^[a-zA-ZÄÖÜäöüß0-9\- .]{2,60}/,
		'Bitte geben Sie den Namen Ihrer Organisation ein!');


arrFields['department'] = new Array(1, /^([a-zA-ZÄÖÜäöüß0-9\- .]{2,60})$/i,
		'Bitte geben Sie Ihre Abteilung an!');

arrFields['street'] = new Array(1, /^[a-zA-ZÄÖÜäöüß\- .]{3,30}/,
		'Bitte geben Sie die Strasse ein!');

arrFields['number'] = new Array(1, /^[.\d]+/,
		'Bitte geben Sie die Hausnummer ein!');

arrFields['zipcode'] = new Array(1, /^\d{5}$/,
		'Bitte geben Sie die korrekte Postleitzahl ein!');



// add new recipient
arrFields['institution'] = new Array(1, /^[a-zA-Z0-9ÄÖÜäöüß\- .]{2,30}/,
		'Bitte geben Sie die Einrichtung ein!');

arrFields['rec'] = new Array(1, /^[a-zA-Z0-9ÄÖÜäöüß\- .]{2,30}/,
'Bitte geben Sie den Empfänger ein!');

arrFields['location'] = new Array(1, /^[a-zA-ZÄÖÜäöüß\- .]{3,30}/,
'Bitte geben Sie den Ort ein!');

arrFields['publicKeyId'] = new Array(1, /^[a-zA-Z0-9ÄÖÜäöüß\- .]{2,30}/,
		'Bitte geben Sie die Schlüssel-ID ein!');

arrFields['mail_1'] = arrFields['emailORnot'];
arrFields['mail_2'] = arrFields['emailORnot'];

arrFields['mail_3'] = arrFields['emailORnot']

function dialog() {

	return confirm('Test');

}

/** remove unnecessary spaces */

function trim(strIn) {

	return (strIn.replace(/(^\s+|\s+$)/g, ''));

}

/**check text-input and textarea  */

function text_check(objElem, arrCheck) {

	objElem.value = trim(objElem.value);

	if (arrCheck[0] || objElem.value.length > 0) {
		if (!objElem.value.match(arrCheck[1])) {
			alert((arrCheck.length == 3) ? arrCheck[2] : strMsg);
			objElem.select();
			objElem.focus();
			return false;
		}
	}
	return true;

}

/** boxes checken */

function box_check(objElem, arrCheck) {

	intChecked = 0;
	arrBoxes = (typeof objElem.form.elements[objElem.name].length == 'number') ? objElem.form.elements[objElem.name]
			: new Array(objElem);

	for (e = 0; e < arrBoxes.length; ++e) {
		intChecked += (arrBoxes[e].checked) ? 1 : 0
	}

	if (!eval('intChecked' + arrCheck[1])) {
		alert((arrCheck.length == 3) ? arrCheck[2] : strMsg);
		arrBoxes[0].focus();
		return false;
	}
	return true;

}

/** Listen checken */

function list_check(objElem, arrCheck) {
	if (!eval('objElem.selectedIndex' + arrCheck[1])) {
		alert((arrCheck.length == 3) ? arrCheck[2] : strMsg);
		objElem.focus();
		return false;
	}
	return true;
}

/** located in objElem  objForm? */

function in_form(objElem, objForm) {

	return (typeof objElem.form == 'object' && objElem.form == objForm);

}

/** zu pr�fende Formularelemente ermitteln */

function get_field_type(objElem) {

	switch (objElem.tagName.toLowerCase()) {
	case "input":
		switch (objElem.type) {
		case "text":
			strFieldType = 'text';
			break;

		case "password":
			strFieldType = 'text';
			break;

		case "file":
			strFieldType = 'text';
			break;

		case "radio":
			strFieldType = 'box';
			break;

		case "checkbox":
			strFieldType = 'box';
			break;

		default:
			strFieldType = false;
			break;
		}
		break;

	case "select":
		strFieldType = 'list';
		break;

	case "textarea":
		strFieldType = 'text';
		break;

	default:
		strFieldType = false;
		break;
	}
	return strFieldType;

}

function check_form(objForm) {
	if (!document.getElementsByName) {

		return true;

	}

	for ( var strName in arrFields) {

		for (j = 0; j < document.getElementsByName(strName).length; ++j) {

			objElem = document.getElementsByName(strName)[j];

			if (in_form(objElem, objForm)) {

				if (!eval(get_field_type(objElem) + '_check(objElem,arrFields[strName])')) {

					return false;

				}
				if (get_field_type(objElem) == 'box') {

					continue;

				}
			}
		}
	}

	return true;

}

function check_emails(formIsValid, minMail){
	var mailFields = ["mail_1","mail_2","mail_3"];
	var counter = 0;
	
	if(formIsValid) {
		for (i = 0; i < mailFields.length; i++){
			var element = document.getElementsByName(mailFields[i]);
			if(element.length > 0 && element[0].value.length > 0){
				counter++;
			}	
		}
	}
	
	if(formIsValid && counter >= minMail) {
		return true;
	} else {
		alert('Bitte geben Sie mindestens eine E-Mail Adresse ein!');
		return false;
	}	
}

