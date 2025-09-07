package org.jono.medicalmodelsauthorizationserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jono.medicalmodelsauthorizationserver.model.DocumentState;

@AllArgsConstructor
@Data
public class DocumentDto {
    private String title;
    private String body;
    private DocumentState state;
}
