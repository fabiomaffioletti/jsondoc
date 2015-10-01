package org.jsondoc.core.util;

import java.lang.reflect.Field;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;

public class JSONDocHibernateValidatorProcessor {

	private final static String AssertFalse_message = "must be false";
	private final static String AssertTrue_message = "must be true";
	private final static String DecimalMax_message = "must be less than %s %s";
	private final static String DecimalMin_message = "must be greater than %s %s";
	private final static String Digits_message = "numeric value made of <%s digits>.<%s digits>)";
	private final static String Future_message = "must be in the future";
	private final static String Max_message = "must be less than or equal to %s";
	private final static String Min_message = "must be greater than or equal to %s";
	private final static String NotNull_message = "may not be null";
	private final static String Null_message = "must be null";
	private final static String Past_message = "must be in the past";
	private final static String Pattern_message = "must match %s";
	private final static String Size_message = "size must be between %s and %s";
	private final static String Email_message = "must be a well-formed email address";
	private final static String Length_message = "length must be between %s and %s";
	private final static String NotBlank_message = "may not be empty";
	private final static String NotEmpty_message = "may not be empty";
	private final static String Range_message = "must be between %s and %s";
	private final static String URL_message = "must be a valid URL";
	private final static String CreditCardNumber_message = "must be a valid credit card number";
	private final static String ScriptAssert_message = "script expression %s didn't evaluate to true";

	public static void processHibernateValidatorAnnotations(Field field, ApiObjectFieldDoc apiPojoFieldDoc) {
		try {
			Class.forName("org.hibernate.validator.constraints.NotBlank");

			if (field.isAnnotationPresent(AssertFalse.class)) {
				apiPojoFieldDoc.addFormat(AssertFalse_message);
			}

			if (field.isAnnotationPresent(AssertTrue.class)) {
				apiPojoFieldDoc.addFormat(AssertTrue_message);
			}
			
			if (field.isAnnotationPresent(NotNull.class)) {
				apiPojoFieldDoc.addFormat(NotNull_message);
			}

			if (field.isAnnotationPresent(Null.class)) {
				apiPojoFieldDoc.addFormat(Null_message);
			}
			
			if (field.isAnnotationPresent(Size.class)) {
				Size annotation = field.getAnnotation(Size.class);
				apiPojoFieldDoc.addFormat(String.format(Size_message, annotation.min(), annotation.max()));
			}
			
			if (field.isAnnotationPresent(NotBlank.class)) {
				apiPojoFieldDoc.addFormat(NotBlank_message);
			}

			if (field.isAnnotationPresent(NotEmpty.class)) {
				apiPojoFieldDoc.addFormat(NotEmpty_message);
			}
			
			if (field.isAnnotationPresent(Length.class)) {
				Length annotation = field.getAnnotation(Length.class);
				apiPojoFieldDoc.addFormat(String.format(Length_message, annotation.min(), annotation.max()));
			}

			if (field.isAnnotationPresent(Range.class)) {
				Range annotation = field.getAnnotation(Range.class);
				apiPojoFieldDoc.addFormat(String.format(Range_message, annotation.min(), annotation.max()));
			}

			if (field.isAnnotationPresent(DecimalMax.class)) {
				DecimalMax annotation = field.getAnnotation(DecimalMax.class);
				apiPojoFieldDoc.addFormat(String.format(DecimalMax_message, annotation.inclusive() ? "or equal to" : "", annotation.value()));
			}

			if (field.isAnnotationPresent(DecimalMin.class)) {
				DecimalMin annotation = field.getAnnotation(DecimalMin.class);
				apiPojoFieldDoc.addFormat(String.format(DecimalMin_message, annotation.inclusive() ? "or equal to" : "", annotation.value()));
			}

			if (field.isAnnotationPresent(Future.class)) {
				apiPojoFieldDoc.addFormat(Future_message);
			}

			if (field.isAnnotationPresent(Past.class)) {
				apiPojoFieldDoc.addFormat(Past_message);
			}

			if (field.isAnnotationPresent(Digits.class)) {
				Digits annotation = field.getAnnotation(Digits.class);
				apiPojoFieldDoc.addFormat(String.format(Digits_message, annotation.integer(), annotation.fraction()));
			}

			if (field.isAnnotationPresent(Max.class)) {
				Max annotation = field.getAnnotation(Max.class);
				apiPojoFieldDoc.addFormat(String.format(Max_message, annotation.value()));
			}

			if (field.isAnnotationPresent(Min.class)) {
				Min annotation = field.getAnnotation(Min.class);
				apiPojoFieldDoc.addFormat(String.format(Min_message, annotation.value()));
			}

			if (field.isAnnotationPresent(Pattern.class)) {
				Pattern annotation = field.getAnnotation(Pattern.class);
				apiPojoFieldDoc.addFormat(String.format(Pattern_message, annotation.regexp()));
			}

			if (field.isAnnotationPresent(Email.class)) {
				apiPojoFieldDoc.addFormat(Email_message);
			}

			if (field.isAnnotationPresent(URL.class)) {
				apiPojoFieldDoc.addFormat(URL_message);
			}

			if (field.isAnnotationPresent(CreditCardNumber.class)) {
				apiPojoFieldDoc.addFormat(CreditCardNumber_message);
			}

		} catch (ClassNotFoundException e) {
			// nothing to do here
		}

	}

}
