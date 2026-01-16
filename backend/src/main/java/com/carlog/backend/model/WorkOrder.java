package com.carlog.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String mechanicNotes;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDate closedAt;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = false)
    private User mechanic;

    @ManyToOne
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @Builder.Default
    private Double totalAmount = 0.0;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkOrderLine> lines = new ArrayList<>();

    public void addWorkOrderLine(WorkOrderLine line){

        if(this.lines == null){
            this.lines = new java.util.ArrayList<>();
        }

        this.lines.add(line);
        line.setWorkOrder(this);

        double subTotalLine = line.getQuantity() * line.getPricePerUnit();

        double IVAPercent = (subTotalLine * line.getIVA())/100;
        double subTotalWithoutDiscount = subTotalLine + IVAPercent;

        double discountPercent = (subTotalWithoutDiscount * line.getDiscount())/100;
        double discountAmount = subTotalWithoutDiscount - discountPercent;

        line.setSubTotal(discountAmount);

        this.totalAmount += discountAmount;
    }

    public void removeWorkOrderLine(WorkOrderLine line){
        this.lines.remove(line);

        line.setWorkOrder(null);

        if(line.getSubTotal() != null){
            this.totalAmount -= line.getSubTotal();
        }

        if(this.totalAmount < 0)
            this.totalAmount = 0.0;

        this.totalAmount = (this.totalAmount * 100)/100;
    }
}
