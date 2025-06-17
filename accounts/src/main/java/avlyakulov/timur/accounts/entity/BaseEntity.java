package avlyakulov.timur.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass //указывает, что данный класс не является сущностью, но его поля должны быть унаследованы и включены в маппинг других сущностей.
public class BaseEntity {

    @Column(name = "created_at", updatable = false) //update false - Это поле нельзя обновлять при UPDATE запросе в базу данных. То есть это поле было создано 1 раз
    private LocalDateTime createdAt;
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
    @Column(name = "updated_by", insertable = false)
    private String updatedBy;
}
