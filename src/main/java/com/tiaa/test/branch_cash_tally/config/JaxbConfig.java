package com.tiaa.test.branch_cash_tally.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tiaa.test.branch_cash_tally.FileProcessor;
import com.tiaa.test.branch_cash_tally.gen.CmFoodChainType;


@Configuration
public class JaxbConfig {

	private Logger logger = LoggerFactory.getLogger(FileProcessor.class);
	@Bean
	public Unmarshaller getJaxbUnMarshaller() {
		try {
			JAXBContext jaxbContext = getJaxbContext();
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
			return jaxbMarshaller;
		} catch (JAXBException e) {
			logger.error("JAXBException occured while creating UnMarshaller", e);
		}
		return null;
	}
	
	/**
	 * This method creates the JaxbContext for CmFoodChainType
	 * @return
	 * @throws JAXBException
	 */
	public JAXBContext getJaxbContext() throws JAXBException{
		
		return JAXBContext.newInstance(CmFoodChainType.class);
	}
	

}
