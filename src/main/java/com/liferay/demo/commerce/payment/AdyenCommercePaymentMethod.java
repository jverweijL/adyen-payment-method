package com.liferay.demo.commerce.payment;


import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jan Verweij
 */
@Component(
	immediate = true,
	property = {
		"commerce.payment.engine.method.key=" + AdyenCommercePaymentMethod.KEY
	},
	service = CommercePaymentMethod.class
)
public class AdyenCommercePaymentMethod implements CommercePaymentMethod {

	public static final String KEY = "adyen-payment-method";

	@Override
	public CommercePaymentResult cancelPayment(
			CommercePaymentRequest commercePaymentRequest)
			throws Exception {

		return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.ORDER_STATUS_CANCELLED, false, null, null,
				Collections.emptyList(), true);
	}

	@Override
	public CommercePaymentResult completePayment(
	        // Implement custom payment completion logic in this method.
            // CommercePaymentResult is a container that stores information relevant to
            // the completion of a payment process. See CommercePaymentResult.java for more information.

			CommercePaymentRequest commercePaymentRequest)
			throws Exception {

		AdyenCommercePaymentRequest adyenPaymentRequest =
				(AdyenCommercePaymentRequest)commercePaymentRequest;

		return new CommercePaymentResult(
				null, adyenPaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null, null,
				Collections.emptyList(), true);
	}

	@Override
	public String getDescription(Locale locale) {
	    // This method populates the "Description" column in the Payment Methods administration page.

		ResourceBundle resourceBundle = _getResourceBundle(locale);
		return LanguageUtil.get(resourceBundle, "adyen-description");
	}

	@Override
	public String getKey() {
		// This provides a unique identifier for the payment method in the payment method registry.
        // The key can be used to fetch the payment method from the registry.
        // Reusing a key that is already in use will override the existing associated payment method.

	    return KEY;
	}

	@Override
	public String getName(Locale locale) {
	    // This populates the "Name" column in the Payment Methods administration page.
        // It works similarly to the getDescription method.
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, KEY);
	}

	@Override
	public int getPaymentType() {
        //This identifies how the payment engine will use a given payment method.
        // We use the value COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE to inform the payment engine that there are
        // no online processing requirements for this payment method.
        // There are two other payment type constants available out-of-the-box:
        // COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_STANDARD
        // and
        // COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT.
	    return CommercePaymentConstants.COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT;
	}

	@Override
	public String getServletPath() {
		// is used to identify the servlet that manages the payment. It is required by the interface but does nothing in this case.
		_log.debug("servletpath is needed");
		return "does_it_work";
	}

	@Override
	public boolean isCancelEnabled() { return true; }

	@Override
	public boolean isCompleteEnabled() {

	    // This must return true for the payment method to complete payments.
        // This informs the payment engine what functionality is supported by our payment method.

        return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		// This method must return true for the payment method to process payments.
        // This informs the payment engine what functionality is supported by our payment method.

	    return true;
	}

	@Override
	public CommercePaymentResult processPayment(CommercePaymentRequest commercePaymentRequest) throws Exception {
	    // Implement custom payment logic in this method.
        // The CommercePaymentResult should store information relevant to the processing of a payment.

		_log.debug("processPayment being called, time to redirect?");
		_log.debug("commercePaymentRequest: " +  commercePaymentRequest);

		AdyenCommercePaymentRequest adyenPaymentRequest =
				(AdyenCommercePaymentRequest)commercePaymentRequest;

		_log.debug("adyenPaymentRequest: " +  adyenPaymentRequest);
		_log.debug("OrderID: " + adyenPaymentRequest.getCommerceOrderId());

		//TODO put this in property or config
		String url = "http://localhost:8888/web/clicks-n-bricks/adyen";
		_log.debug("Redirect url: " + url);


		return new CommercePaymentResult(
				"ABC123", adyenPaymentRequest.getCommerceOrderId(),
				-1, true, url, null,
				Collections.emptyList(), true);


	}

	/*private String _getServletUrl(
			commercePaymentRequest commercePaymentRequest) {

		return StringBundler.concat(
				_portal.getPortalURL(
						commercePaymentRequest.getHttpServletRequest()),
				_portal.getPathModule(), StringPool.SLASH,
				AuthorizeNetCommercePaymentMethodConstants.
						START_PAYMENT_SERVLET_PATH);
	}
*/
	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());
	}

	@Reference
	private Portal _portal;

	private static final Log _log = LogFactoryUtil.getLog(AdyenCommercePaymentMethod.class);

}