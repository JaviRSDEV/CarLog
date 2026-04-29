package com.carlog.backend.service;

import com.carlog.backend.error.WorkOrderNotFoundException;
import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderLine;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final SpringTemplateEngine springTemplateEngine;

    public byte[] generateInvoicePdf(Long workOrderId) throws Exception{
        WorkOrder workOrder = workOrderJpaRepository.findById(workOrderId)
                .orElseThrow(() -> new WorkOrderNotFoundException());

        double imponibleBase = 0.0;
        double totalIva = 0.0;
        double totalDiscount = 0.0;

        for(WorkOrderLine line : workOrder.getLines()){
            double subTotalLine = line.getQuantity() * line.getPricePerUnit();
            double ivaAmount = (subTotalLine * line.getIva()) / 100;
            double subTotalWithoutDiscount = subTotalLine + ivaAmount;
            double discountAmount = (subTotalWithoutDiscount * line.getDiscount()) / 100;

            imponibleBase += subTotalLine;
            totalIva += ivaAmount;
            totalDiscount += discountAmount;
        }

        Context context = new Context();
        context.setVariable("order", workOrder);
        context.setVariable("client", workOrder.getVehicle().getOwner());
        context.setVariable("vehicle", workOrder.getVehicle());
        context.setVariable("workshop", workOrder.getWorkshop());

        context.setVariable("imponibleBase", imponibleBase);
        context.setVariable("totalIva", totalIva);
        context.setVariable("totalDiscount", totalDiscount);

        context.setVariable("totalAmount", workOrder.getTotalAmount());

        String html = springTemplateEngine.process("invoice-template", context);

        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }

    }
}
