package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrderLine;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

public record NewWorkOrderLineDTO(
                                  @NotBlank(message = "El concepto no puede estar vacio")
                                  String concept,

                                  @NotNull
                                  @Min(value = 0, message = "La cantidad debe ser positiva")
                                  Double quantity,

                                  @NotNull
                                  @Min(value = 0, message = "El precio debe ser positivo")
                                  Double pricePerUnit,

                                  @Min(value = 0, message = "El IVA debe ser mayor o igual a 0")
                                  Double IVA,

                                  @Min(value = 0, message = "El descuento debe ser mayor o igual a 0")
                                  @Max(value = 100, message = "El descuento no puede ser mayor al 100%")
                                  Double discount
                                  ) {
    public static NewWorkOrderLineDTO of(WorkOrderLine wol){
        return new NewWorkOrderLineDTO(
                    wol.getConcept(),
                    wol.getQuantity(),
                    wol.getPricePerUnit(),
                    wol.getIVA(),
                    wol.getDiscount()
        );
    }
}
