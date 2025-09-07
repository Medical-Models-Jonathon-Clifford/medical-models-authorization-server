package org.jono.medicalmodelsauthorizationserver.model;

import lombok.Data;
import org.jono.medicalmodelsauthorizationserver.service.NodeRelationship;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("document_relationship")
@Data
public class DocumentRelationship implements NodeRelationship {
    @Id
    private String id;
    private String parentDocumentId;
    private String childDocumentId;

    public DocumentRelationship(final String parentDocumentId, final String childDocumentId) {
        this.parentDocumentId = parentDocumentId;
        this.childDocumentId = childDocumentId;
    }

    @Override
    public String getParentId() {
        return parentDocumentId;
    }

    @Override
    public String getChildId() {
        return childDocumentId;
    }
}
