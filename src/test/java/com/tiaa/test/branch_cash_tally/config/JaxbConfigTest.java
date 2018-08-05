package com.tiaa.test.branch_cash_tally.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JaxbConfigTest {

	@Autowired
	private JaxbConfig config;

	@Test
	public void testGetJaxbUnMarshaller() {
		Unmarshaller unMarshaller = config.getJaxbUnMarshaller();
		Assert.assertNotNull(unMarshaller);
	}
	
	@Test
	public void testGetJaxbUnMarshallerException(){
		
		config = new JaxbConfig(){

			@Override
			public JAXBContext getJaxbContext() throws JAXBException {
				throw new JAXBException("Throwing exception for testing");
			}
		};
		
		Unmarshaller unMarshaller=config.getJaxbUnMarshaller();
		Assert.assertNull(unMarshaller);
		
	}

}
