package com.liferay.demo.commerce.payment;

import CommercePaymentMethod;

import org.osgi.service.component.annotations.Component;

/**
 * @author jverweij
 */
@Component(
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = CommercePaymentMethod.class
)
public class AdyenCommercePaymentMethod implements CommercePaymentMethod {

	// TODO enter required service methods

}