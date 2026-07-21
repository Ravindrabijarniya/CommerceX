package com.ravindra.commercex.payment.provider;

import com.ravindra.commercex.payment.enums.PaymentProviderType;
import com.ravindra.commercex.payment.exception.UnsupportedPaymentProviderException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentProviderFactory {

    private final Map<PaymentProviderType, PaymentProvider> providers;

    public PaymentProviderFactory(
        List<PaymentProvider> paymentProviders
    ) {

        this.providers = new EnumMap<>(PaymentProviderType.class);

        paymentProviders.forEach(provider ->
            providers.put(
                provider.getProviderType(),
                provider
            )
        );
    }

    public PaymentProvider getProvider(
        PaymentProviderType providerType
    ) {

        PaymentProvider provider =
            providers.get(providerType);

        if (provider == null) {
            throw new UnsupportedPaymentProviderException(providerType);
        }

        return provider;
    }
}
