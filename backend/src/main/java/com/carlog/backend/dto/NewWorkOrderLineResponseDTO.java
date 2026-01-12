package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrderLine;

public record NewWorkOrderLineResponseDTO(
                                          String concept,
                                          Double quantity,
                                          Double pricePerUnit,
                                          Double subTotal) {

    public static NewWorkOrderLineResponseDTO of(WorkOrderLine wol){
        return new NewWorkOrderLineResponseDTO(
                wol.getConcept(),
                wol.getQuantity(),
                wol.getPricePerUnit(),
                wol.getSubTotal()
        );}
}
