package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrderLine;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewWorkOrderLineDTO(
                                  @NotBlank(message = "El concepto no puede estar vacio")
                                  String concept,

                                  @NotNull
                                  @Min(value = 0, message = "La cantidad debe ser positiva")
                                  Double quantity,

                                  @NotNull
                                  @Min(value = 0, message = "El precio debe ser positivo")
                                  Double pricePerUnit
                                  ) {
    public static NewWorkOrderLineDTO of(WorkOrderLine wol){
        return new NewWorkOrderLineDTO(
                    wol.getConcept(),
                    wol.getQuantity(),
                    wol.getPricePerUnit()
        );
    }
}
