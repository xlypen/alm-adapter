package ru.tsc.almadapter.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.tsc.almadapter.dto.ExtValues;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Getter
@Setter
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value_id")
    private Long valueId;

    @Column(name = "dictionary_id")
    private Long dictionaryId;

    @Column(name = "version")
    private Long version;

    @Column(name = "internal_id")
    private Integer internalId;

    @Column(name = "hash")
    private String hash;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "value")
    private String value;

    @Column(name = "ext_values", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private ExtValues extValues;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_start")
    private Timestamp dateStart;

    @Column(name = "date_end")
    private Timestamp dateEnd;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "heritage_id")
    private String heritageId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "record_id")
    private Long recordId;

}