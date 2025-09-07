package org.jono.medicalmodelsauthorizationserver.service.comment;

interface DeletionStrategy {
    CommentsToDelete execute();
}
