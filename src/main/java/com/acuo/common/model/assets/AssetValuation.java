package com.acuo.common.model.assets;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by lucie on 15/3/17.
 */
@Data
public class AssetValuation {

    private double yield;
    private double price;
    private LocalDate valuationDateTime;
    private String priceQuotationType;

}