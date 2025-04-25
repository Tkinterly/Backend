package com.tinkerly.tinkerly.components;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class PriceGenerator {
    public static int generate(int recommendedPrice, int workerExperience, Date registrationDate) {
        int platformPresence = Period.between(
                registrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        ).getMonths();
        double experienceFactor = 0.2 * (1 + Math.log10(1 + workerExperience));
        double estimatedPrice = 0.9 * recommendedPrice * (1 + experienceFactor * Math.log10(1 + platformPresence));

        return (int) (Math.round(estimatedPrice / 10) * 10);
    }

    public static int generate(int recommendedPrice, int workerExperience, Date registrationDate, int biddingTier) {
        int platformPresence = Period.between(
                registrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        ).getMonths();
        double biddingPrice = recommendedPrice * (1 - (biddingTier - 2)  * 0.025);
        double experienceFactor = 0.2 * (1 + Math.log10(1 + workerExperience));
        double estimatedPrice = 0.9 * biddingPrice * (1 + experienceFactor * Math.log10(1 + platformPresence));

        return (int) (Math.round(estimatedPrice / 10) * 10);
    }
}
