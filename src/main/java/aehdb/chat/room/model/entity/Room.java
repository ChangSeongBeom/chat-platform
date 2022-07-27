package aehdb.chat.room.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.JoinFormula;

import aehdb.chat.message.model.entity.Message;
import aehdb.comm.model.converter.TFCode;
import aehdb.comm.model.converter.TFCodeConverter;
import aehdb.comm.model.entity.BaseEntity;
import aehdb.mng.legacy.model.entity.Legacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "UUID")
	private UUID roomUuid;

	@Convert(converter = TFCodeConverter.class)
	@Column(columnDefinition = "char")
	private TFCode isClosed = TFCode.FALSE;

	@Column(length = 50)
	@NotNull
	private String roomNm;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_id") // 외래키
	private Legacy legacy;

	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinFormula("(" +
		    "SELECT ms.id  " +
		    "FROM message ms " +
		    "WHERE ms.room_uuid = room_uuid " +
		    "ORDER BY ms.create_date DESC " +
		    "LIMIT 1" +
		")")
	private Message lastMessage;
	
	@PrePersist
	public void autoUUIDFill() {
		this.setRoomUuid(UUID.randomUUID());
	}
	// 생성자 이용할까 하다가 이왕이면 이게 맞다고 생각이 드는건..? 내 생각이 맞았다.
	// the constructor method is called when you create the object in memory.
	// the prePersist event is fired right before you persist it into database.
	
	
	
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "legacy")
//	private List<Room> room;
}
