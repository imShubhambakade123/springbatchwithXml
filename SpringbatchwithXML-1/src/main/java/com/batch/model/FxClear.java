package com.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ROW")
public class FxClear {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srno")
    private int SRNO;
	
	@Column(name = "mbm_mbr_id")
	private String MBM_MBR_ID;
	
	@Column(name = "mbm_mbr_name")
	private String MBM_MBR_NAME;

}
