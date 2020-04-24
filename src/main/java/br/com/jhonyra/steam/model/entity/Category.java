package br.com.jhonyra.steam.model.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CATEGORY")
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", nullable = false, length = 255)
	private String name;
	
	@Embedded
	private DataControl dataControl;
	
	public DataControl getDataControl () {
		if(this.dataControl == null) {
			this.dataControl = new DataControl();
		}
		
		return this.dataControl;
	}
	
}
