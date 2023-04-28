package com.jet.notification.repositories

import com.jet.notification.entities.MobileDeviceEntity
import org.hibernate.annotations.QueryHints.READ_ONLY
import org.hibernate.jpa.QueryHints.HINT_CACHEABLE
import org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.QueryHint

interface MobileDeviceRepository : JpaRepository<MobileDeviceEntity, UUID> {

    fun findByDeviceId(deviceId: String): MobileDeviceEntity?

    @Query(
        """
            SELECT DISTINCT md.id
            FROM MobileDeviceEntity md
            WHERE md.firebaseRegistrationTokenUpdatedAt <= :firebaseRegistrationTokenExpiryDateTime
            ORDER BY md.id
        """
    )
    @QueryHints(
        value = [
            QueryHint(name = HINT_CACHEABLE, value = "false"),
            QueryHint(name = READ_ONLY, value = "true"),
            QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false")
        ]
    )
    fun findIdsWithExpiredFirebaseRegistrationToken(
        firebaseRegistrationTokenExpiryDateTime: OffsetDateTime,
        pageable: Pageable,
    ): Page<UUID>

    @QueryHints(
        value = [
            QueryHint(name = HINT_CACHEABLE, value = "false"),
            QueryHint(name = READ_ONLY, value = "true"),
        ]
    )
    @EntityGraph(attributePaths = ["topics"])
    fun findAllWithTopicsByIdIn(ids: Collection<UUID>): List<MobileDeviceEntity>
}
