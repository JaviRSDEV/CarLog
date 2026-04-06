package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrderLine;

public record NewWorkOrderLineResponseDTO(
                                          Long id,
                                          String concept,
                                          Double quantity,
                                          Double pricePerUnit,
                                          Double IVA,
                                          Double discount,
                                          Double subTotal) {

    public static NewWorkOrderLineResponseDTO of(WorkOrderLine wol){
        return new NewWorkOrderLineResponseDTO(
                wol.getId(),
                wol.getConcept(),
                wol.getQuantity(),
                wol.getPricePerUnit(),
                wol.getIVA(),
                wol.getDiscount(),
                wol.getSubTotal()
        );}
}
