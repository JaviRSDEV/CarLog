package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_order_line")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String concept;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(nullable = false)
    @Builder.Default
    private Double IVA = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double discount = 0.0;

    @Column
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @PreUpdate
    public void calculateSubTotal(){
        if(this.quantity != null && this.pricePerUnit != null){
            double priceWithoutIVA = this.quantity * this.pricePerUnit;
            double IVAUnit = (priceWithoutIVA * this.IVA)/100;
            double subTotalWithoutDiscount = priceWithoutIVA + IVAUnit;
            double discountAmount = (subTotalWithoutDiscount * this.discount)/100;

            this.subTotal = subTotalWithoutDiscount - discountAmount;
        }else{
            this.subTotal = 0.0;
        }
    }
}
