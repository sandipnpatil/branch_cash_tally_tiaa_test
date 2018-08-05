package com.tiaa.test.branch_cash_tally.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tiaa.test.branch_cash_tally.gen.CmFoodChainType;


@Configuration
public class JaxbConfig {

	@Bean
	public Unmarshaller getJaxbUnMarshaller() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CmFoodChainType.class);
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
			return jaxbMarshaller;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
