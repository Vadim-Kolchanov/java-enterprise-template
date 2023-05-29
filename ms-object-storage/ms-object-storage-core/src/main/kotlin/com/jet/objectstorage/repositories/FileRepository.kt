package com.jet.objectstorage.repositories

import com.jet.objectstorage.entities.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FileRepository : JpaRepository<FileEntity, UUID>
