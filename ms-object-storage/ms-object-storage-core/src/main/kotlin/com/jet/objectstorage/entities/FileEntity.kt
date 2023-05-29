package com.jet.objectstorage.entities

import com.jet.common.entities.UUIDAbstractEntity
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "file")
class FileEntity(
    id: UUID = UUID.randomUUID(),
    val folder: String,
    val fileName: String,
    val uri: String,
    val contentType: String,
    val sizeInBytes: Long,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
) : UUIDAbstractEntity(id)
