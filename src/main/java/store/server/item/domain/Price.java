package store.server.item.domain;

import lombok.Data;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Embeddable
public class Price implements Serializable, Comparable<Price> {

    @NotNull
    @Positive
    private Double value;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Max(100)
    @Positive
    private Integer discount;

    @Override
    public int compareTo(Price other) {
        return this.createMonetaryAmount().compareTo(other.createMonetaryAmount());
    }

    private MonetaryAmount createMonetaryAmount() {
        CurrencyConversion conversion = MonetaryConversions.getConversion(Currency.EUR.name());

        return Monetary.getDefaultAmountFactory()
                .setCurrency(currency.name())
                .setNumber(value)
                .create()
                .with(conversion);
    }

}
