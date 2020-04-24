package br.com.jhonyra.steam.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "GAME")
@Data
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",  nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = true, columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "RELEASE_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date release_date;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEVELOPER_ID", nullable = false)
	private Developer developer;
	
	@Embedded
	private DataControl dataControl;
	
	public DataControl getDataControl () {
		if(this.dataControl == null) {
			this.dataControl = new DataControl();
		}
		
		return this.dataControl;
	}
	
}