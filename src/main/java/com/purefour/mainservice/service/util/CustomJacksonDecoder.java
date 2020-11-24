package com.purefour.mainservice.service.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomJacksonDecoder {
	public final ObjectMapper mapper;

	public CustomJacksonDecoder() {
		this.mapper = new ObjectMapper();
		this.mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.mapper.registerModule(new JavaTimeModule());
	}

}
