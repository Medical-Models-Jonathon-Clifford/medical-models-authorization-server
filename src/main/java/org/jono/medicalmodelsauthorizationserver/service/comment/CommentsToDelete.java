package org.jono.medicalmodelsauthorizationserver.service.comment;

import java.util.List;
import lombok.Builder;

@Builder
record CommentsToDelete(
        List<String> commentIds,
        List<String> commentRelationshipIds
) {}
