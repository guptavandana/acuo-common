package com.acuo.common.model.margin;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Recall {

    private String callAmpId;
    private String recallAmpId;
    private String securityId;
    private Types.SecurityIdType securityIdType;
    private String deliveryType;
    private double quantity;
    private Currency FXCurrency;
    private double currentMarketValue;
    private double adjustedCollateralValue;
    private LocalDate settlementDate;
    private double FXRate;
    private double price;
    private double haircut;
    private String externalUsername;
    private int rejectReasonCode;
    private int version;

}