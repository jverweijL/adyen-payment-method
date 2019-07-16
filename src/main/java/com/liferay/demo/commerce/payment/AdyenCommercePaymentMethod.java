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
 * @author jverweij
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
	public CommercePaymentResult completePayment(CommercePaymentRequest commercePaymentRequest) throws Exception {
		_log.debug("completePayment being called, time to redirect?");
		return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null, null,
				Collections.emptyList(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "adyen-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, KEY);
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentConstants.COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT;
	}

	@Override
	public String getServletPath() {
		// is used to identify the servlet that manages the payment. It is required by the interface but does nothing in this case.
		return null;
	}

	@Override
	public boolean isCompleteEnabled() {
		return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return true;
	}

	@Override
	public CommercePaymentResult processPayment(CommercePaymentRequest commercePaymentRequest) throws Exception {
		_log.debug("processPayment being called, time to redirect?");
		_log.debug("commercePaymentRequest: " +  commercePaymentRequest);

		AdyenPaymentRequest adyenPaymentRequest =
				(AdyenPaymentRequest)commercePaymentRequest;

		_log.debug("adyenPaymentRequest: " +  adyenPaymentRequest);

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