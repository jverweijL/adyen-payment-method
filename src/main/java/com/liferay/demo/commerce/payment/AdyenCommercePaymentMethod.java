package com.liferay.demo.commerce.payment;


import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;

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
	
	@Override
	public CommercePaymentResult completePayment(CommercePaymentRequest commercePaymentRequest) throws Exception {
		return null;
	}

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getName(Locale locale) {
		return null;
	}

	@Override
	public int getPaymentType() {
		return 0;
	}

	@Override
	public String getServletPath() {
		return null;
	}

	@Override
	public boolean isCompleteEnabled() {
		return false;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return false;
	}

	// TODO enter required service methods

}