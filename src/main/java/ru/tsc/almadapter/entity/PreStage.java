package ru.tsc.almadapter.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Table(name = "pre_stage")
@Data
public class PreStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_stage_id")
    private Long preStageId;

    @Column(name = "dictionary_id")
    private Long dictionaryId;

    @Column(name = "json_data", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private String jsonData;

    @Column(name = "load_date")
    private Timestamp loadDate;

    @Column(name = "pre_stage_status_id")
    private Long preStageStatusId;

    @Column(name = "pre_stage_type")
    private Integer preStageType;

    @Column(name = "raw_data")
    private String rawData;

    @Column(name = "user_id")
    private Long userId;

}
