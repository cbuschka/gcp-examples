package com.github.cbuschka.gcp_examples.cloudrun_with_cloudsql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Message")
@Table(name="message")
public class MessageEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String body;

	public Long getId()
	{
		return id;
	}

	public String getBody()
	{
		return body;
	}
}
