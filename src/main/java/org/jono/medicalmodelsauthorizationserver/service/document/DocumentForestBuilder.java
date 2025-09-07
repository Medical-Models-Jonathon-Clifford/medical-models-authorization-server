package org.jono.medicalmodelsauthorizationserver.service.document;

import java.util.List;
import org.jono.medicalmodelsauthorizationserver.model.Document;
import org.jono.medicalmodelsauthorizationserver.model.DocumentRelationship;
import org.jono.medicalmodelsauthorizationserver.service.ForestBuilder;

public class DocumentForestBuilder {

    public static List<DocumentTree> buildForest(final List<Document> documentList,
            final List<DocumentRelationship> documentRelationshipList) {
        return ForestBuilder.buildForest(documentList, documentRelationshipList, DocumentTree::new,
                                         DocumentTree::getChildren);
    }
}
