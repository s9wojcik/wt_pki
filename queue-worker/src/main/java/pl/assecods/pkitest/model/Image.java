package pl.assecods.pkitest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"id", "encodedName", "timeC"})
public class Image {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	private String name;
	
	@Column(name = "ENCODED_NAME")
	private String encodedName;
	
	private String url;
	
	@Column(name = "TIME_C")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeC;
	
	
	public Image() {
		
	}
	
	public Image(String userId, String name, String encodedName, String url, Date timeC) {
		this.userId = userId;
		this.name = name;
		this.encodedName = encodedName;
		this.url = url;
		this.timeC = timeC;
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEncodedName() {
		return encodedName;
	}

	public String getUrl() {
		return url;
	}

	public Date getTimeC() {
		return timeC;
	}
}
