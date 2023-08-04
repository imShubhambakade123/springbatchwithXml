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
public class CBLO {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srno")
	private int SRNO;
	
	@Column(name = "mbm_mbr_id")
	private String MBM_MBR_ID;
	
	@Column(name = "mbm_mbr_name")
	private String MBM_MBR_NAME;
	
	@Column(name = "mbm_cblo_mbr_id")
	private String MBM_CBLO_MBR_ID;
	
	@Column(name = "mbp_bsns_sgmnt")
	private String MBP_BSNS_SGMNT;
	
	@Column(name = "addr1")
	private String ADDR1;
	
	@Column(name = "tphone1")
	private String TPHONE1;
	
	@Column(name = "faxno")
	private String FAXNO;
	
	@Column(name = "emailid")
	private String EMAILID;
	
	@Column(name = "username")
	private String USERNAME;

	@Column(name = "domain")
	private String DOMAIN;
}
