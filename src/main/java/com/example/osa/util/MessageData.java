package com.example.osa.util;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
@Getter
@Setter
public class MessageData {
	
	private String to;
	private String subject;
	private Date sentDate;
	private String text;

}
