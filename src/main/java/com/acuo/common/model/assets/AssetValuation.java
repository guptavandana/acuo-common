package com.acuo.common.model.assets;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetValuation {

    private double yield;
    private double price;
    private LocalDate valuationDateTime;
    private String priceQuotationType;
    private String source;
    private Currency nominalCurrency;
    private Currency reportCurrency;
    private double coupon;

}
