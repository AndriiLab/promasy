package com.github.andriilab.promasy.domain.bid.entities;

import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * POJO for data of finance amount spent on each cpv code
 */
public class CpvAmount {

    @Getter private final List<Bid> bids = new LinkedList<>();
    @Getter @Setter private BidType type;
    @Getter @Setter private Cpv cpv;
    @Getter private BigDecimal totalAmount;

    private CpvAmount(Cpv cpv, BidType type, BigDecimal totalAmount) {
        this.cpv = cpv;
        this.type = type;
        this.totalAmount = totalAmount;
    }

    public CpvAmount(Cpv cpv, BidType type, BigDecimal totalAmount, Bid model) {
        this(cpv, type, totalAmount);
        this.addBidModel(model);
    }

    public CpvAmount() {
        this.totalAmount = BigDecimal.ZERO;
    }

    /**
     * Groups bids by 4-digit CPV group and bid type, summing their total prices.
     *
     * @param cpvLookup resolves an 8-character CPV code (4-digit group padded with zeros) to its {@link Cpv}
     * @return unmodifiable list sorted by bid type, then CPV code
     */
    public static List<CpvAmount> groupByCpvAndType(List<Bid> bids, Function<String, Cpv> cpvLookup) {
        Map<String, CpvAmount> map = new HashMap<>();

        for (Bid bid : bids) {
            String cpvCode = bid.getCpv().getCpvId().substring(0, 4) + "0000";
            BidType bidType = bid.getType();
            BigDecimal bidAmount = bid.getTotalPrice();

            String key = cpvCode + " " + bidType;
            CpvAmount cpvAmount = map.get(key);
            if (cpvAmount != null) {
                cpvAmount.addBidModel(bid);
                cpvAmount.addToTotalAmount(bidAmount);
            } else {
                map.put(key, new CpvAmount(cpvLookup.apply(cpvCode), bidType, bidAmount, bid));
            }
        }

        return Collections.unmodifiableList(map.values().stream()
                .sorted(Comparator.comparing(CpvAmount::getType)
                        .thenComparing(m -> m.getCpv().getCpvId()))
                .collect(Collectors.toList()));
    }

    public void addToTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = this.totalAmount.add(totalAmount);
    }

    public void addBidModel(Bid model) {
        int indexOfModel = bids.indexOf(model);
        if (indexOfModel != -1) {
            bids.set(indexOfModel, model);
        } else {
            bids.add(model);
        }
    }

    @Override
    public String toString() {
        return "CpvAmount{" +
                "cpv=" + cpv.getCpvId() +
                ", type=" + type +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
